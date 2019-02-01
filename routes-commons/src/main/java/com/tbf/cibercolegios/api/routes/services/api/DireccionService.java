package com.tbf.cibercolegios.api.routes.services.api;

import java.util.List;

import com.tbf.cibercolegios.api.core.services.api.CrudService;
import com.tbf.cibercolegios.api.routes.model.graph.DireccionDto;

public interface DireccionService extends CrudService<DireccionDto, Integer> {

	List<DireccionDto> findAllByInstitucionId(int institucionId);
	
}
