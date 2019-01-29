package co.com.orbitta.cibercolegios.web.backing.rutas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.com.orbitta.cibercolegios.rutas.dto.DireccionDto;
import co.com.orbitta.cibercolegios.rutas.dto.RutaDto;
import co.com.orbitta.cibercolegios.rutas.service.api.DireccionService;
import co.com.orbitta.cibercolegios.rutas.service.api.RutaService;
import co.com.orbitta.cibercolegios.rutas.service.api.readonly.CiberService;
import co.com.orbitta.cibercolegios.web.backing.rutas.models.RutaViewModel;
import co.com.orbitta.cibercolegios.web.backing.sesion.UserPreferences;
import lombok.Getter;
import lombok.Setter;
import lombok.val;
import net.bootsfaces.utils.FacesMessages;

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

	private RutaViewModel model;

	private Integer getInstitucionId() {
		val result = preferences.getInstitucionId();
		return result;
	}

	private Integer getPaisId() {
		val result = preferences.getPaisId();
		return result;
	}

	public String getTitle() {
		String result;
		if (isNewEntity()) {
			result = "Agregar nueva ruta";
		} else {
			result = "Editar ruta :" + model.getRuta().getCodigo();
		}
		return result;
	}

	public String getActionLabel() {
		String result;
		if (isNewEntity()) {
			result = "Agregar";
		} else {
			result = "Actualizar";
		}
		return result;
	}

	public boolean init() {
		boolean result = false;

		this.newEntity = true;
		try {
			reset();
			this.model = new RutaViewModel();
			result = true;
		} catch (RuntimeException e) {
			FacesMessages.fatal(e.getMessage());
		}

		return result;
	}

	public boolean init(Integer id, Integer version) {
		boolean result = false;

		this.newEntity = false;
		try {
			reset();
			val optional = rutasService.findById(id);
			if (optional.isPresent()) {
				this.model = asViewModel(optional.get());
				this.model.getRuta().setVersion(version);
				result = true;
			} else {
				FacesMessages.error("No se encontró la ruta con id=" + id);
			}
		} catch (RuntimeException e) {
			FacesMessages.fatal(e.getMessage());
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
			FacesMessages.fatal(e.getMessage());
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
			FacesMessages.fatal(e.getMessage());
		}
	}

	public void onChangeIdentificacion() {
		val institucion = getInstitucionId();
		val tipoId = model.getTipoIdentificacionId();
		val identificacion = model.getNumeroIdentificacionId();

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

	private void reset() {
		loadTiposDocumento();
		loadDepartamentos();
		loadDirecciones();
	}

	private void loadTiposDocumento() {
		val list = ciberService.findAllTiposDocumento();
		listaTipoDocumento = new ArrayList<>();
		for (val e : list) {
			listaTipoDocumento.add(new SelectItem(e.getId(), e.getAbreviatura()));
		}
	}

	private void loadDepartamentos() {
		val list = ciberService.findAllDepartamentosByPais(getPaisId());
		listaDepartamento = new ArrayList<>();
		for (val e : list) {
			listaDepartamento.add(new SelectItem(e.getDepartamentoId(), e.getNombre()));
		}
	}

	private void loadDirecciones() {
		val list = direccionesService.findAllByInstitucionId(getInstitucionId());
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

	private RutaViewModel asViewModel(RutaDto ruta) {
		val monitor = ciberService.findUsuarioById(ruta.getMonitorId()).get();
		val direccion = direccionesService.findById(ruta.getDireccionSedeId()).get();

		val result = new RutaViewModel();
		result.setRuta(ruta);

		result.setMonitor(monitor);
		result.setTipoIdentificacionId(monitor.getTipoId());
		result.setNumeroIdentificacionId(monitor.getNumeroId());
		result.setNombreMonitor(monitor.getNombreCompleto());

		result.setUsarDireccionExistente(true);
		result.setDepartamentoId(direccion.getDepartamentoId());
		result.setCiudadId(direccion.getCiudadId());
		result.setDireccionSede(direccion.getDireccion());

		return result;
	}

	private DireccionDto asDireccion(RutaViewModel vm) {
		val result = new DireccionDto();

		result.setInstitucionId(getInstitucionId());

		result.setEstadoId(DireccionDto.ESTADO_NO_PROCESADA);

		result.setPaisId(getPaisId());
		result.setDepartamentoId(vm.getDepartamentoId());
		result.setCiudadId(vm.getCiudadId());
		result.setDireccion(vm.getDireccionSede());

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
