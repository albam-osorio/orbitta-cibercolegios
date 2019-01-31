package com.tbf.cibercolegios.api.routes.services.api;

import com.tbf.cibercolegios.api.routes.model.graph.LogPasajeroDto;

import co.com.orbitta.core.services.crud.api.CrudService;

public interface LogPasajeroService extends CrudService<LogPasajeroDto, Integer> {

	//List<LogPasajeroDto> findUltimosLogPasajerosByRutaId(int rutaId);

	//Optional<LogPasajeroDto> findUltimoLogPasajerosByRutaIdAndPasajeroId(int rutaId, int pasajeroId);

}
