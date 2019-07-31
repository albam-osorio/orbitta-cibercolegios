package com.tbf.cibercolegios.api.routes.web.users;

import static java.util.stream.Collectors.toList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RestController;

import com.tbf.cibercolegios.api.ciber.services.api.CiberService;
import com.tbf.cibercolegios.api.model.routes.enums.CourseType;
import com.tbf.cibercolegios.api.routes.model.graph.web.RutaConCapacidadDto;
import com.tbf.cibercolegios.api.routes.models.users.TrayectoViewModel;
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
public class UsuariosDireccionDialogEditRutaController extends DialogCommandController<TrayectoViewModel, String>
		implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	protected UserProfile profile;

	@Autowired
	private CiberService ciberService;

	@Autowired
	private RutaService rutaService;

	// ----------------------------------------------------------------------------------------------------
	// --
	// ----------------------------------------------------------------------------------------------------
	private Integer pasajeroId;

	private List<TrayectoViewModel> trayectos;

	private boolean filtrar = true;

	private List<Integer> otrasRutasAsignadasId;

	private List<RutaConCapacidadDto> rutasConCapacidad;

	// ----------------------------------------------------------------------------------------------------
	// --INICIALIZADORES
	// ----------------------------------------------------------------------------------------------------
	@Override
	protected void init() {
		this.setOtrasRutasAsignadasId(new ArrayList<>());
		this.setRutasConCapacidad(new ArrayList<>());
	}

	@Override
	protected void clear() {
		super.clear();
		this.getOtrasRutasAsignadasId().clear();
		this.getRutasConCapacidad().clear();
	}

	@Override
	protected boolean populate() {
		val institucionId = profile.getInstitucionId();
		if (institucionId != null && this.getPasajeroId() != null && this.getTrayectos() != null) {
			this.setFiltrar(true);

			val rutaId = getModel().getRutaId();
			this.getOtrasRutasAsignadasId().addAll(getTrayectos().stream().map(TrayectoViewModel::getRutaId)
					.filter(a -> a != null && !a.equals(rutaId)).distinct().collect(toList()));

			this.getRutasConCapacidad().addAll(findRutasConCapacidad(institucionId, this.getPasajeroId(), rutaId));
			return true;
		}
		return false;
	}

	// -----------------------------------------------------------------------------------
	// --
	// -----------------------------------------------------------------------------------
	private List<RutaConCapacidadDto> findRutasConCapacidad(int institucionId, int pasajeroId, Integer rutaId) {

		// Obtener todas las rutas del colegio que no hayan sido asignadas al mismo
		// pasajero
		val list = rutaService.findAllRutasConCapacidadByInstitucionId(institucionId, pasajeroId).stream()
				.filter(a -> !this.getOtrasRutasAsignadasId().contains(a.getId())).collect(toList());

		// Se selecciona la ruta asignada al trayecto actual
		val optional = list.stream().filter(a -> a.getId().equals(rutaId)).findFirst();
		if (optional.isPresent()) {
			optional.get().setSeleccionada(true);
		}

		// Si el trayecto esta inactivo se puede seleccionar cualquier ruta
		// Si el trayecto esta activo solo aquellas con disponibilidad
		list.forEach(a -> {
			a.setHabilitada(enableRuta(a, getModel().isTieneDireccionAm(), getModel().isTieneDireccionPm(), rutaId));
		});

		// Se filtran las rutas que no van a poder ser seleccionadas
		return list.stream().filter(a -> isFiltrar() ? a.isHabilitada() : true).collect(toList());
	}

	private static boolean enableRuta(RutaConCapacidadDto ruta, boolean requiereAm, boolean requierePm,
			Integer rutaAsignadaId) {
		boolean result = true;

		boolean asignadaAEsteTrayecto = ruta.getId().equals(rutaAsignadaId);
		boolean am = (!requiereAm || (requiereAm && ruta.getDisponibilidadAm() > 0));
		boolean pm = (!requierePm || (requierePm && ruta.getDisponibilidadPm() > 0));

		result = (asignadaAEsteTrayecto || (am && pm));

		return result;
	}

	// ----------------------------------------------------------------------------------------------------
	// -- EVENTOS
	// ----------------------------------------------------------------------------------------------------
	public void onChangeFiltro() {
		val optional = this.getRutasConCapacidad().stream().filter(a -> a.isSeleccionada())
				.map(RutaConCapacidadDto::getId).findFirst();

		val institucionId = profile.getInstitucionId();
		val rutaId = optional.isPresent() ? optional.get() : null;

		this.getRutasConCapacidad().clear();
		this.getRutasConCapacidad().addAll(findRutasConCapacidad(institucionId, this.getPasajeroId(), rutaId));
	}

	public void onChangeSeleccionado(RutaConCapacidadDto ruta) {

		if (ruta.isSeleccionada()) {
			// Se libera el cupo de la ruta anteriormente seleccionada
			getRutasConCapacidad().stream().filter(a -> a != ruta && a.isSeleccionada()).forEach(a -> {
				a.setSeleccionada(false);
			});
		}

		val rutaId = ruta.isSeleccionada() ? ruta.getId() : null;
		// Si el trayecto esta inactivo se puede seleccionar cualquier ruta
		// Si el trayecto esta activo solo aquellas con disponibilidad
		getRutasConCapacidad().forEach(a -> {
			a.setHabilitada(enableRuta(a, getModel().isTieneDireccionAm(), getModel().isTieneDireccionPm(), rutaId));
		});

		if (isFiltrar()) {
			val remove = getRutasConCapacidad().stream().filter(a -> !a.isHabilitada()).collect(toList());
			for (val e : remove) {
				getRutasConCapacidad().remove(e);
			}
		}
	}

	public int ocupacionAm(RutaConCapacidadDto ruta) {
		int result = 0;
		if (ruta.isSeleccionada() && getModel().isActivo() && getModel().isTieneDireccionAm()) {
			result = 1;
		}
		return result;
	}

	public int ocupacionPm(RutaConCapacidadDto ruta) {
		int result = 0;
		if (ruta.isSeleccionada() && getModel().isActivo() && getModel().isTieneDireccionPm()) {
			result = 1;
		}
		return result;
	}

	// ----------------------------------------------------------------------------------------------------
	// -- COMANDOS
	// ----------------------------------------------------------------------------------------------------
	public void submit() {
		submit(new AsignarRutaCommand(), null);
	}

	private final class AsignarRutaCommand extends Command<String> {

		@Override
		protected void test(List<String> errors) {
			super.test(errors);
			if (getModel().isActivo()) {
				val selected = getRutasConCapacidad().stream().filter(a -> a.isSeleccionada()).findFirst();
				if (selected.isPresent()) {
					val ruta = selected.get();
					if (getModel().isTieneDireccionAm() && ruta.getDisponibilidadAm() <= 0) {
						errors.add("En la ruta seleccionada, no hay cupo para el trayecto "
								+ CourseType.SENTIDO_IDA.getDescripcion());
					}
					if (getModel().isTieneDireccionPm() && ruta.getDisponibilidadPm() <= 0) {
						errors.add("En la ruta seleccionada, no hay cupo para el trayecto "
								+ CourseType.SENTIDO_RETORNO.getDescripcion());
					}
				}
			}
		}

		@Override
		protected void execute() {
			val selected = getRutasConCapacidad().stream().filter(a -> a.isSeleccionada()).findFirst();

			// En este punto getModel().getRuta().getId() tiene la ruta actualmente y
			// temporalmente asignada,
			// puede ser igual originalRutaId, pero si se ha invocado esta funcionalidad mas
			// de una vez y no se han guardado los cambios,
			// puede que estos valores sean diferentes. originalRutaId se usara al momento
			// de guardar.

			val oldRutaId = getModel().getRutaId();
			val newRutaId = selected.isPresent() ? selected.get().getId() : null;

			boolean modificado = false;
			if (oldRutaId != null && newRutaId != null) {
				modificado = !oldRutaId.equals(newRutaId);
			} else {
				if (oldRutaId != null || newRutaId != null) {
					modificado = true;
				}
			}

			if (modificado) {
				if (selected.isPresent()) {
					getModel().setRutaId(selected.get().getId());
					getModel().setRutaDescripcion(selected.get().getDescripcion());
				} else {
					getModel().setRutaId(null);
					getModel().setRutaDescripcion(null);
				}
				if (getModel().isTieneDireccionAm()) {
					getModel().getDireccionAm().setSecuencia(null);
				}

				if (getModel().isTieneDireccionPm()) {
					getModel().getDireccionPm().setSecuencia(null);
				}
			}
		}
	}
}