package co.com.orbitta.cibercolegios.rutas.service.api.tracking;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import co.com.orbitta.cibercolegios.rutas.dto.tracking.monitor.DatosRutaDto;



@Transactional(readOnly = true)
public interface MonitorTrackingService {

	List<DatosRutaDto> findRutasByMonitorId(int monitorId);

	@Transactional(readOnly = false)
	DatosRutaDto iniciarRecorrido(int monitorId, int rutaId, BigDecimal x, BigDecimal y, int sentido);

	@Transactional(readOnly = false)
	int registrarPosicion(int logRutaId, BigDecimal x, BigDecimal y);

	@Transactional(readOnly = false)
	int registrarEvento(int logRutaId, BigDecimal x, BigDecimal y, int estadoRutaId);

	@Transactional(readOnly = false)
	int registrarParadaPasajero(int logRutaId, BigDecimal x, BigDecimal y, int usuarioRutaId, int estadoUsuarioRutaId);

	@Transactional(readOnly = false)
	int finalizarRecorrido(int logRutaId, BigDecimal x, BigDecimal y);

	Optional<DatosRutaDto> findEstadoRutaByRutaId(int rutaId);
}
