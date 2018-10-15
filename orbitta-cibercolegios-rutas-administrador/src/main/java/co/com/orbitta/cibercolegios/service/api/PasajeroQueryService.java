package co.com.orbitta.cibercolegios.service.api;

import java.util.List;
import java.util.Optional;

import co.com.orbitta.cibercolegios.rutas.dto.PasajeroDto;
import co.com.orbitta.core.services.crud.api.QueryService;

public interface PasajeroQueryService extends QueryService<PasajeroDto, Integer> {

	Optional<PasajeroDto> findByUsuarioId(int usuarioId);

	List<PasajeroDto> findAllByRutaId(int rutaId);
}
