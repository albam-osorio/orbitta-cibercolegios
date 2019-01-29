package co.com.orbitta.cibercolegios.web.backing.rutas;

import static java.util.stream.Collectors.toList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RestController;

import co.com.orbitta.cibercolegios.rutas.service.api.RutaService;
import co.com.orbitta.cibercolegios.rutas.service.api.readonly.CiberService;
import co.com.orbitta.cibercolegios.web.backing.rutas.models.ItemRutaViewModel;
import co.com.orbitta.cibercolegios.web.backing.sesion.UserPreferences;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.val;

@RestController
@Scope("view")
@Setter
@Getter
public class RutasBacking implements Serializable, RutasConstantes {

	private static final long serialVersionUID = 1L;

	@Autowired
	private UserPreferences preferences;

	@Autowired
	private CiberService ciberService;

	@Autowired
	private RutaService rutasService;

	@Autowired
	private RutasEdicionManager edicionManager;

	@Autowired
	private RutasAsignacionManager asignacionManager;

	private boolean edit = false;

	private boolean asign = false;

	@Setter(value = AccessLevel.NONE)
	private List<ItemRutaViewModel> data;

	private ItemRutaViewModel selected;

	private Integer getInstitucionId() {
		val result = preferences.getInstitucionId();
		return result;
	}

	public boolean isEditable() {
		return selected != null;
	}

	public boolean isDeletable() {
		return isEditable() && (selected.getInscritos() == 0);
	}

	public List<ItemRutaViewModel> getData() {
		return data;
	}

	@PostConstruct
	public void init() {
		reset();
	}

	public void onRowSelect(ItemRutaViewModel item) {
		if (item != null) {
			selected = item;
		} else {
			selected = null;
		}
	}

	public void onRowDeselect(ItemRutaViewModel item) {
		selected = null;
	}

	public void add() {
		resetActions();

		boolean success = edicionManager.init();

		if (success) {
			this.edit = true;
		}
	}

	public void edit(Integer id, Integer version) {
		resetActions();

		boolean success = edicionManager.init(id, version);

		if (success) {
			this.edit = true;
		}
	}

	public void save() {
		edicionManager.save();
		reset();
	}

	public void delete(Integer id, Integer version) {
		edicionManager.delete(id, version);
		reset();
	}

	public void asign(ItemRutaViewModel item) {
		resetActions();

		boolean success = asignacionManager.init(item);

		if (success) {
			this.asign = true;
		}
	}

	public void confirmAssignment() {
		asignacionManager.save();
		reset();
	}

	public void cancel() {
		this.reset();
	}

	public String back() {
		val result = RutasConstantes.URL_HOME;
		return result;
	}

	private void reset() {
		resetActions();

		data = new ArrayList<>();
		data.addAll(getRutas());
		this.selected = null;
	}

	private void resetActions() {
		this.edit = false;
		this.asign = false;
	}

	private List<ItemRutaViewModel> getRutas() {
		val institucionId = getInstitucionId();
		if (institucionId != null) {
			val result = rutasService.findAllByInstitucionId(institucionId).stream().map(m -> {
				val monitor = ciberService.findUsuarioById(m.getMonitorId()).get();
				val inscritos = asignacionManager.getNumeroDeInscritosByRutaId(m.getId());

				ItemRutaViewModel r = new ItemRutaViewModel();
				r.setRuta(m);
				r.setNombreMonitor(monitor.getNombreCompleto());
				r.setInscritos(inscritos);
				return r;
			}).collect(toList());

			return result;
		} else {
			return new ArrayList<ItemRutaViewModel>();
		}
	}
}
