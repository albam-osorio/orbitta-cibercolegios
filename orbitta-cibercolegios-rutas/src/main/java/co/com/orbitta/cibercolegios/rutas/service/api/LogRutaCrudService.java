package co.com.orbitta.cibercolegios.rutas.service.api;

import java.util.Optional;

import co.com.orbitta.cibercolegios.dto.LogRutaDto;
import co.com.orbitta.core.services.crud.api.CrudService;

public interface LogRutaCrudService extends CrudService<LogRutaDto, Integer> {

	Optional<LogRutaDto> findSiguienteUbicacion(int rutaId, int id);

	Optional<Integer> findIdUltimoLogRutaDelDiaByRutaId(int rutaId);
}
