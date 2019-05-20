package com.tbf.cibercolegios.api.routes.controllers.routes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.faces.model.SelectItem;

import org.springframework.beans.factory.annotation.Autowired;

import com.tbf.cibercolegios.api.ciber.services.api.CiberService;
import com.tbf.cibercolegios.api.routes.controllers.routes.models.RutaViewModel;
import com.tbf.cibercolegios.api.routes.controllers.util.UserPreferences;
import com.tbf.cibercolegios.api.routes.model.graph.DireccionDto;
import com.tbf.cibercolegios.api.routes.model.graph.RutaDto;
import com.tbf.cibercolegios.api.routes.services.api.DireccionService;
import com.tbf.cibercolegios.api.routes.services.api.RutaService;
import com.tbf.cibercolegios.core.DialogCommandController;

import lombok.Getter;
import lombok.Setter;
import lombok.val;

@Setter
@Getter
public abstract class RutasDialogCommandController extends DialogCommandController<RutaViewModel, String>
		implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private UserPreferences preferences;

	@Autowired
	private CiberService ciberService;

	@Autowired
	private RutaService rutasService;

	@Autowired
	private DireccionService direccionesService;

	private List<SelectItem> listaTipoDocumento;

	private List<SelectItem> listaDepartamento;

	private List<SelectItem> listaCiudad;

	private List<SelectItem> listaDireccion;

	// ----------------------------------------------------------------------------------------------------
	// --INICIALIZADORES
	// ----------------------------------------------------------------------------------------------------
	@Override
	protected void init() {
		listaTipoDocumento = new ArrayList<>();
		listaDepartamento = new ArrayList<>();
		listaDireccion = new ArrayList<>();
		listaCiudad = new ArrayList<>();

		listaTipoDocumento.addAll(findAllTiposDocumentos());
		listaDepartamento.addAll(findAllDepartamentos(getPaisId()));
	}

	@Override
	protected void populate() {
		resetDepartamento();

		listaDireccion.clear();
		listaDireccion.addAll(findAllDirecciones(getInstitucionId()));
	}

	private void resetDepartamento() {
		resetCiudades();
		if (getModel() != null) {
			getModel().setDepartamentoId(null);
		}
	}

	private void resetCiudades() {
		listaCiudad.clear();
		if (getModel() != null) {
			getModel().setCiudadId(null);
		}
	}

	// ----------------------------------------------------------------------------------------------------
	// --INTERACCIONES Y EVENTOS
	// ----------------------------------------------------------------------------------------------------
	public void onChangeIdentificacion() {
		val institucion = getInstitucionId();
		val tipoId = getModel().getTipoIdentificacionId();
		val identificacion = getModel().getNumeroIdentificacion();

		if (tipoId != null && !"".equals(identificacion)) {
			val monitor = ciberService.findMonitorByInstitucionAndIdentificacion(institucion, tipoId, identificacion);

			if (monitor.isPresent()) {
				getModel().setMonitor(monitor.get());
				getModel().setNombreMonitor(getModel().getMonitor().getNombreCompleto());
			} else {
				getModel().setNombreMonitor("Monitor no encontrado");
			}
		} else {
			getModel().setNombreMonitor("");
		}
	}

	public void onChangeUsarDireccionExistente() {
		getModel().resetDireccion();
	}

	public void onChangeDepartamento() {
		resetCiudades();

		if (getModel().getDepartamentoId() != null) {
			listaCiudad.addAll(findAllCiudadesByDepartamento(getPaisId(), getModel().getDepartamentoId()));
		}
	}

	public void onChangeDireccionExistente() {
		String value = "";
		val id = getModel().getRuta().getDireccionSedeId();
		if (id != null) {
			val optional = this.listaDireccion.stream().filter(a -> a.getValue().equals(id)).findFirst();
			if (optional.isPresent()) {
				value = optional.get().getLabel();
			}
		}
		getModel().setDireccionExistente(value);
	}

	// ----------------------------------------------------------------------------------------------------
	// --ACCESO A DATOS
	// ----------------------------------------------------------------------------------------------------
	private List<SelectItem> findAllTiposDocumentos() {
		return ciberService.findAllTiposDocumento().stream()
				.sorted((a, b) -> a.getDescripcion().compareTo(b.getDescripcion()))
				.map(e -> new SelectItem(e.getId(), e.getDescripcion())).collect(Collectors.toList());
	}

	private List<SelectItem> findAllDepartamentos(Integer paisId) {
		return ciberService.findAllDepartamentosByPais(paisId).stream()
				.map(e -> new SelectItem(e.getDepartamentoId(), e.getNombre())).collect(Collectors.toList());
	}

	private List<SelectItem> findAllDirecciones(Integer institucionId) {
		List<SelectItem> result = new ArrayList<>();

		val list = direccionesService.findAllByInstitucionId(institucionId);
		for (val e : list) {
			val ciudad = ciberService.findCiudadByPk(e.getPaisId(), e.getDepartamentoId(), e.getCiudadId());
			val label = e.getDireccion() + ", " + ciudad.get().getNombre();
			result.add(new SelectItem(e.getId(), label));
		}
		return result;
	}

	private List<SelectItem> findAllCiudadesByDepartamento(Integer paisId, Integer departamentoId) {
		return ciberService.findAllCiudadesByDepartamento(paisId, departamentoId).stream()
				.map(e -> new SelectItem(e.getCiudadId(), e.getNombre())).collect(Collectors.toList());
	}

	// -----------------------------------------------------------------------------------
	// -- Auxiliares
	// -----------------------------------------------------------------------------------
	public abstract String getTitle();

	public abstract String getActionLabel();

	private Integer getInstitucionId() {
		val result = preferences.getInstitucionId();
		return result;
	}

	private Integer getPaisId() {
		val result = preferences.getPaisId();
		return result;
	}

	protected RutaViewModel asViewModel(RutaDto ruta) {
		val monitor = ciberService.findUsuarioById(ruta.getMonitorId()).get();
		val direccion = direccionesService.findById(ruta.getDireccionSedeId()).get();

		val result = new RutaViewModel();
		result.setRuta(ruta);

		result.setMonitor(monitor);
		result.setTipoIdentificacionId(monitor.getTipoId());
		result.setNumeroIdentificacion(monitor.getNumeroId());
		result.setNombreMonitor(monitor.getNombreCompleto());

		result.setUsarDireccionExistente(true);
		result.setDepartamentoId(direccion.getDepartamentoId());
		result.setCiudadId(direccion.getCiudadId());
		result.setDireccionNueva("");
		result.setDireccionExistente("");

		return result;
	}

	protected DireccionDto asDireccion(RutaViewModel vm) {
		val result = new DireccionDto();

		result.setInstitucionId(getInstitucionId());

		result.setEstadoId(DireccionDto.ESTADO_NO_PROCESADA);

		result.setPaisId(getPaisId());
		result.setDepartamentoId(vm.getDepartamentoId());
		result.setCiudadId(vm.getCiudadId());
		result.setDireccion(vm.getDireccionNueva());

		return result;
	}

	protected RutaDto asModel(RutaViewModel vm) {
		val result = vm.getRuta();

		if (result.getId() == null) {
			result.setInstitucionId(getInstitucionId());
			result.setSentido(RutaDto.SENTIDO_IDA);
			result.setEstadoId(RutaDto.ESTADO_INACTIVA);
		}

		result.setMonitorId(vm.getMonitor().getId());

		return result;
	}
}
