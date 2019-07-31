package com.tbf.cibercolegios.api.routes.web.routes;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javax.faces.model.SelectItem;

import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import com.tbf.cibercolegios.api.ciber.model.graph.NivelEducativoDto;
import com.tbf.cibercolegios.api.ciber.model.graph.UsuarioPasajeroDto;
import com.tbf.cibercolegios.api.ciber.services.api.CiberService;
import com.tbf.cibercolegios.api.model.routes.PasajeroDireccion;
import com.tbf.cibercolegios.api.routes.model.graph.RutaDto;
import com.tbf.cibercolegios.api.routes.models.routes.RutaEditDireccionViewModel;
import com.tbf.cibercolegios.api.routes.models.routes.RutaEditDialogAssignViewModel;
import com.tbf.cibercolegios.api.routes.models.routes.RutaEditTrayectoViewModel;
import com.tbf.cibercolegios.api.routes.repository.PasajeroDireccionRepository;
import com.tbf.cibercolegios.api.routes.services.api.DireccionService;
import com.tbf.cibercolegios.api.routes.services.api.PasajeroService;
import com.tbf.cibercolegios.api.routes.services.api.RutaService;
import com.tbf.cibercolegios.api.routes.web.utils.FacesMessages;
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
public class RutaEditDialogAssignController extends DialogCommandController<RutaDto, String> {

	private static final String BR = "<br/>";

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
	private List<NivelEducativoDto> niveles;

	private List<SelectItem> programas;

	private List<SelectItem> grados;

	private Integer nivelId;

	private Integer jornadaId;

	private Integer programaId;

	private Integer gradoId;

	// ----------------------------------------------------------------------------------------------------
	// --
	// ----------------------------------------------------------------------------------------------------
	private List<RutaEditDialogAssignViewModel> inscritos;

	private List<RutaEditDialogAssignViewModel> noInscritos;

	private List<RutaEditDialogAssignViewModel> removidos;

	private DualListModel<RutaEditDialogAssignViewModel> dualListModel;

	// ----------------------------------------------------------------------------------------------------
	// --INICIALIZADORES
	// ----------------------------------------------------------------------------------------------------
	@Override
	protected void init() {
		niveles = new ArrayList<>();
		programas = new ArrayList<>();
		grados = new ArrayList<>();

		noInscritos = new ArrayList<>();
		inscritos = new ArrayList<>();
		removidos = new ArrayList<>();

		niveles.addAll(findNivelesEducativos(getInstitucionId()));
	}

	@Override
	protected void clear() {
		super.clear();

		programas.clear();
		grados.clear();
		nivelId = null;
		jornadaId = null;
		programaId = null;
		gradoId = null;

		noInscritos.clear();
		inscritos.clear();
		removidos.clear();
	}

	@Override
	protected boolean populate() {
		getInscritos().addAll(findInscritos(getInstitucionId(), getModel().getId()));

		resetNivel();
		return true;
	}

	private void resetNivel() {
		resetProgramas();

		nivelId = null;
	}

	private void resetProgramas() {
		resetGrados();

		programaId = null;
		programas.clear();
	}

	private void resetGrados() {
		resetEstudiantes();

		gradoId = null;
		grados.clear();
	}

	private void resetEstudiantes() {
		populateNoInscritos(new ArrayList<>());
		populateDualListModel();
	}

	// ----------------------------------------------------------------------------------------------------
	// --INTERACCIONES Y EVENTOS
	// ----------------------------------------------------------------------------------------------------
	public void onChangeNivelAcademico() {
		resetProgramas();

		val optional = niveles.stream().filter(a -> a.getId().equals(nivelId)).findFirst();
		if (optional.isPresent()) {
			val nivel = optional.get();
			jornadaId = nivel.getJornadaId();

			if (jornadaId != null && nivelId != null) {
				programas.clear();
				programas.addAll(findProgramas(getInstitucionId(), jornadaId, nivelId));
			}
		}
	}

	public void onChangePrograma() {
		resetGrados();

		if (jornadaId != null && nivelId != null && programaId != null) {
			grados.clear();
			grados.addAll(findGrados(getInstitucionId(), jornadaId, nivelId, programaId));
		}
	}

