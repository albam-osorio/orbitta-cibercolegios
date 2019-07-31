package com.tbf.cibercolegios.api.routes.web.routes;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import com.tbf.cibercolegios.api.ciber.model.graph.CiudadDto;
import com.tbf.cibercolegios.api.ciber.model.graph.DepartamentoDto;
import com.tbf.cibercolegios.api.ciber.model.graph.UsuarioPasajeroDto;
import com.tbf.cibercolegios.api.ciber.services.api.CiberService;
import com.tbf.cibercolegios.api.model.routes.PasajeroDireccion;
import com.tbf.cibercolegios.api.model.routes.enums.CourseType;
import com.tbf.cibercolegios.api.routes.model.graph.DireccionDto;
import com.tbf.cibercolegios.api.routes.model.graph.RutaDto;
import com.tbf.cibercolegios.api.routes.models.routes.RutaEditDialogSortViewModel;
import com.tbf.cibercolegios.api.routes.models.routes.RutaEditDireccionViewModel;
import com.tbf.cibercolegios.api.routes.repository.PasajeroDireccionRepository;
import com.tbf.cibercolegios.api.routes.services.api.DireccionService;
import com.tbf.cibercolegios.api.routes.services.api.PasajeroService;
import com.tbf.cibercolegios.api.routes.services.api.RutaService;
import com.tbf.cibercolegios.api.routes.web.utils.UserProfile;
import com.tbf.cibercolegios.core.Command;
import com.tbf.cibercolegios.core.DialogCommandController;

import lombok.Getter;
import lombok.Setter;
import lombok.val;

@RestController
@Scope("view")
@Setter
@Getter
public class RutaEditDialogSortController extends DialogCommandController<RutaDto, String> {

	private static final long serialVersionUID = 1L;

	// -----------------------------------------------------------------------------------
	// --
	// -----------------------------------------------------------------------------------
	@Autowired
	private UserProfile profile;

	@Autowired
	private CiberService ciberService;

	@Autowired
	private RutaService rutaService;

	@Autowired
	private PasajeroService pasajeroService;

	@Autowired
	private DireccionService direccionService;

	// ----------------------------------------------------------------------------------------------------
	// --
	// ----------------------------------------------------------------------------------------------------
	private List<RutaEditDialogSortViewModel> direccionesAm;

	private List<RutaEditDialogSortViewModel> direccionesPm;

	// ----------------------------------------------------------------------------------------------------
	// --INICIALIZADORES
	// ----------------------------------------------------------------------------------------------------
	@Override
	protected void init() {
		direccionesAm = new ArrayList<>();
		direccionesPm = new ArrayList<>();
	}

	@Override
	protected void clear() {
		super.clear();

		direccionesAm.clear();
		direccionesPm.clear();
	}

	@Override
	protected boolean populate() {
		val institucionId = getInstitucionId();
		val rutaId = getModel().getId();

		val pasajerosDirecciones = getPasajeroService().findAllPasajeroDireccionByRutaId(rutaId).stream()
				.filter(a -> a.isActivo()).collect(toList());
		val pasajerosId = pasajerosDirecciones.stream().map(PasajeroDireccion::getPasajeroId).distinct()
				.collect(toList());
		val pasajeros = getCiberService()
				.findAllUsuariosPasajerosByInstitucionIdAndPasajeroIdInAsUsuarioPasajeroDto(institucionId, pasajerosId);

		// -----------------------------------------------------------------------------------
		// Direcciones y ciudades
		// -----------------------------------------------------------------------------------
		val direcionesId = pasajerosDirecciones.stream().map(PasajeroDireccion::getDireccionId).distinct()
				.collect(toList());
		val direcciones = getDireccionService().findAllById(direcionesId);

		val ciudades = getCiberService().findCiudadesByDirecciones(direcciones);

		// -----------------------------------------------------------------------------------
		// Construir los trayectos
		// -----------------------------------------------------------------------------------
		Comparator<RutaEditDialogSortViewModel> comparator = (a, b) -> a.getOrden().compareTo(b.getOrden());

		{
			val list = pasajerosDirecciones.stream().filter(a -> a.getSentido() == CourseType.SENTIDO_IDA.getIntValue())
					.map(asModel(pasajeros, direcciones, ciudades)).sorted(comparator).collect(toList());
			int i = 1;
			for (val direccion : list) {
				direccion.setSecuencia(i++);
			}
			getDireccionesAm().addAll(list);
		}

		{
			val list = pasajerosDirecciones.stream()
					.filter(a -> a.getSentido() == CourseType.SENTIDO_RETORNO.getIntValue())
					.map(asModel(pasajeros, direcciones, ciudades)).sorted(comparator).collect(toList());
			int i = 1;
			for (val direccion : list) {
				direccion.setSecuencia(i++);
			}
			getDireccionesPm().addAll(list);
		}

		return true;
	}

