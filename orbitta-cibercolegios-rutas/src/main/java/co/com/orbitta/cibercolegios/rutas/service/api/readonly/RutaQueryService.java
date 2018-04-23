package co.com.orbitta.cibercolegios.rutas.service.api.readonly;

import java.util.List;

import co.com.orbitta.cibercolegios.dto.readonly.RutaDto;
import co.com.orbitta.core.services.crud.api.QueryService;

public interface RutaQueryService extends QueryService<RutaDto, Integer> {

	List<RutaDto> findAllByMonitorId(int monitorId);
}
