package com.tbf.cibercolegios.api.routes.controllers.routes;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;

import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RestController;

import com.tbf.cibercolegios.api.ciber.model.graph.NivelEducativoDto;
import com.tbf.cibercolegios.api.ciber.model.graph.UsuarioDto;
import com.tbf.cibercolegios.api.ciber.services.api.CiberService;
import com.tbf.cibercolegios.api.routes.controllers.routes.models.ItemRutaViewModel;
import com.tbf.cibercolegios.api.routes.controllers.routes.models.PasajeroViewModel;
import com.tbf.cibercolegios.api.routes.controllers.util.FacesMessages;
import com.tbf.cibercolegios.api.routes.controllers.util.UserPreferences;
import com.tbf.cibercolegios.api.routes.model.graph.PasajeroDto;
import com.tbf.cibercolegios.api.routes.services.api.PasajeroService;
import com.tbf.cibercolegios.api.routes.services.api.RutaService;
import com.tbf.cibercolegios.core.DialogCommandController;

import lombok.Getter;
import lombok.Setter;
import lombok.val;

@RestController
@Scope("view")
@Setter
@Getter
public class RutasDialogAssignController extends DialogCommandController<ItemRutaViewModel, String> {

	private static final long serialVersionUID = 1L;

	@Autowired
	private UserPreferences preferences;

	@Autowired
	private CiberService ciberService;

	@Autowired
	private RutaService rutasService;

	@Autowired
	private PasajeroService pasajerosService;

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
	private List<PasajeroDto> pasajeros;

	private List<PasajeroViewModel> inscritos;

	private List<PasajeroViewModel> noInscritos;

	private DualListModel<PasajeroViewModel> dualListModel;

	// ----------------------------------------------------------------------------------------------------
	// --INICIALIZADORES
	// ----------------------------------------------------------------------------------------------------
	@Override
	protected void init() {
		niveles = new ArrayList<>();
		programas = new ArrayList<>();
		grados = new ArrayList<>();
		noInscritos = new ArrayList<>();

		this.niveles.addAll(findNivelesEducativos(getInstitucionId()));
	}

	@Override
	protected void populate() {
		this.pasajeros = findPasajeros(getModel().getRuta().getId());
		this.inscritos = asInscritos(this.pasajeros);

		resetProgramas();
		this.setNivelId(null);
	}

	private void resetProgramas() {
		resetGrados();

		setProgramaId(null);
		this.programas.clear();
	}

	private void resetGrados() {
		resetEstudiantes();

		setGradoId(null);
		this.grados.clear();
	}

