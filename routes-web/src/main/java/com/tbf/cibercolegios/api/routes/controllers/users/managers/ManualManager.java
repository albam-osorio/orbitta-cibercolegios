package com.tbf.cibercolegios.api.routes.controllers.users.managers;

import static java.util.stream.Collectors.toList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tbf.cibercolegios.api.ciber.model.graph.GrupoDto;
import com.tbf.cibercolegios.api.ciber.model.graph.NivelEducativoDto;
import com.tbf.cibercolegios.api.ciber.model.graph.UsuarioDto;
import com.tbf.cibercolegios.api.ciber.services.api.CiberService;
import com.tbf.cibercolegios.api.routes.controllers.util.FacesMessages;
import com.tbf.cibercolegios.api.routes.controllers.util.UserPreferences;

import lombok.Getter;
import lombok.Setter;
import lombok.val;

@Component
@Setter
@Getter
public class ManualManager implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private UserPreferences preferences;

	@Autowired
	private CiberService ciberService;

	@Autowired
	private DireccionManager direccionManager;

	private List<NivelEducativoDto> niveles;

	private List<SelectItem> programas;

	private List<SelectItem> grados;

	private List<GrupoDto> grupos;

	private List<UsuarioDto> usuarios;

	private Integer nivelId;

	private Integer jornadaId;

	private Integer programaId;

	private Integer gradoId;

	private GrupoDto grupo;

	private Integer getInstitucionId() {
		val result = preferences.getInstitucionId();
		return result;
	}

	// -----------------------------------------------------------------------------------
	public boolean init() {
		boolean result = false;

		try {
			reset();
			this.niveles = findNivelesEducativos(getInstitucionId());
			result = true;
		} catch (RuntimeException e) {
			FacesMessages.fatal(e);
		}

		return result;
	}

	public void reset() {
		resetProgramas();
		this.setNivelId(null);
	}

	// -----------------------------------------------------------------------------------
	private List<NivelEducativoDto> findNivelesEducativos(int institucionId) {
		val result = ciberService.findAllNivelesEducativosByInstitucion(institucionId);
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

	private void resetProgramas() {
		resetGrados();

		setProgramaId(null);
		this.programas = new ArrayList<>();
	}

	private List<SelectItem> findProgramas(int institucionId, int jornadaId, int nivelId) {
		val list = ciberService.findAllProgramasByNivelEducativo(institucionId, jornadaId, nivelId);

		val result = list.stream().map(a -> new SelectItem(a.getId(), a.getDescripcion())).collect(toList());
		return result;
	}

	public void onChangePrograma() {
		resetGrados();

		this.grados = findGrados(getInstitucionId(), getJornadaId(), getNivelId(), getProgramaId());
	}

	private void resetGrados() {
		resetGrupos();

		setGradoId(null);
		this.grados = new ArrayList<>();
	}

	private List<SelectItem> findGrados(int institucionId, int jornadaId, int nivelId, int programaId) {
		val list = ciberService.findAllGradosByPrograma(institucionId, jornadaId, nivelId, programaId);

		val result = list.stream().map(a -> new SelectItem(a.getId(), a.getDescripcion())).collect(toList());
		return result;
	}

	public void onChangeGrado() {
		resetGrupos();

		this.grupos = findGrupos(getInstitucionId(), getJornadaId(), getNivelId(), getProgramaId(), getGradoId());
	}

	private void resetGrupos() {
		resetEstudiantes();

		this.grupo = null;
		this.grupos = new ArrayList<>();
	}

	private List<GrupoDto> findGrupos(int institucionId, int jornadaId, int nivelId, int programaId, int gradoId) {
		val result = ciberService.findAllGruposByGrado(institucionId, jornadaId, nivelId, programaId, gradoId);

		return result;
	}

	public void setGrupo(GrupoDto grupo) {
		this.grupo = grupo;

		resetEstudiantes();
		if (getGrupo() != null) {
			this.usuarios = findUsuarios(getInstitucionId(), getJornadaId(), getNivelId(), getProgramaId(),
					getGradoId(), getGrupo().getId());
		}
	}

	private void resetEstudiantes() {
		this.usuarios = new ArrayList<>();
		this.direccionManager.reset();
	}

	private List<UsuarioDto> findUsuarios(int institucionId, int jornadaId, int nivelId, int programaId, int gradoId,
			int grupoId) {
		val result = ciberService.findAllEstudiantesByGrupo(institucionId, jornadaId, nivelId, programaId, gradoId,
				grupoId);

		return result.stream().sorted((a, b) -> a.getNombreCompleto().compareTo(b.getNombreCompleto()))
				.collect(toList());
	}

	public void setUsuario(UsuarioDto usuario) {
		this.direccionManager.init(usuario);
	}
}
