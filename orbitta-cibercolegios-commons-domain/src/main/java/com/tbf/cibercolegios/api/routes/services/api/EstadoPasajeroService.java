package com.tbf.cibercolegios.api.routes.services.api;

import java.util.List;

import com.tbf.cibercolegios.api.routes.model.graph.EstadoPasajeroDto;

import co.com.orbitta.core.services.crud.api.CrudService;

public interface EstadoPasajeroService extends CrudService<EstadoPasajeroDto, Integer> {

	List<EstadoPasajeroDto> findAll();

	EstadoPasajeroDto findEstadoInicioPredeterminado();

}
