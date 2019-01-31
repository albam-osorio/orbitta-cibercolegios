package com.tbf.cibercolegios.cibercolegios.web.controller.usuarios.managers;

import static java.util.stream.Collectors.toList;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tbf.cibercolegios.api.routes.model.graph.DireccionDto;
import com.tbf.cibercolegios.api.routes.model.graph.PasajeroDto;
import com.tbf.cibercolegios.api.routes.model.graph.readonly.UsuarioDto;
import com.tbf.cibercolegios.api.routes.model.graph.readonly.UsuarioPerfilDto;
import com.tbf.cibercolegios.api.routes.services.api.DireccionService;
import com.tbf.cibercolegios.api.routes.services.api.PasajeroService;
import com.tbf.cibercolegios.api.routes.services.api.ciber.CiberService;
import com.tbf.cibercolegios.cibercolegios.web.util.FacesMessages;
import com.tbf.cibercolegios.cibercolegios.web.util.UserPreferences;

import lombok.Getter;
import lombok.Setter;
import lombok.val;

@Component
@Setter
@Getter
public class DireccionManager implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private UserPreferences preferences;

	@Autowired
	private CiberService ciberService;

	@Autowired
	private PasajeroService pasajeroService;

	@Autowired
	private DireccionService direccionService;

	private UsuarioDto usuario;

	private UsuarioPerfilDto perfil;

	private StreamedContent streamedFoto;

	private List<UsuarioDto> acudientes;

	private List<SelectItem> departamentos;

	private Integer departamentoAmId;

	private List<SelectItem> ciudadesAm;

	private Integer ciudadAmId;

	private String direccionAm;

	private Integer departamentoPmId;

	private List<SelectItem> ciudadesPm;

	private Integer ciudadPmId;

	private String direccionPm;

	private Integer getInstitucionId() {
		val result = preferences.getInstitucionId();
		return result;
	}

	private Integer getPaisId() {
		val result = preferences.getPaisId();
		return result;
	}

	// -----------------------------------------------------------------------------------
	public boolean init(UsuarioDto usuario) {
		boolean result = false;

		try {
			reset();
			this.departamentos = findDepartamentos(getPaisId());
			this.setUsuario(usuario);
			result = true;
		} catch (RuntimeException e) {
			FacesMessages.fatal(e);
			e.printStackTrace();
		}

		return result;
	}

	public void reset() {
		resetCiudadesAm();
		this.setDepartamentoAmId(null);
		this.setDireccionAm("");

		resetCiudadesPm();
		this.setDepartamentoPmId(null);
		this.setDireccionPm("");
	}

	// -----------------------------------------------------------------------------------
	private List<SelectItem> findDepartamentos(int paisId) {
		val list = ciberService.findAllDepartamentosByPais(paisId);

		val result = list.stream().map(a -> new SelectItem(a.getDepartamentoId(), a.getNombre())).collect(toList());
		return result;
	}

	public void onChangeDepartamentoAm() {
		resetCiudadesAm();

		this.ciudadesAm = findCiudades(getPaisId(), getDepartamentoAmId());
	}

	public void onChangeDepartamentoPm() {
		resetCiudadesPm();

		this.ciudadesPm = findCiudades(getPaisId(), getDepartamentoPmId());
	}

	private void resetCiudadesAm() {
		setCiudadAmId(null);
		this.ciudadesAm = new ArrayList<>();
	}

	private void resetCiudadesPm() {
		setCiudadPmId(null);
		this.ciudadesPm = new ArrayList<>();
	}

	private List<SelectItem> findCiudades(int paisId, int departamentoId) {
		val list = ciberService.findAllCiudadesByDepartamento(paisId, departamentoId);

		val result = list.stream().map(a -> new SelectItem(a.getCiudadId(), a.getNombre())).collect(toList());
		return result;
	}

	public void onChangeCiudadAm() {

	}

	public void onChangeCiudadPm() {

	}

	public void setUsuario(UsuarioDto usuario) {
		this.usuario = usuario;
		this.perfil = findPerfil(usuario.getId());
		this.streamedFoto = asStreamedContent(this.perfil.getFoto());
		this.acudientes = findAcudientes(usuario.getId());

		val optional = pasajeroService.findByUsuarioId(usuario.getId());
		if (optional.isPresent()) {
			val pasajero = optional.get();

			val am = direccionService.findById(pasajero.getDireccionIdaId());
			if (am.isPresent()) {
				this.setDepartamentoAmId(am.get().getDepartamentoId());
				this.onChangeDepartamentoAm();
				this.setCiudadAmId(am.get().getCiudadId());
				this.setDireccionAm(am.get().getDireccion());
			}

			val pm = direccionService.findById(pasajero.getDireccionRetornoId());
			if (pm.isPresent()) {
				this.setDepartamentoPmId(pm.get().getDepartamentoId());
				this.onChangeDepartamentoPm();
				this.setCiudadPmId(pm.get().getCiudadId());
				this.setDireccionPm(pm.get().getDireccion());
			}

		}
	}

	private UsuarioPerfilDto findPerfil(Integer usuarioId) {
		val optional = ciberService.findPerfilByUsuario(usuarioId);
		val p = optional.get();
		return p;

	}

	private StreamedContent asStreamedContent(byte[] bytes) {
		StreamedContent result;
		if (bytes != null) {
			val stream = new ByteArrayInputStream(bytes);
			result = new DefaultStreamedContent(stream, "image/jpeg");
		} else {
			result = null;
		}
		return result;
	}

	private List<UsuarioDto> findAcudientes(Integer usuarioId) {
		val result = ciberService.findAcudientesByUsuario(usuarioId);
		return result;
	}

	// -----------------------------------------------------------------------------------
	public void save() {
		int usuarioId = getUsuario().getId();

		DireccionDto direccionIda = asNewDirecccionIda();
		DireccionDto direccionRetorno = asNewDirecccionRetorno();
		val usuariosAcudientesId = ciberService.findUsuariosIdDeAcudientesByUsuarioId(usuarioId);

		val optional = pasajeroService.findByUsuarioId(usuarioId);
		if (optional.isPresent()) {
			PasajeroDto pasajero = optional.get();
			direccionIda.setId(pasajero.getDireccionIdaId());
			direccionRetorno.setId(pasajero.getDireccionRetornoId());

			pasajero = pasajeroService.update(pasajero, direccionIda, direccionRetorno, usuariosAcudientesId);
		} else {
			PasajeroDto pasajero = asNewPasajero(usuarioId);

			pasajero = pasajeroService.create(pasajero, direccionIda, direccionRetorno, usuariosAcudientesId);
		}
	}

	private DireccionDto asNewDirecccionIda() {
		DireccionDto result;

		result = new DireccionDto();
		result.setInstitucionId(getInstitucionId());
		result.setEstadoId(DireccionDto.ESTADO_NO_PROCESADA);
		result.setPaisId(getPaisId());
		result.setDepartamentoId(getDepartamentoAmId());
		result.setCiudadId(getCiudadAmId());
		result.setDireccion(getDireccionAm());

		return result;
	}

	private DireccionDto asNewDirecccionRetorno() {
		DireccionDto result;

		result = new DireccionDto();
		result.setInstitucionId(getInstitucionId());
		result.setEstadoId(DireccionDto.ESTADO_NO_PROCESADA);
		result.setPaisId(getPaisId());
		result.setDepartamentoId(getDepartamentoPmId());
		result.setCiudadId(getCiudadPmId());
		result.setDireccion(getDireccionPm());

		return result;
	}

	private PasajeroDto asNewPasajero(Integer usuarioId) {
		val result = new PasajeroDto();

		result.setUsuarioId(usuarioId);
		result.setSecuenciaIda(0);
		result.setDireccionIdaId(null);
		result.setSecuenciaRetorno(0);
		result.setDireccionRetornoId(null);
		result.setEstadoId(PasajeroDto.ESTADO_INACTIVO);
		result.setAcudientes(new ArrayList<>());

		return result;
	}
}
