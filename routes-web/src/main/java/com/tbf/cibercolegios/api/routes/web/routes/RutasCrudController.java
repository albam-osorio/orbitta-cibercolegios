package com.tbf.cibercolegios.api.routes.web.routes;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RestController;

import com.tbf.cibercolegios.api.ciber.model.graph.UsuarioDto;
import com.tbf.cibercolegios.api.ciber.services.api.CiberService;
import com.tbf.cibercolegios.api.routes.model.graph.web.RutaConCapacidadDto;
import com.tbf.cibercolegios.api.routes.models.routes.ItemRutaViewModel;
import com.tbf.cibercolegios.api.routes.services.api.PasajeroService;
import com.tbf.cibercolegios.api.routes.services.api.RutaService;
import com.tbf.cibercolegios.api.routes.web.utils.FacesMessages;
import com.tbf.cibercolegios.api.routes.web.utils.UserProfile;
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
	private UserProfile profile;

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
	protected boolean populate() {
		find();
		return true;
	}

	// -----------------------------------------------------------------------------------
	// --
	// -----------------------------------------------------------------------------------
	@Override
	protected void executeFind() {
		getModels().addAll(findAllRutas());
	}

	private List<ItemRutaViewModel> findAllRutas() {
		val institucionId = profile.getInstitucionId();

		if (institucionId != null) {
			val rutas = rutasService.findAllByInstitucionId(institucionId);
			val rutasConCapacidad = rutasService.findAllRutasConCapacidadByInstitucionId(institucionId, null);

			val result = rutas.stream().map(ruta -> {
				UsuarioDto monitor = ciberService
						.findUsuarioMonitorByInstitucionIdAndUsuarioId(institucionId, ruta.getMonitorId()).get();

				RutaConCapacidadDto rutaConCapacidad = rutasConCapacidad.stream().filter(a -> a.getId().equals(ruta.getId()))
						.findFirst().get();

				ItemRutaViewModel r = new ItemRutaViewModel();
				r.setRuta(ruta);
				r.setNombreMonitor(monitor.getNombreCompleto());
				r.setOcupacionAm(rutaConCapacidad.getOcupacionAm());
				r.setOcupacionPm(rutaConCapacidad.getOcupacionPm());
				return r;
			}).sorted((a, b) -> a.getRuta().getCodigo().compareTo(b.getRuta().getCodigo())).collect(toList());

			return result;
		} else {
			return new ArrayList<>();
		}
	}

	// -----------------------------------------------------------------------------------
	// --
	// -----------------------------------------------------------------------------------
	public boolean isEditable() {
		int size = getSelectionCount();
		boolean result = size == 1;
		return result;
	}

	public boolean isDeletable() {
		int size = getSelectionCount();
		long ocupacionAm = this.getSelection().stream().mapToLong(ItemRutaViewModel::getOcupacionAm).sum();
		long ocupacionPm = this.getSelection().stream().mapToLong(ItemRutaViewModel::getOcupacionPm).sum();

		boolean result = (size > 0) && (ocupacionAm == 0) && (ocupacionPm == 0);
		return result;
	}

	public int getSelectionCount() {
		return this.getSelection().size();
	}

	// -----------------------------------------------------------------------------------
	// --
	// -----------------------------------------------------------------------------------
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
				FacesMessages.error("No se encontró la ruta con código=" + item.getRuta().getCodigo());
			}
		} catch (RuntimeException e) {
			error(e);
		}
	}
}