	private void resetEstudiantes() {
		this.noInscritos.clear();
		this.dualListModel = new DualListModel<>(noInscritos, inscritos);
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
				this.programas.clear();
				this.programas.addAll(findProgramas(getInstitucionId(), jornadaId, nivelId));
			}
		}
	}

	public void onChangePrograma() {
		resetGrados();

		if (jornadaId != null && nivelId != null && programaId != null) {
			this.grados.clear();
			this.grados.addAll(findGrados(getInstitucionId(), jornadaId, nivelId, programaId));
		}
	}

	public void onChangeGrado() {
		resetEstudiantes();

		if (jornadaId != null && nivelId != null && programaId != null && gradoId != null) {

			this.noInscritos.clear();
			this.noInscritos.addAll(findNoInscritos(getInstitucionId(), jornadaId, nivelId, programaId, gradoId));

			this.dualListModel = new DualListModel<>(noInscritos, inscritos);
		}
	}

	// -----------------------------------------------------------------------------------
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
		dualListModel = new DualListModel<>(noInscritos, inscritos);
	}

	private void onTransferAdd(TransferEvent event) {
		val transferidos = new ArrayList<PasajeroViewModel>();
		val descartados = new ArrayList<PasajeroViewModel>();
		for (Object item : event.getItems()) {
			val pasajero = ((PasajeroViewModel) item);

			if (inscritos.size() + transferidos.size() < getModel().getRuta().getCapacidadMaxima()) {
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

	private void onTransferRemove(TransferEvent event) {
		val transferidos = new ArrayList<PasajeroViewModel>();
		for (Object item : event.getItems()) {
			val pasajero = ((PasajeroViewModel) item);

			boolean transferir = false;
			if (gradoId != null && programaId != null && jornadaId != null && nivelId != null) {
				if (gradoId.equals(pasajero.getGradoId())) {
					if (programaId.equals(pasajero.getProgramaId())) {
						if (jornadaId.equals(pasajero.getJornadaId())) {
							if (nivelId.equals(pasajero.getNivelId())) {
								transferir = true;
							}
						}
					}
				}
			}

			pasajero.setAdicionado(false);
			if (pasajero.isMiembro()) {
				pasajero.setRemovido(true);
			}
			if (transferir) {
				transferidos.add(pasajero);
			}
		}

		noInscritos.addAll(transferidos);
		inscritos.removeAll(event.getItems());
	}

	public void onReorder(AjaxBehaviorEvent event) {
		try {
			inscritos.clear();
			inscritos.addAll(dualListModel.getTarget());
		} catch (Exception e) {
			FacesMessages.fatal(e);
		}

	}

	// ----------------------------------------------------------------------------------------------------
	// -- COMANDOS
	// ----------------------------------------------------------------------------------------------------
	@Override
	protected void executeSubmit(List<String> errors) {
		int n = inscritos.size() - 1;
		int i = 0;
		for (val p : inscritos) {
			p.setSecuenciaIda(i);
			p.setSecuenciaRetorno(n - i);
			i++;
		}

		val updated = new ArrayList<PasajeroDto>();
		for (val p : inscritos) {
			val optional = pasajeros.stream().filter(a -> a.getUsuarioId() == p.getUsuarioId()).findFirst();
			
			PasajeroDto pasajero;
			if (!optional.isPresent()) {
				//El pasajero inscrito no esta en los que originalmente estaban inscritos en la ruta
				val op = pasajerosService.findByUsuarioId(p.getUsuarioId());
				pasajero = op.get();
				pasajero.setRutaId(getModel().getRuta().getId());
				pasajero.setEstadoId(PasajeroDto.ESTADO_INACTIVO);
			} else {
				pasajero = optional.get();
			}

			//Existiese o no en la ruta, a todos se les debe asignar las secuencias ida/retorno
			pasajero.setSecuenciaIda(p.getSecuenciaIda());
			pasajero.setSecuenciaRetorno(p.getSecuenciaRetorno());
			updated.add(pasajero);
		}

		
		//Los pasajeros que originalmente no estaban en la ruta pero que ahora ya no estan en los inscritos
		for (val p : pasajeros) {
			boolean found = inscritos.stream().anyMatch(a -> a.getUsuarioId() == p.getUsuarioId());
			if (!found) {
				p.setRutaId(null);
				p.setEstadoId(PasajeroDto.ESTADO_INACTIVO);
				updated.add(p);
			}
		}

		pasajerosService.update(updated);
	}

	// ----------------------------------------------------------------------------------------------------
	// --ACCESO A DATOS
	// ----------------------------------------------------------------------------------------------------
	private List<PasajeroDto> findPasajeros(Integer id) {
		val result = pasajerosService.findAllByRutaId(id);
		return result;
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
		val list = ciberService.findAllGradosByPrograma(institucionId, jornadaId, nivelId, programaId);

		val result = list.stream().map(a -> new SelectItem(a.getId(), a.getDescripcion())).collect(toList());
		return result;
	}

	private List<PasajeroViewModel> findNoInscritos(int institucionId, int jornadaId, int nivelId, int programaId,
			int gradoId) {
		val list = ciberService.findAllPajaserosSinRutaByGrado(institucionId, jornadaId, nivelId, programaId, gradoId);

		val result = new ArrayList<PasajeroViewModel>();
		for (val usuario : list) {
			PasajeroViewModel p = null;

			val optional = inscritos.stream().filter(a -> a.getUsuarioId() == usuario.getId()).findFirst();
			if (optional.isPresent()) {
				p = optional.get();
			} else {
				boolean esMiembro = false;
				boolean include = true;

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
				p.setNivelId(nivelId);
				p.setJornadaId(jornadaId);
				p.setProgramaId(programaId);
				p.setGradoId(gradoId);
			}

		}

		return result;
	}

	// -----------------------------------------------------------------------------------
	// -- Auxiliares
	// -----------------------------------------------------------------------------------
	private Integer getInstitucionId() {
		val result = preferences.getInstitucionId();
		return result;
	}

	public long getNumeroDeInscritosByRutaId(Integer id) {
		val result = pasajerosService.countByRutaId(id);
		return result;
	}

	private List<PasajeroViewModel> asInscritos(List<PasajeroDto> pasajeros) {
		val result = new ArrayList<PasajeroViewModel>();

		val list = pasajeros.stream().sorted((a, b) -> Integer.compare(a.getSecuenciaIda(), b.getSecuenciaIda()))
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
}
