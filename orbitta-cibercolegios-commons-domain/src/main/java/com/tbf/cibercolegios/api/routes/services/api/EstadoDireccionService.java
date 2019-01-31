package com.tbf.cibercolegios.api.routes.services.api;

import java.util.List;

import com.tbf.cibercolegios.api.routes.model.graph.EstadoDireccionDto;

import co.com.orbitta.core.services.crud.api.CrudService;

public interface EstadoDireccionService extends CrudService<EstadoDireccionDto, Integer> {

	List<EstadoDireccionDto> findAll();
}
