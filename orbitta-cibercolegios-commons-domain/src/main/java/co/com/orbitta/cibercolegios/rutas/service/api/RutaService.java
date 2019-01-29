package co.com.orbitta.cibercolegios.rutas.service.api;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import co.com.orbitta.cibercolegios.rutas.dto.RutaDto;
import co.com.orbitta.cibercolegios.rutas.dto.tracking.MonitorDatosRutaDto;
import co.com.orbitta.core.services.crud.api.CrudService;

public interface RutaService extends CrudService<RutaDto, Integer> {

	@Transactional(readOnly = true)
	List<MonitorDatosRutaDto> findAllByMonitorIdAsMonitorDatosRuta(int monitorId);
	
	@Transactional(readOnly = true)
	List<MonitorDatosRutaDto> findAllByInstitucionIdAsMonitorDatosRuta(int institucionId);

	@Transactional(readOnly = true)
	List<RutaDto> findAllByMonitorId(int monitorId);

	@Transactional(readOnly = true)
	List<RutaDto> findAllByInstitucionId(int monitorId);
	
	@Transactional(readOnly = true)
	Optional<MonitorDatosRutaDto> findByIdAsMonitorDatosRuta(int id);

}