	private Function<PasajeroDireccion, RutaEditDialogSortViewModel> asModel(List<UsuarioPasajeroDto> pasajeros,
			List<DireccionDto> direcciones, Map<DepartamentoDto, List<CiudadDto>> ciudades) {
		return pd -> {
			val pasajero = pasajeros.stream().filter(a -> a.getPasajeroId() == pd.getPasajeroId()).findFirst().get();
			val direccion = RutaEditDireccionViewModel.asDireccionViewModel(pd, direcciones, ciudades);

			val result = new RutaEditDialogSortViewModel();
			result.setPasajero(pasajero);
			result.setCorrelacion(pd.getCorrelacion());
			result.setDireccion(direccion);

			return result;
		};
	}

	// ----------------------------------------------------------------------------------------------------
	// --INTERACCIONES Y EVENTOS
	// ----------------------------------------------------------------------------------------------------
	public void onReorderAm() {
		int i = 1;
		for (val direccion : this.getDireccionesAm()) {
			direccion.setSecuencia(i++);
		}
	}

	public void onReorderPm() {
		int i = 1;
		for (val direccion : this.getDireccionesPm()) {
			direccion.setSecuencia(i++);
		}
	}

	// ----------------------------------------------------------------------------------------------------
	// -- COMANDOS
	// ----------------------------------------------------------------------------------------------------
	public boolean enableSubmit() {
		return getDireccionesAm().stream().anyMatch(a -> a.isModificado())
				|| getDireccionesPm().stream().anyMatch(a -> a.isModificado());
	}

	public void submit() {
		submit(new OrganizarCommand(), null);
	}

	public void copyAmToPm() {
		copy(getDireccionesAm(), getDireccionesPm());
	}

	public void copyPmToAm() {
		copy(getDireccionesPm(), getDireccionesAm());
	}

	private void copy(List<RutaEditDialogSortViewModel> origen, List<RutaEditDialogSortViewModel> destino) {
		val tmp = new ArrayList<RutaEditDialogSortViewModel>();

		tmp.addAll(destino);
		destino.clear();

		origen.stream().sorted((a, b) -> Integer.compare(b.getSecuencia(), a.getSecuencia())).forEach(a -> {
			val optional = tmp.stream()
					.filter(b -> b.getPasajero().getPasajeroId().equals(a.getPasajero().getPasajeroId())
							&& b.getDireccion().getDireccion().equals(a.getDireccion().getDireccion()))
					.findFirst();

			if (optional.isPresent()) {
				tmp.remove(optional.get());
				destino.add(optional.get());
			}
		});

		if (!tmp.isEmpty()) {
			destino.addAll(tmp);

			val format = "Algunos pasajeros y/o direcciones no se encontraban en el sentido destino y se han colocado al final del recorrido. No olvide revisar el orden en el sentido destino.";

			val msg = new FacesMessage(FacesMessage.SEVERITY_WARN, format, "");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}

		int i = 1;
		for (val direccion : destino) {
			direccion.setSecuencia(i++);
		}
	}

	// -----------------------------------------------------------------------------------
	// -- Auxiliares
	// -----------------------------------------------------------------------------------
	private Integer getInstitucionId() {
		val result = getProfile().getInstitucionId();
		return result;
	}

	public long getNumeroDeInscritosAm() {
		val result = getDireccionesAm().size();
		return result;
	}

	public long getNumeroDeInscritosPm() {
		val result = getDireccionesPm().size();
		return result;
	}

	@Autowired
	private PasajeroDireccionRepository pasajeroDireccionRepository;

	private final class OrganizarCommand extends Command<String> {

		@Transactional(readOnly = false)
		@Override
		protected void execute() {
			new ArrayList<PasajeroDireccion>();
			val list = new ArrayList<RutaEditDialogSortViewModel>();
			list.addAll(getDireccionesAm().stream().filter(a-> a.isModificado()).collect(toList()));
			list.addAll(getDireccionesPm().stream().filter(a-> a.isModificado()).collect(toList()));;
			
			val ids = list.stream().map(a->a.getDireccion().getId()).distinct().collect(toList());
			val entities = getPasajeroDireccionRepository().findAllById(ids);
			
			list.forEach(a-> {
				val optional = entities.stream().filter(b-> b.getId().equals(a.getDireccion().getId())).findFirst();
				if(optional.isPresent()) {
					val entity =optional.get();
					entity.setSecuencia(a.getSecuencia());
					entity.setVersion(a.getDireccion().getVersion());
				}
			});
			
			getPasajeroDireccionRepository().saveAll(entities);
		}
	}
}
