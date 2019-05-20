package com.tbf.cibercolegios.api.routes.controllers.routes;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RestController;

import com.tbf.cibercolegios.api.ciber.services.api.CiberService;
import com.tbf.cibercolegios.api.routes.controllers.routes.models.ItemRutaViewModel;
import com.tbf.cibercolegios.api.routes.controllers.util.FacesMessages;
import com.tbf.cibercolegios.api.routes.controllers.util.UserPreferences;
import com.tbf.cibercolegios.api.routes.services.api.PasajeroService;
import com.tbf.cibercolegios.api.routes.services.api.RutaService;
import com.tbf.cibercolegios.core.CrudController;

import lombok.Getter;
import lombok.Setter;
import lombok.val;

@RestController
@Scope("view")
@Setter
@Getter
public class RutasCrudController extends CrudController<ItemRutaViewModel, String> {

	private static final long serialVersionUID = 1L;

	@Autowired
	private UserPreferences preferences;

	@Autowired
	private CiberService ciberService;

	@Autowired
	private RutaService rutasService;

	@Autowired
	private PasajeroService pasajerosService;

	@Override
	protected void init() {
		super.init();
		this.reset();
	}

	@Override
	protected void populate() {
		find();
	}

	@Override
	protected void executeFind() {
		getModels().addAll(findAllRutas());
	}

	private List<ItemRutaViewModel> findAllRutas() {
		val institucionId = getInstitucionId();
		if (institucionId != null) {
			val result = rutasService.findAllByInstitucionId(institucionId).stream().map(m -> {
				val monitor = ciberService.findUsuarioById(m.getMonitorId()).get();
				val inscritos = pasajerosService.countByRutaId(m.getId());

				ItemRutaViewModel r = new ItemRutaViewModel();
				r.setRuta(m);
				r.setNombreMonitor(monitor.getNombreCompleto());
				r.setInscritos(inscritos);
				return r;
			}).sorted((a, b) -> a.getRuta().getCodigo().compareTo(b.getRuta().getCodigo())).collect(toList());

			return result;
		} else {
			return new ArrayList<ItemRutaViewModel>();
		}
	}

	private Integer getInstitucionId() {
		val result = preferences.getInstitucionId();
		return result;
	}

	public boolean isEditable() {
		int size = getSelection().size();
		boolean result = size == 1;
		return result;
	}

	public boolean isDeletable() {
		int size = getSelection().size();
		long inscritos = this.getSelection().stream().mapToLong(ItemRutaViewModel::getInscritos).sum();

		boolean result = (size > 0) && (inscritos == 0);
		return result;
	}

	public void delete() {
		getSelection().forEach(item -> {
			delete(item);
		});
		reset();
	}

	public void delete(ItemRutaViewModel item) {
		Integer id = item.getRuta().getId();
		try {
			val optional = rutasService.findById(id);
			if (optional.isPresent()) {
				rutasService.delete(id, item.getRuta().getVersion());

				val summary = String.format("La ruta con código %s ha sido eliminada", optional.get().getCodigo());
				FacesMessages.info("Ruta eliminada", summary);
			} else {
				FacesMessages.error("No se encontró la ruta con id=" + id);
			}
		} catch (RuntimeException e) {
			FacesMessages.fatal(e);
		}
	}
	
	public int getSelectionCount() {
		return this.getSelection().size();
	}

}
