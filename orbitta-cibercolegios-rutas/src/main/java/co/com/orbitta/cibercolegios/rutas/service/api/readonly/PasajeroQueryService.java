package co.com.orbitta.cibercolegios.rutas.service.api.readonly;

import java.util.List;

import co.com.orbitta.cibercolegios.dto.readonly.PasajeroDto;
import co.com.orbitta.core.services.crud.api.QueryService;

public interface PasajeroQueryService extends QueryService<PasajeroDto, Integer> {

	List<PasajeroDto> findAllByRutaId(int rutaId);

	int numeroParadas(int rutaId);
}
