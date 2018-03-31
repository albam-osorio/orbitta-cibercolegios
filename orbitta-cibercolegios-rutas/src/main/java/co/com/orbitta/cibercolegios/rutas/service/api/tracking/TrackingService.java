package co.com.orbitta.cibercolegios.rutas.service.api.tracking;

import java.math.BigDecimal;

import org.springframework.transaction.annotation.Transactional;

import co.com.orbitta.cibercolegios.dto.tracking.DatosRutaDto;

@Transactional(readOnly = true)
public interface TrackingService {

	@Transactional(readOnly = false)
	DatosRutaDto iniciarRecorrido(int monitorId, int sentido, BigDecimal cx, BigDecimal cy);

	@Transactional(readOnly = false)
	int registrarPosicion(int ultimaUbicacionRutaId, BigDecimal cx, BigDecimal cy);

	@Transactional(readOnly = false)
	int registrarEventoEnRecorrido(int ultimaUbicacionRutaId, BigDecimal cx, BigDecimal cy, int estadoId);

	@Transactional(readOnly = false)
	int registrarParadaPasajero(int ultimaUbicacionRutaId, BigDecimal cx, BigDecimal cy, int estadoId,
			int usuarioRutaId, int logId);

	@Transactional(readOnly = false)
	int finalizarRecorrido(int ultimaUbicacionRutaId, BigDecimal cx, BigDecimal cy, int estadoId);

	DatosRutaDto getInformacionRuta(int rutaId);

	void getEstadoRuta();
}