	public void onChangeGrado() {
		resetEstudiantes();

		if (jornadaId != null && nivelId != null && programaId != null && gradoId != null) {
			val noMiembros = findNoInscritos(getInstitucionId(), jornadaId, nivelId, programaId, gradoId);

			populateNoInscritos(noMiembros);
			populateDualListModel();
		}
	}

	private List<NivelEducativoDto> findNivelesEducativos(int institucionId) {
		val result = getCiberService().findAllNivelesEducativosByInstitucionId(institucionId);
		return result;
	}

	private List<SelectItem> findProgramas(int institucionId, int jornadaId, int nivelId) {
		val list = getCiberService().findAllProgramasByNivelEducativoId(institucionId, jornadaId, nivelId);

		val result = list.stream().map(a -> new SelectItem(a.getId(), a.getDescripcion())).collect(toList());
		return result;
	}

	private List<SelectItem> findGrados(int institucionId, int jornadaId, int nivelId, int programaId) {
		val list = getCiberService().findAllGradosByProgramaId(institucionId, jornadaId, nivelId, programaId);

		val result = list.stream().map(a -> new SelectItem(a.getId(), a.getDescripcion())).collect(toList());
		return result;
	}

	// ----------------------------------------------------------------------------------------------------
	// --
	// ----------------------------------------------------------------------------------------------------
	private void populateNoInscritos(List<RutaEditDialogAssignViewModel> noMiembros) {
		this.getNoInscritos().clear();
		this.getNoInscritos().addAll(noMiembros);
	}

	private DualListModel<RutaEditDialogAssignViewModel> populateDualListModel() {
		return dualListModel = new DualListModel<>(getNoInscritos(), getInscritos());
	}

	public void onTransfer(TransferEvent event) {
		try {
			if (event.isAdd()) {
				onTransferAdd(event);
			} else {
				onTransferRemove(event);
			}
		} catch (Exception e) {
			FacesMessages.fatal(e);
		}

		populateDualListModel();
	}

	private void onTransferAdd(TransferEvent event) {
		val transferidos = new ArrayList<RutaEditDialogAssignViewModel>();
		val duplicados = new ArrayList<RutaEditDialogAssignViewModel>();
		val existentes = new ArrayList<RutaEditDialogAssignViewModel>();
		val descartados = new ArrayList<RutaEditDialogAssignViewModel>();

		val pasajerosId = getInscritos().stream().map(RutaEditDialogAssignViewModel::getTrayecto)
				.map(RutaEditTrayectoViewModel::getPasajero).map(UsuarioPasajeroDto::getPasajeroId).distinct()
				.collect(toList());

		val duplicadosId = event.getItems().stream()
				.map(a -> ((RutaEditDialogAssignViewModel) a).getTrayecto().getPasajero().getPasajeroId())
				.collect(groupingBy(Function.identity(), counting())).entrySet().stream().filter(a -> a.getValue() > 1)
				.map(a -> a.getKey()).collect(toList());

		long am = getNumeroDeInscritosAm();
		long pm = getNumeroDeInscritosPm();
		long capacidad = getModel().getCapacidadMaxima();

		for (Object item : event.getItems()) {
			boolean success = true;
			val pasajero = ((RutaEditDialogAssignViewModel) item);
			val pasajeroId = pasajero.getTrayecto().getPasajero().getPasajeroId();

			if (duplicadosId.contains(pasajeroId)) {
				duplicados.add(pasajero);
				success = false;
			}

			if (pasajerosId.contains(pasajeroId)) {
				existentes.add(pasajero);
				success = false;
			}

			if (success) {
				long incrementoAm = (pasajero.getTrayecto().isActivo() && pasajero.getTrayecto().isTieneDireccionAm())
						? 1L
						: 0L;
				long incrementoPm = (pasajero.getTrayecto().isActivo() && pasajero.getTrayecto().isTieneDireccionPm())
						? 1L
						: 0L;

				if (am + incrementoAm > capacidad || pm + incrementoPm > capacidad) {
					descartados.add(pasajero);
					success = false;
				} else {
					am += incrementoAm;
					pm += incrementoPm;
				}
			}

			if (success) {
				if (pasajero.isMiembro()) {
					pasajero.setRemovido(false);
				} else {
					pasajero.setAdicionado(true);
				}
				transferidos.add(pasajero);
			}
		}

		if (!duplicados.isEmpty()) {
			val nombres = BR + duplicados.stream().map(a -> a.getTrayecto().getPasajero().getNombreCompleto())
					.distinct().collect(joining(BR));

			val format = "Los siguientes estudiantes poseen mas de una dirección y usted esta tratando de inscribir mas de una en esta ruta: %s";
			val msg = String.format(format, nombres);

			FacesMessages.warning(msg);
		}

		if (!existentes.isEmpty()) {
			val nombres = BR + existentes.stream().map(a -> a.getTrayecto().getPasajero().getNombreCompleto())
					.distinct().collect(joining(BR));

			val format = "Los siguientes estudiantes ya estan en la lista de inscritos de esta ruta: %s";
			val msg = String.format(format, nombres);

			FacesMessages.warning(msg);
		}

		if (!descartados.isEmpty()) {
			val nombres = BR + descartados.stream().map(a -> a.getTrayecto().getPasajero().getNombreCompleto())
					.distinct().collect(joining(BR));

			val format = "La capacidad máxima de la ruta es de solo %d pasajeros. Los siguientes estudiantes no pudieron ser inscritos en esta ruta: %s";
			val msg = String.format(format, getModel().getCapacidadMaxima(), nombres);

			FacesMessages.warning(msg);
		}

		getInscritos().addAll(transferidos);
		getNoInscritos().removeAll(transferidos);
	}

