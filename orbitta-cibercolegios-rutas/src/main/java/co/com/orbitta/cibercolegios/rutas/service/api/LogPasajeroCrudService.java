package co.com.orbitta.cibercolegios.rutas.service.api;

import java.util.List;
import java.util.Optional;

import co.com.orbitta.cibercolegios.dto.LogPasajeroDto;
import co.com.orbitta.core.services.crud.api.CrudService;

public interface LogPasajeroCrudService extends CrudService<LogPasajeroDto, Integer> {

	List<LogPasajeroDto> findUltimosLogPasajerosByRutaId(int rutaId, int sentido);

	Optional<LogPasajeroDto> findUltimoLogPasajerosByRutaIdAndPasajeroId(int rutaId, int pasajeroId);

}
