package com.tbf.cibercolegios.api.routes.controllers.users;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import org.primefaces.event.FileUploadEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RestController;

import com.tbf.cibercolegios.api.routes.controllers.users.managers.BatchManager;
import com.tbf.cibercolegios.api.routes.controllers.users.managers.ManualManager;

import lombok.Getter;
import lombok.Setter;

@RestController
@Scope("view")
@Setter
@Getter
public class UsuariosController implements Serializable, UsuariosConstantes {

	private static final long serialVersionUID = 1L;

	@Autowired
	private BatchManager batchManager;

	@Autowired
	private ManualManager manualManager;

	private boolean batch = false;

	private boolean manual = false;

	@PostConstruct
	public void init() {
		this.getBatchManager().init();
		this.getManualManager().init();
	}

	public void fileUploadListener(FileUploadEvent event) {
		this.setBatch(true);
		this.setManual(false);

		this.getBatchManager().fileUploadListener(event);
	}

	public void onChangeNivelAcademico() {
		this.setBatch(false);
		this.setManual(true);

		this.getManualManager().onChangeNivelAcademico();
	}

	public void onChangePrograma() {
		this.setBatch(false);
		this.setManual(true);

		this.getManualManager().onChangePrograma();
	}

	public void onChangeGrado() {
		this.setBatch(false);
		this.setManual(true);

		this.getManualManager().onChangeGrado();
	}

	public void saveBatch() {
		this.getBatchManager().save();
		reset();
	}

	public void saveManual() {
		this.getManualManager().getDireccionManager().save();
		this.getManualManager().getDireccionManager().reset();
	}

	public void cancel() {
		reset();
	}

	private void reset() {
		this.batchManager.reset();
		this.manualManager.reset();
		this.setBatch(false);
		this.setManual(false);
	}
}