	private void onTransferRemove(TransferEvent event) {
		val transferidos = new ArrayList<RutaEditDialogAssignViewModel>();
		for (Object item : event.getItems()) {
			val pasajero = ((RutaEditDialogAssignViewModel) item);

			pasajero.setAdicionado(false);
			if (pasajero.isMiembro()) {
				pasajero.setRemovido(true);
			}

			transferidos.add(pasajero);
		}

		val remover = transferidos.stream().filter(a -> a.isMiembro()).collect(toList());
		getRemovidos().addAll(remover);
		getNoInscritos().addAll(transferidos);
		getInscritos().removeAll(event.getItems());
	}

	// ----------------------------------------------------------------------------------------------------
	// -- COMANDOS
	// ----------------------------------------------------------------------------------------------------
	public void submit() {
		submit(new AsignarCommand(), null);
	}

	@Autowired
	private PasajeroDireccionRepository pasajeroDireccionRepository;

	private final class AsignarCommand extends Command<String> {

		@Transactional(readOnly = false)
		@Override
		protected void execute() {

			val remove = getRemovidos().stream().map(a -> a.getTrayecto()).collect(toList());
			for (val trayecto : remove) {
				if (trayecto.isTieneDireccionAm()) {
					remove(trayecto.getDireccionAm());
				}
				if (trayecto.isTieneDireccionPm()) {
					remove(trayecto.getDireccionPm());
				}
			}

			val insert = getInscritos().stream().filter(a -> a.isAdicionado()).map(a -> a.getTrayecto())
					.collect(toList());

			for (val trayecto : insert) {
				if (trayecto.isTieneDireccionAm()) {
					add(trayecto.getDireccionAm());
				}
			}
		}

		private void remove(RutaEditDireccionViewModel direccion) {
			val optional = getPasajeroDireccionRepository().findById(direccion.getId());
			if (optional.isPresent()) {
				val entity = optional.get();
				entity.setRutaId(null);
				entity.setSecuencia(null);
				getPasajeroDireccionRepository().save(entity);
			}
		}

		private void add(RutaEditDireccionViewModel direccion) {
			val optional = getPasajeroDireccionRepository().findById(direccion.getId());
			if (optional.isPresent()) {
				val entity = optional.get();
				entity.setRutaId(getModel().getId());
				entity.setSecuencia(null);
				getPasajeroDireccionRepository().save(entity);
			}
		}
	}

