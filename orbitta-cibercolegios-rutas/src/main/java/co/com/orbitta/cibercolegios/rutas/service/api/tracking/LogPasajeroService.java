package co.com.orbitta.cibercolegios.rutas.service.api.tracking;

import co.com.orbitta.cibercolegios.rutas.dto.tracking.LogPasajeroDto;
import co.com.orbitta.core.services.crud.api.CrudService;

public interface LogPasajeroService extends CrudService<LogPasajeroDto, Integer> {

	//List<LogPasajeroDto> findUltimosLogPasajerosByRutaId(int rutaId);

	//Optional<LogPasajeroDto> findUltimoLogPasajerosByRutaIdAndPasajeroId(int rutaId, int pasajeroId);

}
