package com.tbf.cibercolegios.api.routes.controllers.routes.managers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.faces.model.SelectItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tbf.cibercolegios.api.ciber.services.api.CiberService;
import com.tbf.cibercolegios.api.routes.controllers.routes.models.RutaViewModel;
import com.tbf.cibercolegios.api.routes.controllers.util.FacesMessages;
import com.tbf.cibercolegios.api.routes.controllers.util.UserPreferences;
import com.tbf.cibercolegios.api.routes.model.graph.DireccionDto;
import com.tbf.cibercolegios.api.routes.model.graph.RutaDto;
import com.tbf.cibercolegios.api.routes.services.api.DireccionService;
import com.tbf.cibercolegios.api.routes.services.api.RutaService;

import lombok.Getter;
import lombok.Setter;
import lombok.val;

@Component
@Setter
@Getter
public class RutasEdicionManager implements Serializable {

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

	private boolean newEntity = false;

	private boolean editing = false;

	private RutaViewModel model;

	private Integer getInstitucionId() {
		val result = preferences.getInstitucionId();
		return result;
	}

	private Integer getPaisId() {
		val result = preferences.getPaisId();
		return result;
	}

	public boolean initAdd() {
		resetActions();
		try {
			resetListas();
			this.model = new RutaViewModel();

			this.setNewEntity(true);
			this.setEditing(true);
		} catch (RuntimeException e) {
			FacesMessages.fatal(e);
		}
		boolean result = this.isEditing();
		return result;
	}

	public boolean initEdit(Integer id, Integer version) {
		resetActions();
		try {
			resetListas();
			val optional = rutasService.findById(id);
			if (optional.isPresent()) {
				this.model = asViewModel(optional.get());
				this.model.getRuta().setVersion(version);

				updateDireccionExistente();
				this.setNewEntity(false);
				this.setEditing(true);
			} else {
				FacesMessages.error("No se encontró la ruta con id=" + id);
			}
		} catch (RuntimeException e) {
			FacesMessages.fatal(e);
		}
		boolean result = this.isEditing();
		return result;
	}

	private void updateDireccionExistente() {
		String value = "";
		val id = this.model.getRuta().getDireccionSedeId();
		if (id != null) {
			val optional = this.listaDireccion.stream().filter(a -> a.getValue().equals(id)).findFirst();
			if (optional.isPresent()) {
				value = optional.get().getLabel();
			}
		}
		this.model.setDireccionExistente(value);
	}

	private void resetActions() {
		this.setNewEntity(false);
		this.setEditing(false);
	}

	// -----------------------------------------------------------------------------------
	public String getTitle() {
		String result;
		if (isEditing()) {
			if (isNewEntity()) {
				result = "AGREGAR NUEVA RUTA";
			} else {
				result = "EDITAR RUTA";
			}
		} else {
			result = "Ocurrio un error durante la inicialización del formulario";
		}
		return result;
	}

	public String getActionLabel() {
		String result;
		if (isEditing()) {
			if (isNewEntity()) {
				result = "Agregar";
			} else {
				result = "Actualizar";
			}
		} else {
			result = "X";
		}
		return result;
	}

	public void save() {
		try {
			if (model.getRuta().getDireccionSedeId() == null) {
				val direccion = asDireccion(model);
				val newDireccion = direccionesService.create(direccion);

				model.getRuta().setDireccionSedeId(newDireccion.getId());
			}

			val ruta = asModel(model);
			RutaDto newRuta;
			String summary;
			if (isNewEntity()) {
				newRuta = rutasService.create(ruta);
				summary = "La ruta con código %s ha sido creada";
			} else {
				newRuta = rutasService.update(ruta);
				summary = "La ruta con código %s ha sido modificada";
			}

			summary = String.format("La ruta con código %s ha sido modificada", newRuta.getCodigo());
			FacesMessages.info("Ruta modificada", summary);
		} catch (RuntimeException e) {
			FacesMessages.fatal(e);
		}
	}

