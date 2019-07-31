package com.tbf.cibercolegios.api.routes.services.api;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.tbf.cibercolegios.api.core.services.api.CrudService;
import com.tbf.cibercolegios.api.routes.model.graph.RutaDto;
import com.tbf.cibercolegios.api.routes.model.graph.tracking.MonitorDatosRutaDto;
import com.tbf.cibercolegios.api.routes.model.graph.web.RutaConCapacidadDto;

public interface RutaService extends CrudService<RutaDto, Integer> {

	@Transactional(readOnly = true)
	List<RutaDto> findAllByInstitucionId(int institucionId);

	@Transactional(readOnly = true)
	List<RutaDto> findAllByInstitucionIdAndMonitorId(int institucionId, int monitorId);

	@Transactional(readOnly = true)
	Optional<RutaConCapacidadDto> findRutasConCapacidadById(int rutaId);

	@Transactional(readOnly = true)
	List<RutaConCapacidadDto> findAllRutasConCapacidadByInstitucionId(int institucionId, Integer pasajeroId);

	// -----------------------------------------------------------------------------------------------------------------
	// -- Monitor
	// -----------------------------------------------------------------------------------------------------------------
	@Transactional(readOnly = true)
	Optional<MonitorDatosRutaDto> findByIdAsMonitorDatosRuta(int id);

	@Transactional(readOnly = true)
	List<MonitorDatosRutaDto> findAllByInstitucionIdAsMonitorDatosRuta(int institucionId);

	@Transactional(readOnly = true)
	List<MonitorDatosRutaDto> findAllByInstitucionIdAndMonitorIdAsMonitorDatosRuta(int institucionId, int monitorId);
}
