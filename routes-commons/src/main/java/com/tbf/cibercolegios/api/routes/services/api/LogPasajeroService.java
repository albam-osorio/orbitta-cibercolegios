package com.tbf.cibercolegios.api.routes.services.api;

import com.tbf.cibercolegios.api.core.services.api.CrudService;
import com.tbf.cibercolegios.api.routes.model.graph.LogPasajeroDto;

public interface LogPasajeroService extends CrudService<LogPasajeroDto, Integer> {

	//List<LogPasajeroDto> findUltimosLogPasajerosByRutaId(int rutaId);

	//Optional<LogPasajeroDto> findUltimoLogPasajerosByRutaIdAndPasajeroId(int rutaId, int pasajeroId);

}
