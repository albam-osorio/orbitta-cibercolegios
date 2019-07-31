package com.tbf.cibercolegios.api.routes.web.users;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.faces.model.SelectItem;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;

import com.tbf.cibercolegios.api.ciber.services.api.CiberService;
import com.tbf.cibercolegios.api.model.routes.enums.CourseType;
import com.tbf.cibercolegios.api.routes.models.users.DireccionViewModel;
import com.tbf.cibercolegios.api.routes.models.users.TrayectoViewModel;
import com.tbf.cibercolegios.api.routes.web.utils.UserProfile;
import com.tbf.cibercolegios.core.DialogCommandController;

import lombok.Getter;
import lombok.Setter;
import lombok.val;

@Setter
@Getter
public abstract class UsuariosDireccionDialogAbstractController
		extends DialogCommandController<DireccionViewModel, String> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	protected UserProfile profile;

	@Autowired
	private CiberService ciberService;

	// ----------------------------------------------------------------------------------------------------
	// --
	// ----------------------------------------------------------------------------------------------------
	protected List<SelectItem> listaDepartamento;

	protected List<SelectItem> listaCiudad;

	private Integer departamentoId;

	private Integer ciudadId;

	@NotNull
	@Size(max = 100)
	private String direccion;

	private TrayectoViewModel trayecto;

	private DireccionViewModel direccionAlterna;

	private CourseType sentidoAlterno;

	private boolean replicar;

	// ----------------------------------------------------------------------------------------------------
	// --INICIALIZADORES
	// ----------------------------------------------------------------------------------------------------
	@Override
	protected void init() {
		setListaDepartamento(new ArrayList<>());
		setListaCiudad(new ArrayList<>());
		getListaDepartamento().addAll(findAllDepartamentos(getProfile().getPaisId()));
	}

	@Override
	protected void clear() {
		super.clear();
		this.getListaCiudad().clear();
	}

	@Override
	protected boolean populate() {
		val institucionId = getProfile().getInstitucionId();
		val paisId = getProfile().getPaisId();

		if (institucionId != null && paisId != null) {
			this.setDepartamentoId(this.getModel().getDepartamentoId());
			if (this.getDepartamentoId() != null) {
				this.getListaCiudad().addAll(findAllCiudadesByDepartamento(paisId, this.getDepartamentoId()));
			}

			this.setCiudadId(this.getModel().getCiudadId());
			this.setDireccion(this.getModel().getDireccion());

			if (CourseType.SENTIDO_IDA.equals(this.getModel().getSentido())) {
				setDireccionAlterna(getTrayecto().getDireccionPm());
				setSentidoAlterno(CourseType.SENTIDO_RETORNO);
			} else {
				setDireccionAlterna(getTrayecto().getDireccionAm());
				setSentidoAlterno(CourseType.SENTIDO_IDA);
			}

			boolean replicar = true;
			if (getModel().getId() == null) {
				if (getDireccionAlterna() != null) {
					replicar = false;
				}
			} else {
				if (getDireccionAlterna() == null) {
					replicar = false;
				} else {
					if (!getModel().getDepartamentoId().equals(getDireccionAlterna().getDepartamentoId())) {
						replicar = false;
					}
					if (!getModel().getCiudadId().equals(getDireccionAlterna().getCiudadId())) {
						replicar = false;
					}
					if (!getModel().getDireccion().equals(getDireccionAlterna().getDireccion())) {
						replicar = false;
					}
				}
			}

			setReplicar(replicar);

			return true;
		}

		return false;
	}

	// ----------------------------------------------------------------------------------------------------
	// --EVENTOS
	// ----------------------------------------------------------------------------------------------------
	public void onChangeDepartamento() {
		resetCiudades();

		if (this.getDepartamentoId() != null) {
			getListaCiudad().addAll(findAllCiudadesByDepartamento(getProfile().getPaisId(), this.getDepartamentoId()));

			if (getListaCiudad().size() == 1) {
				this.setCiudadId((Integer) getListaCiudad().get(0).getValue());
			}
		}
	}

	public void onChangeCiudad() {

	}

	private void resetCiudades() {
		getListaCiudad().clear();
		this.setCiudadId(null);
	}

	// ----------------------------------------------------------------------------------------------------
	// -- COMANDOS
	// ----------------------------------------------------------------------------------------------------
	abstract public void submit();

	protected void replicarDireccion() {
		if (isReplicar()) {
			if (getDireccionAlterna() == null) {
				setDireccionAlterna(new DireccionViewModel());
				getDireccionAlterna().setSentido(getSentidoAlterno());
				if (CourseType.SENTIDO_IDA.equals(getSentidoAlterno())) {
					getTrayecto().setDireccionAm(getDireccionAlterna());
				} else {
					getTrayecto().setDireccionPm(getDireccionAlterna());
				}
			}
			getDireccionAlterna().setDepartamentoId(getModel().getDepartamentoId());
			getDireccionAlterna().setDepartamentoNombre(getModel().getDepartamentoNombre());
			getDireccionAlterna().setCiudadId(getModel().getCiudadId());
			getDireccionAlterna().setCiudadNombre(getModel().getCiudadNombre());
			getDireccionAlterna().setDireccion(getModel().getDireccion());
			getDireccionAlterna().setGeoCodificada(false);
			getDireccionAlterna().setModificado(true);
		}
	}

	// ----------------------------------------------------------------------------------------------------
	// --
	// ----------------------------------------------------------------------------------------------------
	protected List<SelectItem> findAllDepartamentos(Integer paisId) {
		return getCiberService().findAllDepartamentosByPaisId(paisId).stream()
				.map(e -> new SelectItem(e.getDepartamentoId(), e.getNombre())).collect(Collectors.toList());
	}

	protected List<SelectItem> findAllCiudadesByDepartamento(Integer paisId, Integer departamentoId) {
		return getCiberService().findAllCiudadesByDepartamentoId(paisId, departamentoId).stream()
				.map(e -> new SelectItem(e.getCiudadId(), e.getNombre())).collect(Collectors.toList());
	}
}