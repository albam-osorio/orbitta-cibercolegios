package com.tbf.cibercolegios.api.routes.web.users;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RestController;

import com.tbf.cibercolegios.api.ciber.model.graph.NivelEducativoDto;
import com.tbf.cibercolegios.api.ciber.model.graph.UsuarioDto;
import com.tbf.cibercolegios.api.ciber.services.api.CiberService;
import com.tbf.cibercolegios.api.routes.web.utils.UserProfile;
import com.tbf.cibercolegios.core.CrudController;

import lombok.Getter;
import lombok.Setter;
import lombok.val;

@RestController
@Scope("view")
@Setter
@Getter
public class UsuariosManualController extends CrudController<UsuarioDto, String> {

	private static final long serialVersionUID = 1L;

	// -----------------------------------------------------------------------------------
	// --
	// -----------------------------------------------------------------------------------
	@Autowired
	private UserProfile profile;

	@Autowired
	private CiberService ciberService;

	// -----------------------------------------------------------------------------------
	// --
	// -----------------------------------------------------------------------------------
	private List<NivelEducativoDto> niveles;

	private List<SelectItem> nivelesList;

	private List<SelectItem> programasList;

	private List<SelectItem> gradosList;

	private List<SelectItem> cursosList;

	private Integer nivelId;

	private Integer jornadaId;

	private Integer programaId;

	private Integer gradoId;

	private Integer cursoId;

	private boolean activo = false;

	@Override
	protected void init() {
		super.init();

		this.niveles = new ArrayList<>();
		this.nivelesList = new ArrayList<>();
		this.programasList = new ArrayList<>();
		this.gradosList = new ArrayList<>();
		this.cursosList = new ArrayList<>();
	}

	@Override
	protected void clear() {
		super.clear();

		this.niveles.clear();
		this.nivelesList.clear();
		this.programasList.clear();
		this.gradosList.clear();
		this.cursosList.clear();

		this.nivelId = null;
		this.jornadaId = null;
		this.programaId = null;
		this.gradoId = null;
		this.cursoId = null;

		this.activo = false;
	}

	@Override
	protected boolean populate() {
		val institucionId = profile.getInstitucionId();
		if (institucionId == null) {
			val format = "No se inicializo el valor del identificador de la institución.";
			throw new RuntimeException(format);
		}

		this.niveles.addAll(findNivelesEducativos(institucionId));
		this.nivelesList.addAll(
				this.niveles.stream().map(a -> new SelectItem(a.getId(), a.getDescripcion())).collect(toList()));

		if (niveles.size() == 1) {
			this.nivelId = niveles.stream().findFirst().get().getId();

			onChangeNivel();
		}

		return true;
	}

	public void onChangeNivel() {
		resetProgramas();
		this.jornadaId = null;

		if (this.nivelId != null) {
			val optional = this.niveles.stream().filter(a -> a.getId().equals(this.nivelId)).findFirst();
			if (optional.isPresent()) {
				val nivel = optional.get();
				this.jornadaId = nivel.getJornadaId();

				this.programasList.addAll(findProgramas(profile.getInstitucionId(), this.jornadaId, this.nivelId));

				if (programasList.size() == 1) {
					this.programaId = (Integer) this.programasList.stream().findFirst().get().getValue();

					onChangePrograma();
				}
			}
		}
		this.activo = true;
	}

	public void onChangePrograma() {
		resetGrados();

		if (this.programaId != null) {
			this.gradosList
					.addAll(findGrados(profile.getInstitucionId(), this.jornadaId, this.nivelId, this.programaId));

			if (gradosList.size() == 1) {
				this.gradoId = (Integer) this.gradosList.stream().findFirst().get().getValue();

				onChangeGrado();
			}
		}

		this.activo = true;
	}

	public void onChangeGrado() {
		resetCursos();

		if (this.gradoId != null) {
			this.cursosList.addAll(findCursos(profile.getInstitucionId(), this.jornadaId, this.nivelId, this.programaId,
					this.gradoId));

			if (cursosList.size() == 1) {
				this.cursoId = (Integer) this.cursosList.stream().findFirst().get().getValue();

				onChangeCurso();
			}
		}

		this.activo = true;
	}

	public void onChangeCurso() {
		clearModels();

		if (this.cursoId != null) {
			find();
		}

		this.activo = true;
	}

	// -----------------------------------------------------------------------------------
	// --
	// -----------------------------------------------------------------------------------

	private void resetProgramas() {
		resetGrados();

		this.programasList.clear();
		this.programaId = null;
	}

	private void resetGrados() {
		resetCursos();

		this.gradosList.clear();
		this.gradoId = null;
	}

	private void resetCursos() {
		clearModels();

		this.cursosList.clear();
		this.cursoId = null;
	}

	// -----------------------------------------------------------------------------------
	// --
	// -----------------------------------------------------------------------------------
	@Override
	protected void executeFind() {
		this.getModels().addAll(findUsuarios(profile.getInstitucionId(), this.jornadaId, this.nivelId, this.programaId,
				this.gradoId, this.cursoId));
	}

	// -----------------------------------------------------------------------------------
	// --
	// -----------------------------------------------------------------------------------
	private List<NivelEducativoDto> findNivelesEducativos(int institucionId) {
		val result = ciberService.findAllNivelesEducativosByInstitucionId(institucionId);
		return result;
	}

	private List<SelectItem> findProgramas(int institucionId, int jornadaId, int nivelId) {
		val list = ciberService.findAllProgramasByNivelEducativoId(institucionId, jornadaId, nivelId);

		val result = list.stream().map(a -> new SelectItem(a.getId(), a.getDescripcion())).collect(toList());
		return result;
	}

	private List<SelectItem> findGrados(int institucionId, int jornadaId, int nivelId, int programaId) {
		val list = ciberService.findAllGradosByProgramaId(institucionId, jornadaId, nivelId, programaId);

		val result = list.stream().map(a -> new SelectItem(a.getId(), a.getDescripcion())).collect(toList());
		return result;
	}

	private List<SelectItem> findCursos(int institucionId, int jornadaId, int nivelId, int programaId, int gradoId) {
		val list = ciberService.findAllCursosByGradoId(institucionId, jornadaId, nivelId, programaId, gradoId);

		val result = list.stream().map(a -> new SelectItem(a.getId(), a.getDescripcion())).collect(toList());
		return result;
	}

	private List<UsuarioDto> findUsuarios(int institucionId, int jornadaId, int nivelId, int programaId, int gradoId,
			int cursoId) {
		val result = ciberService.findAllUsuariosEstudiantesByCurso(institucionId, jornadaId, nivelId, programaId,
				gradoId, cursoId).stream().sorted((a, b) -> a.getNombreCompleto().compareTo(b.getNombreCompleto()))
				.collect(toList());

		return result;
	}

}
