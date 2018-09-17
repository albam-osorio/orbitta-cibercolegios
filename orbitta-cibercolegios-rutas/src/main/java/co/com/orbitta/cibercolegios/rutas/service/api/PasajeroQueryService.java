package co.com.orbitta.cibercolegios.rutas.service.api;

import java.util.List;

import co.com.orbitta.cibercolegios.rutas.dto.PasajeroDto;
import co.com.orbitta.core.services.crud.api.QueryService;

public interface PasajeroQueryService extends QueryService<PasajeroDto, Integer> {

	List<PasajeroDto> findAllByRutaId(int rutaId);

	int numeroParadas(int rutaId);
}