	public void delete(Integer id, Integer version) {
		try {
			val optional = rutasService.findById(id);
			if (optional.isPresent()) {
				rutasService.delete(id, version);

				val summary = String.format("La ruta con código %s ha sido eliminada", optional.get().getCodigo());
				FacesMessages.info("Ruta eliminada", summary);
			} else {
				FacesMessages.error("No se encontró la ruta con id=" + id);
			}
		} catch (RuntimeException e) {
			FacesMessages.fatal(e);
		}
	}

	// -----------------------------------------------------------------------------------
	public void onChangeIdentificacion() {
		val institucion = getInstitucionId();
		val tipoId = model.getTipoIdentificacionId();
		val identificacion = model.getNumeroIdentificacion();

		if (tipoId != null && !"".equals(identificacion)) {
			val monitor = ciberService.findMonitorByInstitucionAndIdentificacion(institucion, tipoId, identificacion);

			if (monitor.isPresent()) {
				model.setMonitor(monitor.get());
				model.setNombreMonitor(model.getMonitor().getNombreCompleto());
			} else {
				model.setNombreMonitor("Monitor no encontrado");
			}
		} else {
			model.setNombreMonitor("");
		}
	}

	public void onChangeUsarDireccionExistente() {
		model.resetDireccion();
	}

	public void onChangeDepartamento() {
		loadCiudades(model.getDepartamentoId());
		model.setCiudadId(null);
	}

	public void onChangeDireccionExistente() {
		updateDireccionExistente();
	}

	private void resetListas() {
		loadTiposDocumento();
		loadDepartamentos(getPaisId());
		loadDirecciones(getInstitucionId());
	}

	private void loadTiposDocumento() {
		val list = ciberService.findAllTiposDocumento().stream()
				.sorted((a, b) -> a.getDescripcion().compareTo(b.getDescripcion())).collect(Collectors.toList());
		listaTipoDocumento = new ArrayList<>();
		for (val e : list) {
			listaTipoDocumento.add(new SelectItem(e.getId(), e.getDescripcion()));
		}
	}

	private void loadDepartamentos(Integer paisId) {
		val list = ciberService.findAllDepartamentosByPais(paisId);
		listaDepartamento = new ArrayList<>();
		for (val e : list) {
			listaDepartamento.add(new SelectItem(e.getDepartamentoId(), e.getNombre()));
		}
	}

	private void loadDirecciones(Integer institucionId) {
		val list = direccionesService.findAllByInstitucionId(institucionId);
		listaDireccion = new ArrayList<>();
		for (val e : list) {
			val ciudad = ciberService.findCiudadByPk(e.getPaisId(), e.getDepartamentoId(), e.getCiudadId());
			val label = e.getDireccion() + ", " + ciudad.get().getNombre();
			listaDireccion.add(new SelectItem(e.getId(), label));
		}
	}

	private void loadCiudades(int departamentoId) {
		val list = ciberService.findAllCiudadesByDepartamento(getPaisId(), departamentoId);
		listaCiudad = new ArrayList<>();
		for (val e : list) {
			listaCiudad.add(new SelectItem(e.getCiudadId(), e.getNombre()));
		}
	}

	// -----------------------------------------------------------------------------------
	private RutaViewModel asViewModel(RutaDto ruta) {
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

	private DireccionDto asDireccion(RutaViewModel vm) {
		val result = new DireccionDto();

		result.setInstitucionId(getInstitucionId());

		result.setEstadoId(DireccionDto.ESTADO_NO_PROCESADA);

		result.setPaisId(getPaisId());
		result.setDepartamentoId(vm.getDepartamentoId());
		result.setCiudadId(vm.getCiudadId());
		result.setDireccion(vm.getDireccionNueva());

		return result;
	}

	private RutaDto asModel(RutaViewModel vm) {
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
