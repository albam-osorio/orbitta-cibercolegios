package com.tbf.cibercolegios.api.routes.controllers.tracking;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RestController;

import com.tbf.cibercolegios.api.routes.model.graph.tracking.MonitorDatosRutaDto;
import com.tbf.cibercolegios.core.DialogController;

import lombok.Getter;
import lombok.Setter;

@RestController
@Scope("view")
@Setter
@Getter
public class SeguimientoDialogViewController extends DialogController<MonitorDatosRutaDto, Integer> {

	private static final long serialVersionUID = 1L;

	@Override
	protected void init() {
		
	}

	@Override
	protected void populate() {

	}
}
