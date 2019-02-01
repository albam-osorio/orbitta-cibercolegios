package com.tbf.cibercolegios.api.routes.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tbf.cibercolegios.api.core.controller.api.QueryRestController;
import com.tbf.cibercolegios.api.routes.model.graph.EstadoRutaDto;
import com.tbf.cibercolegios.api.routes.model.graph.constants.RutasRestConstants;
import com.tbf.cibercolegios.api.routes.services.api.EstadoRutaService;

import lombok.val;

@RestController
@RequestMapping(value = RutasRestConstants.estadoRuta, produces = MediaType.APPLICATION_JSON_VALUE)
public class EstadoRutaRestCrudController extends QueryRestController<EstadoRutaDto, Integer> {

	@Autowired
	private EstadoRutaService service;

	@Override
	protected EstadoRutaService getService() {
		return service;
	}
	
	@GetMapping(PATH_LIST)
	public ResponseEntity<List<EstadoRutaDto>> list() {
		val result = getService().findAll();
		return ResponseEntity.ok(result);
	}
}
