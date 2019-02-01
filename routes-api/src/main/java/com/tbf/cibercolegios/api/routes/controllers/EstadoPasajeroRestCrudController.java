package com.tbf.cibercolegios.api.routes.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tbf.cibercolegios.api.core.controller.api.QueryRestController;
import com.tbf.cibercolegios.api.routes.controllers.constants.RutasRestConstants;
import com.tbf.cibercolegios.api.routes.model.graph.EstadoPasajeroDto;
import com.tbf.cibercolegios.api.routes.services.api.EstadoPasajeroService;

import lombok.val;

@RestController
@RequestMapping(value = RutasRestConstants.estadoPasajero, produces = MediaType.APPLICATION_JSON_VALUE)
public class EstadoPasajeroRestCrudController extends QueryRestController<EstadoPasajeroDto, Integer> {

	@Autowired
	private EstadoPasajeroService service;

	@Override
	protected EstadoPasajeroService getService() {
		return service;
	}
	
	@GetMapping(PATH_LIST)
	public ResponseEntity<List<EstadoPasajeroDto>> list() {
		val result = getService().findAll();
		return ResponseEntity.ok(result);
	}
}
