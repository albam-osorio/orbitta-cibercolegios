package com.tbf.cibercolegios.api.routes.web.routes;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import com.tbf.cibercolegios.api.ciber.model.graph.CiudadDto;
import com.tbf.cibercolegios.api.ciber.model.graph.DepartamentoDto;
import com.tbf.cibercolegios.api.ciber.model.graph.UsuarioDto;
import com.tbf.cibercolegios.api.ciber.model.graph.UsuarioPasajeroDto;
import com.tbf.cibercolegios.api.ciber.services.api.CiberService;
import com.tbf.cibercolegios.api.model.routes.PasajeroDireccion;
import com.tbf.cibercolegios.api.model.routes.enums.CourseType;
import com.tbf.cibercolegios.api.routes.model.graph.DireccionDto;
import com.tbf.cibercolegios.api.routes.model.graph.RutaDto;
import com.tbf.cibercolegios.api.routes.models.routes.RutaEditTrayectoViewModel;
import com.tbf.cibercolegios.api.routes.models.users.DireccionViewModel;
import com.tbf.cibercolegios.api.routes.models.users.TrayectoViewModel;
import com.tbf.cibercolegios.api.routes.repository.PasajeroDireccionRepository;
import com.tbf.cibercolegios.api.routes.services.api.DireccionService;
import com.tbf.cibercolegios.api.routes.services.api.PasajeroService;
import com.tbf.cibercolegios.api.routes.services.api.RutaService;
import com.tbf.cibercolegios.api.routes.web.utils.UserProfile;
import com.tbf.cibercolegios.core.Command;
import com.tbf.cibercolegios.core.CrudController;

import lombok.Getter;
import lombok.Setter;
import lombok.val;

@RestController
@Scope("view")
@Setter
@Getter
public class RutaEditController extends CrudController<RutaEditTrayectoViewModel, String> {

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

	// -----------------------------------------------------------------------------------
	// --
	// -----------------------------------------------------------------------------------
	private Integer rutaId;
	private RutaDto ruta;
	private UsuarioDto monitor;

	// -----------------------------------------------------------------------------------
	// --
	// -----------------------------------------------------------------------------------
	@Override
	protected boolean populate() {
		val institucionId = getProfile().getInstitucionId();

		if (institucionId != null && getRutaId() != null) {
			{
				val optional = getRutaService().findById(getRutaId());
				if (optional.isPresent()) {
					setRuta(optional.get());
				} else {
					return false;
				}
			}

			{
				val optional = getCiberService().findUsuarioMonitorByInstitucionIdAndUsuarioId(institucionId,
						getRuta().getMonitorId());
				if (optional.isPresent()) {
					setMonitor(optional.get());
				} else {
					return false;
				}
			}

			find();

			return true;
		}

		return false;
	}

	// -----------------------------------------------------------------------------------
	// --
	// -----------------------------------------------------------------------------------
	@Override
	protected void executeFind() {
		val institucionId = getProfile().getInstitucionId();
		val rutaId = getRutaId();

		val pasajerosDirecciones = getPasajeroService().findAllPasajeroDireccionByRutaId(rutaId);
		val pasajerosId = pasajerosDirecciones.stream().map(PasajeroDireccion::getPasajeroId).distinct()
				.collect(toList());
		val pasajeros = getCiberService()
				.findAllUsuariosPasajerosByInstitucionIdAndPasajeroIdInAsUsuarioPasajeroDto(institucionId, pasajerosId);

		val direcionesId = pasajerosDirecciones.stream().map(PasajeroDireccion::getDireccionId).distinct()
				.collect(toList());
		val direcciones = getDireccionService().findAllById(direcionesId);

		val ciudades = getCiberService().findCiudadesByDirecciones(direcciones);

		// -----------------------------------------------------------------------------------
		// Construir los trayectos
		// -----------------------------------------------------------------------------------
		val entries = pasajerosDirecciones.stream().collect(groupingBy(PasajeroDireccion::getPasajeroId)).entrySet();
		val list = entries.stream().map(asTrayecto(pasajeros, direcciones, ciudades))
				.sorted((a, b) -> a.getOrdenAm().compareTo(b.getOrdenAm())).collect(toList());

		this.getModels().addAll(list);
	}

	private Function<Entry<Integer, List<PasajeroDireccion>>, RutaEditTrayectoViewModel> asTrayecto(
			List<UsuarioPasajeroDto> pasajeros, List<DireccionDto> direcciones,
			Map<DepartamentoDto, List<CiudadDto>> ciudades) {
		return entry -> {
			return RutaEditTrayectoViewModel.asTrayecto(entry.getKey(), pasajeros, entry.getValue(), direcciones,
					ciudades);
		};
	}

	// -----------------------------------------------------------------------------------
	// -- EVENTOS
	// -----------------------------------------------------------------------------------

	// -----------------------------------------------------------------------------------
	// -- ENABLE COMMANDS
	// -----------------------------------------------------------------------------------
	public boolean enableAssignPasajeros() {
		return true;
	}

	public void assignPasajeros() {

	}

	public boolean enableSortRoute(TrayectoViewModel trayecto) {
		return trayecto.isActivo() && trayecto.getRutaId() != null && !enableSave();
	}

	public boolean enableAddDireccion(TrayectoViewModel trayecto, CourseType sentido) {
		boolean result = trayecto.isEditable();
		return result;
	}

	public boolean enableEditDireccion(TrayectoViewModel trayecto, DireccionViewModel direccion) {
		return true;
	}

	public boolean enableDeleteDireccion(TrayectoViewModel trayecto, DireccionViewModel direccion) {
		boolean result = trayecto.isEditable();
		return result;
	}

	public boolean enableSave() {
		boolean result = false;
		return result;
	}

	// -----------------------------------------------------------------------------------
	// -- COMANDOS
	// -----------------------------------------------------------------------------------
	public void save() {
		submit(new SaveCommand());
	}

	public void cancel() {

	}
	
	public long getNumeroDeInscritosAm() {
		val result = getModels().stream().filter(a -> a.isActivo() && a.isTieneDireccionAm()).count();
		return result;
	}

	public long getNumeroDeInscritosPm() {
		val result = getModels().stream().filter(a -> a.isActivo() && a.isTieneDireccionPm()).count();
		return result;
	}

	@Autowired
	private PasajeroDireccionRepository pasajeroDireccionRepository;

	private final class SaveCommand extends Command<String> {

		@Override
		protected void test(List<String> errors) {

		}

		@Transactional(readOnly = false)
		@Override
		protected void execute() {

			reset();
		}
	}
}
