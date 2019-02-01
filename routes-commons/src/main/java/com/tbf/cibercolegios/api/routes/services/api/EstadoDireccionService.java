package com.tbf.cibercolegios.api.routes.services.api;

import java.util.List;

import com.tbf.cibercolegios.api.core.services.api.CrudService;
import com.tbf.cibercolegios.api.routes.model.graph.EstadoDireccionDto;

public interface EstadoDireccionService extends CrudService<EstadoDireccionDto, Integer> {

	List<EstadoDireccionDto> findAll();
}
