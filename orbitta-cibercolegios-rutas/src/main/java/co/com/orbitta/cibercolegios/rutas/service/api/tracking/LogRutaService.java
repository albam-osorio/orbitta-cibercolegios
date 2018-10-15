package co.com.orbitta.cibercolegios.rutas.service.api.tracking;

import java.time.LocalDate;
import java.util.Optional;

import co.com.orbitta.cibercolegios.rutas.dto.tracking.LogRutaDto;
import co.com.orbitta.core.services.crud.api.CrudService;

public interface LogRutaService extends CrudService<LogRutaDto, Integer> {

	Optional<LogRutaDto> findUltimoLogRutaByRutaIdAndFechaUltimoRecorrido(int rutaId, LocalDate fechaUltimoRecorrido);
}
