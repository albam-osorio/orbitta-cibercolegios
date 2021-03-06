package com.tbf.cibercolegios.api.routes.services.api;

import java.util.List;

import com.tbf.cibercolegios.api.core.services.api.CrudService;
import com.tbf.cibercolegios.api.routes.model.graph.EstadoPasajeroDto;

public interface EstadoPasajeroService extends CrudService<EstadoPasajeroDto, Integer> {

	List<EstadoPasajeroDto> findAll();

	EstadoPasajeroDto findEstadoInicioPredeterminado();

}
