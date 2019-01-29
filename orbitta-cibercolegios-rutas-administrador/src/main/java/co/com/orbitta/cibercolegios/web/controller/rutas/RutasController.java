package co.com.orbitta.cibercolegios.web.controller.rutas;

import static java.util.stream.Collectors.toList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RestController;

import co.com.orbitta.cibercolegios.rutas.service.api.RutaService;
import co.com.orbitta.cibercolegios.rutas.service.api.readonly.CiberService;
import co.com.orbitta.cibercolegios.web.controller.rutas.managers.RutasAsignacionManager;
import co.com.orbitta.cibercolegios.web.controller.rutas.managers.RutasEdicionManager;
import co.com.orbitta.cibercolegios.web.controller.rutas.models.ItemRutaViewModel;
import co.com.orbitta.cibercolegios.web.util.UserPreferences;
import lombok.Getter;
import lombok.Setter;
import lombok.val;

@RestController
@Scope("view")
@Setter
@Getter
public class RutasController implements Serializable, RutasConstantes {

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

	private List<ItemRutaViewModel> items;

	private List<ItemRutaViewModel> selectedItems;

	private boolean newEntity = false;

	private boolean editing = false;

	private boolean assigning = false;

	private Integer getInstitucionId() {
		val result = preferences.getInstitucionId();
		return result;
	}

	public boolean isEditable() {
		int size = selectedItems.size();

		boolean result = size == 1;
		return result;
	}

	public boolean isDeletable() {
		long inscritos = this.selectedItems.stream().mapToLong(a -> a.getInscritos()).sum();
		int size = selectedItems.size();

		boolean result = (size > 0) && (inscritos == 0);
		return result;
	}

	@PostConstruct
	public void init() {
		reset();
	}

	private void reset() {
		resetActions();

		this.items = getData();
		this.selectedItems = new ArrayList<>();
	}

	private void resetActions() {
		this.setNewEntity(false);
		this.setEditing(false);
		this.setAssigning(false);
	}

	private List<ItemRutaViewModel> getData() {
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
			}).sorted((a, b) -> a.getRuta().getCodigo().compareTo(b.getRuta().getCodigo())).collect(toList());

			return result;
		} else {
			return new ArrayList<ItemRutaViewModel>();
		}
	}

	public int getSelectectedCount() {
		return this.selectedItems.size();
	}

	// ---------------------------------------------------------------------------------
	public void addListener(ActionEvent event) {
		resetActions();

		boolean success = edicionManager.initAdd();

		if (success) {
			this.setNewEntity(true);
			this.setEditing(true);
		}
	}

	public void editListener(ActionEvent event) {
		val optional = this.selectedItems.stream().findFirst();
		if (optional.isPresent()) {
			resetActions();

			int id = optional.get().getRuta().getId();
			int version = optional.get().getRuta().getVersion();
			boolean success = edicionManager.initEdit(id, version);

			if (success) {
				this.setNewEntity(false);
				this.setEditing(true);
			}
		}
	}

	public void deleteListener(ActionEvent event) {
		for (val item : this.selectedItems) {
			edicionManager.delete(item.getRuta().getId(), item.getRuta().getVersion());
		}
		reset();
	}

	public void assignListener(ItemRutaViewModel item) {
		resetActions();

		boolean success = asignacionManager.init(item);

		if (success) {
			this.assigning = true;
		}
	}

	public void save() {
		edicionManager.save();
		reset();
	}

	public void assign() {
		asignacionManager.save();
		reset();
	}
}
