package co.com.orbitta.cibercolegios.service.api;

import java.util.Optional;

import co.com.orbitta.cibercolegios.rutas.dto.tracking.LogRutaDto;
import co.com.orbitta.core.services.crud.api.CrudService;

public interface LogRutaCrudService extends CrudService<LogRutaDto, Integer> {

	Optional<LogRutaDto> findUltimoLogRutaByRutaId(int rutaId);
}