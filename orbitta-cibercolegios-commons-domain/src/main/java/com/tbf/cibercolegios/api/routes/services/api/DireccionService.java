package com.tbf.cibercolegios.api.routes.services.api;

import java.util.List;

import com.tbf.cibercolegios.api.routes.model.graph.DireccionDto;

import co.com.orbitta.core.services.crud.api.CrudService;

public interface DireccionService extends CrudService<DireccionDto, Integer> {

	List<DireccionDto> findAllByInstitucionId(int institucionId);
	
}
