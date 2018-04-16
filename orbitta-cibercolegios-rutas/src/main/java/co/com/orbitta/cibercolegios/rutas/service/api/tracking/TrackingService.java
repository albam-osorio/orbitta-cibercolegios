package co.com.orbitta.cibercolegios.rutas.service.api.tracking;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import co.com.orbitta.cibercolegios.dto.tracking.DatosRutaDto;
import co.com.orbitta.cibercolegios.dto.tracking.DatosUsuarioRutaDto;

@Transactional(readOnly = true)
public interface TrackingService {

	List<DatosRutaDto> getRutasByMonitorId(int monitorId);

	@Transactional(readOnly = false)
	DatosRutaDto iniciarRecorrido(int rutaId, int sentido, BigDecimal x, BigDecimal y);

	@Transactional(readOnly = false)
	int registrarPosicion(int logRutaId, BigDecimal x, BigDecimal y);

	@Transactional(readOnly = false)
	int registrarEvento(int logRutaId, BigDecimal x, BigDecimal y, int estadoRutaId);

	@Transactional(readOnly = false)
	int registrarParadaPasajero(int logRutaId, BigDecimal x, BigDecimal y, int usuarioRutaId, int estadoUsuarioRutaId);

	@Transactional(readOnly = false)
	int finalizarRecorrido(int logRutaId, BigDecimal x, BigDecimal y, int estadoRutaId);

	DatosRutaDto getRutaByUsuarioId(int usuarioId);

	DatosUsuarioRutaDto getEstadoUsuarioRutaById(int usuarioRutaId);

}
