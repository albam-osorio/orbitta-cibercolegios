package co.com.orbitta.cibercolegios.rutas.service.api;

import java.util.Optional;

import co.com.orbitta.cibercolegios.dto.RutaDto;
import co.com.orbitta.core.services.crud.api.CrudService;

public interface RutaCrudService extends CrudService<RutaDto, Integer> {

	Optional<RutaDto> findByMonitorId(int monitorId);

}
