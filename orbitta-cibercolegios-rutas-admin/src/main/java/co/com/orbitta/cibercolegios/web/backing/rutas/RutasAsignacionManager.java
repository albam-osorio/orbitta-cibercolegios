package co.com.orbitta.cibercolegios.web.backing.rutas;

import static java.util.stream.Collectors.toList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;

import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.com.orbitta.cibercolegios.rutas.dto.PasajeroDto;
import co.com.orbitta.cibercolegios.rutas.dto.readonly.NivelEducativoDto;
import co.com.orbitta.cibercolegios.rutas.dto.readonly.UsuarioDto;
import co.com.orbitta.cibercolegios.rutas.service.api.PasajeroService;
import co.com.orbitta.cibercolegios.rutas.service.api.RutaService;
import co.com.orbitta.cibercolegios.rutas.service.api.readonly.CiberService;
import co.com.orbitta.cibercolegios.web.backing.rutas.models.ItemRutaViewModel;
import co.com.orbitta.cibercolegios.web.backing.rutas.models.PasajeroViewModel;
import co.com.orbitta.cibercolegios.web.backing.sesion.UserPreferences;
import lombok.Getter;
import lombok.Setter;
import lombok.val;
import net.bootsfaces.utils.FacesMessages;

@Component
@Setter
@Getter
public class RutasAsignacionManager implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private UserPreferences preferences;

	@Autowired
	private CiberService ciberService;

	@Autowired
	private RutaService rutasService;

	@Autowired
	private PasajeroService pasajerosService;

	private List<NivelEducativoDto> niveles;

	private List<SelectItem> programas;

	private List<SelectItem> grados;

	private Integer nivelId;

	private Integer jornadaId;

	private Integer programaId;

	private Integer gradoId;

	private ItemRutaViewModel model;

	private List<PasajeroDto> pasajeros;

	private List<PasajeroViewModel> inscritos;

	private List<PasajeroViewModel> noInscritos;

	private DualListModel<PasajeroViewModel> dualListModel;

	private Integer getInstitucionId() {
		val result = preferences.getInstitucionId();
		return result;
	}

	public boolean init(ItemRutaViewModel item) {
		boolean result = false;

		try {
			this.model = item;
			this.pasajeros = findPasajeros(model.getRuta().getId());
			this.inscritos = findInscritos();

			resetProgramas();
			this.setNivelId(null);
			this.niveles = findNivelesEducativos(getInstitucionId());

			result = true;
		} catch (RuntimeException e) {
			FacesMessages.fatal(e.getMessage());
		}

		return result;
	}

	private List<PasajeroDto> findPasajeros(Integer id) {
		val result = pasajerosService.findAllByRutaId(id);
		return result;
	}

	private List<PasajeroViewModel> findInscritos() {
		val result = new ArrayList<PasajeroViewModel>();

		val list = getPasajeros().stream().sorted((a, b) -> Integer.compare(a.getSecuenciaIda(), b.getSecuenciaIda()))
				.collect(toList());

		for (val pasajero : list) {
			val optional = ciberService.findUsuarioById(pasajero.getUsuarioId());
			if (optional.isPresent()) {
				val vm = asPasajeroViewModel(pasajero, optional.get());
				result.add(vm);
			}
		}

		return result;
	}

	private PasajeroViewModel asPasajeroViewModel(PasajeroDto pasajero, UsuarioDto usuario) {
		val result = new PasajeroViewModel();
		result.setUsuarioId(pasajero.getUsuarioId());
		result.setNombreEstudiante(usuario.getNombreCompleto());
		result.setSecuenciaIda(pasajero.getSecuenciaIda());
		result.setSecuenciaRetorno(pasajero.getSecuenciaRetorno());
		result.setMiembro(true);
		result.setAdicionado(false);
		result.setRemovido(false);
		return result;
	}

	public void onChangeNivelAcademico() {
		resetProgramas();

		val optional = getNiveles().stream().filter(a -> a.getId().equals(getNivelId())).findFirst();
		if (optional.isPresent()) {
			val nivel = optional.get();
			setJornadaId(nivel.getJornadaId());

			this.programas = findProgramas(getInstitucionId(), getJornadaId(), getNivelId());
		}
	}

	public void onChangePrograma() {
		resetGrados();

		this.grados = findGrados(getInstitucionId(), getJornadaId(), getNivelId(), getProgramaId());
	}

	public void onChangeGrado() {
		resetEstudiantes();

		this.noInscritos = findNoInscritos(getInstitucionId(), getJornadaId(), getNivelId(), getProgramaId(),
				getGradoId());
		this.dualListModel = new DualListModel<>(getNoInscritos(), getInscritos());
	}

	public void onTransfer(TransferEvent event) {
		if (event.isAdd()) {
			onAddTransfer(event);
		} else {
			onRemoveTransfer(event);
		}
		dualListModel = new DualListModel<>(getNoInscritos(), getInscritos());
	}

	private void onAddTransfer(TransferEvent event) {
		val transferidos = new ArrayList<PasajeroViewModel>();
		val descartados = new ArrayList<PasajeroViewModel>();
		for (Object item : event.getItems()) {
			val pasajero = ((PasajeroViewModel) item);

			if (inscritos.size() + transferidos.size() < model.getRuta().getCapacidadMaxima()) {
				if (pasajero.isMiembro()) {
					pasajero.setRemovido(false);
				} else {
					pasajero.setAdicionado(true);
				}
				transferidos.add(pasajero);
			} else {
				descartados.add(pasajero);
			}
		}

		inscritos.addAll(transferidos);
		noInscritos.removeAll(transferidos);

		if (!descartados.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			val msg = String.format(
					"La capacidad máxima de la ruta es de solo %d pasajeros. Los siguientes estudiantes no pudieron ser inscritos en esta ruta:",
					getModel().getRuta().getCapacidadMaxima());

			sb.append(msg);
			for (val p : descartados) {
				sb.append("\n");
				sb.append(p.getNombreEstudiante());
			}

			FacesMessages.warning(sb.toString());
		}
	}

	private void onRemoveTransfer(TransferEvent event) {
		val transferidos = new ArrayList<PasajeroViewModel>();
		for (Object item : event.getItems()) {
			val pasajero = ((PasajeroViewModel) item);

			boolean transferir = false;
			if (getGradoId().equals(pasajero.getGradoId())) {
				if (getProgramaId().equals(pasajero.getProgramaId())) {
					if (getJornadaId().equals(pasajero.getJornadaId())) {
						if (getNivelId().equals(pasajero.getNivelId())) {
							transferir = true;
						}
					}
				}
			}

			if (transferir) {
				pasajero.setAdicionado(false);
				if (pasajero.isMiembro()) {
					pasajero.setRemovido(true);
				}
				transferidos.add(pasajero);
			}
		}

		noInscritos.addAll(transferidos);
		inscritos.removeAll(event.getItems());
	}

	public void onReorder(AjaxBehaviorEvent event) {
		getInscritos().clear();
		getInscritos().addAll(dualListModel.getTarget());
	}

	public void save() {
		try {
			int n = getInscritos().size() - 1;
			int i = 0;
			for (val p : getInscritos()) {
				p.setSecuenciaIda(i);
				p.setSecuenciaRetorno(n - i);
				i++;
			}

			val inserted = new ArrayList<PasajeroDto>();
			val updated = new ArrayList<PasajeroDto>();
			for (val p : getInscritos()) {
				val optional = getPasajeros().stream().filter(a -> a.getUsuarioId() == p.getUsuarioId()).findFirst();
				if (!optional.isPresent()) {
					val pasajero = new PasajeroDto();
					pasajero.setRutaId(getModel().getRuta().getId());
					pasajero.setUsuarioId(p.getUsuarioId());
					pasajero.setSecuenciaIda(p.getSecuenciaIda());
					pasajero.setSecuenciaRetorno(p.getSecuenciaRetorno());
					pasajero.setEstadoId(PasajeroDto.ESTADO_INACTIVO);
					inserted.add(optional.get());
				} else {
					val pasajero = optional.get();
					pasajero.setSecuenciaIda(p.getSecuenciaIda());
					pasajero.setSecuenciaRetorno(p.getSecuenciaRetorno());
					updated.add(optional.get());
				}
			}

			val deleted = new ArrayList<PasajeroDto>();
			for (val p : getPasajeros()) {
				boolean found = getInscritos().stream().anyMatch(a -> a.getUsuarioId() == p.getUsuarioId());
				if (!found) {
					deleted.add(p);
				}
			}

			for (val p : deleted) {
				pasajerosService.delete(p.getId(), p.getVersion());
			}

			pasajerosService.update(updated);
			pasajerosService.create(inserted);
		} catch (RuntimeException e) {
			FacesMessages.fatal(e.getMessage());
		}
	}

	private void resetProgramas() {
		resetGrados();

		setProgramaId(null);
		this.programas = new ArrayList<>();
	}

	private void resetGrados() {
		resetEstudiantes();

		setGradoId(null);
		this.grados = new ArrayList<>();
	}

	private void resetEstudiantes() {
		this.noInscritos = new ArrayList<>();
		this.dualListModel = new DualListModel<>(getNoInscritos(), getInscritos());
	}

	private List<NivelEducativoDto> findNivelesEducativos(int institucionId) {
		val result = ciberService.findAllNivelesEducativosByInstitucion(institucionId);
		return result;
	}

	private List<SelectItem> findProgramas(int institucionId, int jornadaId, int nivelId) {
		val list = ciberService.findAllProgramasByNivelEducativo(institucionId, jornadaId, nivelId);

		val result = list.stream().map(a -> new SelectItem(a.getId(), a.getDescripcion())).collect(toList());
		return result;
	}

	private List<SelectItem> findGrados(int institucionId, int jornadaId, int nivelId, int programaId) {
		val list = ciberService.findAllGradosByPrograma(institucionId, jornadaId, nivelId,
				programaId);

		val result = list.stream().map(a -> new SelectItem(a.getId(), a.getDescripcion())).collect(toList());
		return result;
	}

	private List<PasajeroViewModel> findNoInscritos(int institucionId, int jornadaId, int nivelId, int programaId,
			int gradoId) {
		val list = ciberService.findAllPajaserosSinRutaByGrado(institucionId, jornadaId, nivelId, programaId, gradoId);

		val result = new ArrayList<PasajeroViewModel>();
		for (val usuario : list) {
			PasajeroViewModel p = null;

			val optional = this.getInscritos().stream().filter(a -> a.getUsuarioId() == usuario.getId()).findFirst();
			if (optional.isPresent()) {
				p = optional.get();
			} else {
				boolean esMiembro = false;
				boolean include = false;

				val op = pasajerosService.findByUsuarioId(usuario.getId());
				if (op.isPresent()) {
					val pasajero = op.get();
					if (pasajero.getRutaId() == getModel().getRuta().getId()) {
						esMiembro = true;
						include = true;
					}
				} else {
					include = true;
				}

				if (include) {
					p = new PasajeroViewModel();

					p.setUsuarioId(usuario.getId());
					p.setNombreEstudiante(usuario.getNombreCompleto());
					p.setSecuenciaIda(0);
					p.setSecuenciaRetorno(0);
					p.setMiembro(esMiembro);
					p.setAdicionado(false);
					p.setRemovido(esMiembro);

					result.add(p);
				}
			}

			if (p != null && p.getGradoId() == null) {
				p.setNivelId(getNivelId());
				p.setJornadaId(getJornadaId());
				p.setProgramaId(getProgramaId());
				p.setGradoId(getGradoId());
			}

		}

		return result;
	}

	public long getNumeroDeInscritosByRutaId(Integer id) {
		val result = pasajerosService.countByRutaId(id);
		return result;
	}
}