	// ----------------------------------------------------------------------------------------------------
	// --
	// ----------------------------------------------------------------------------------------------------
	private List<RutaEditDialogAssignViewModel> findInscritos(int institucionId, int rutaId) {
		val pasajerosDirecciones = getPasajeroService().findAllPasajeroDireccionByRutaId(rutaId);
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
		val entries = pasajerosDirecciones.stream()
				.collect(groupingBy(PasajeroDireccion::getPasajeroId, groupingBy(PasajeroDireccion::getCorrelacion)))
				.entrySet();

		val trayectos = new ArrayList<RutaEditTrayectoViewModel>();
		entries.forEach(pasajero -> {
			pasajero.getValue().values().forEach(pasajerosDireccion -> {
				trayectos.add(RutaEditTrayectoViewModel.asTrayecto(pasajero.getKey(), pasajeros, pasajerosDireccion,
						direcciones, ciudades));
			});
		});

		val result = trayectos.stream().map(asPasajero(true)).collect(toList());

		return result.stream().sorted((a, b) -> a.getTrayecto().getOrdenAm().compareTo(b.getTrayecto().getOrdenAm()))
				.collect(toList());
	}

	private List<RutaEditDialogAssignViewModel> findNoInscritos(int institucionId, int jornadaId, int nivelId,
			int programaId, int gradoId) {
		val inscritos = getInscritos().stream().map(RutaEditDialogAssignViewModel::getTrayecto)
				.map(RutaEditTrayectoViewModel::getPasajero).map(UsuarioPasajeroDto::getPasajeroId).distinct()
				.collect(toList());

		val pasajeros = getCiberService().findAllUsuariosPasajerosByGradoIdAsUsuarioPasajeroDto(institucionId,
				jornadaId, nivelId, programaId, gradoId);
		val pasajerosId = pasajeros.stream().map(UsuarioPasajeroDto::getPasajeroId).filter(a -> !inscritos.contains(a))
				.distinct().collect(toList());
		val pasajerosDirecciones = getPasajeroService().findAllPasajeroDireccionByPasajeroIdIn(pasajerosId);

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
		val entries = pasajerosDirecciones.stream()
				.collect(groupingBy(PasajeroDireccion::getPasajeroId, groupingBy(PasajeroDireccion::getCorrelacion)))
				.entrySet();

		val trayectos = new ArrayList<RutaEditTrayectoViewModel>();
		entries.forEach(pasajero -> {
			pasajero.getValue().values().forEach(pasajerosDireccion -> {
				trayectos.add(RutaEditTrayectoViewModel.asTrayecto(pasajero.getKey(), pasajeros, pasajerosDireccion,
						direcciones, ciudades));
			});
		});

		val result = trayectos.stream().map(asPasajero(false)).collect(toList());

		result.forEach(a -> {
			int pasajeroId = a.getTrayecto().getPasajero().getPasajeroId();
			int correlacion = a.getTrayecto().getCorrelacion();
			boolean exists = getRemovidos().stream()
					.anyMatch(b -> b.getTrayecto().getPasajero().getPasajeroId().equals(pasajeroId)
							&& b.getTrayecto().getCorrelacion() == correlacion);

			if (exists) {
				a.setMiembro(true);
				a.setRemovido(true);
			}
		});
		return result.stream().sorted((a, b) -> a.getTrayecto().getOrdenAm().compareTo(b.getTrayecto().getOrdenAm()))
				.collect(toList());
	}

	private Function<RutaEditTrayectoViewModel, RutaEditDialogAssignViewModel> asPasajero(boolean miembro) {
		return trayecto -> {
			RutaEditDialogAssignViewModel result = new RutaEditDialogAssignViewModel();
			result.setTrayecto(trayecto);
			result.setMiembro(miembro);
			result.setAdicionado(false);
			result.setRemovido(false);
			return result;
		};
	}

	// -----------------------------------------------------------------------------------
	// -- Auxiliares
	// -----------------------------------------------------------------------------------
	private Integer getInstitucionId() {
		val result = getProfile().getInstitucionId();
		return result;
	}

	public long getNumeroDeInscritosAm() {
		val result = getInscritos().stream().map(RutaEditDialogAssignViewModel::getTrayecto)
				.filter(a -> a.isActivo() && a.isTieneDireccionAm()).count();
		return result;
	}

	public long getNumeroDeInscritosPm() {
		val result = getInscritos().stream().map(RutaEditDialogAssignViewModel::getTrayecto)
				.filter(a -> a.isActivo() && a.isTieneDireccionPm()).count();
		return result;
	}
}
