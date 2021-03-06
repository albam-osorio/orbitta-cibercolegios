package com.tbf.cibercolegios.api.routes.services.api.tracking;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.tbf.cibercolegios.api.routes.model.graph.tracking.ListaAbordajeDto;
import com.tbf.cibercolegios.api.routes.model.graph.tracking.MonitorDatosRutaDto;

public interface MonitorTrackingService {

	@Transactional(readOnly = false)
	void registrarMonitor(int institucionId, int monitorId, String token);

	/**
	 * Un monitor invoca este servicio como prerrequisito para poder iniciar su
	 * recorrido. Un monitor puede estar asociado a más de una ruta. Este listado le
	 * permite seleccionar con cual de sus rutas va a trabajar.
	 * 
	 * @param monitorId
	 * @return Una lista de dto donde cada uno contiene toda la información de rutas
	 *         requerida por la app de un usuario monitor
	 */
	@Transactional(readOnly = true)
	List<MonitorDatosRutaDto> findRutasByInstitucionIdAndMonitorId(int institucionId, int monitorId);

	@Transactional(readOnly = true)
	Optional<MonitorDatosRutaDto> findRutaByRutaId(int rutaId);

	@Transactional(readOnly = false)
	MonitorDatosRutaDto iniciarRecorrido(int monitorId, int rutaId, BigDecimal x, BigDecimal y, int sentido);

	@Transactional(readOnly = false)
	MonitorDatosRutaDto registrarAbordaje(ListaAbordajeDto lista);

	@Transactional(readOnly = false)
	int registrarPosicion(int rutaId, BigDecimal x, BigDecimal y);

	@Transactional(readOnly = false)
	int registrarEvento(int rutaId, BigDecimal x, BigDecimal y, int estadoRutaId);

	@Transactional(readOnly = false)
	int registrarParadaPasajero(int rutaId, BigDecimal x, BigDecimal y, int usuarioId, int estadoUsuarioRutaId);

	@Transactional(readOnly = false)
	int finalizarRecorrido(int rutaId, BigDecimal x, BigDecimal y);

	@Transactional(readOnly = false)
	int registrarPosicionByLogRuta(int logRutaId, BigDecimal x, BigDecimal y);

	@Transactional(readOnly = false)
	int registrarEventoByLogRuta(int logRutaId, BigDecimal x, BigDecimal y, int estadoRutaId);

	@Transactional(readOnly = false)
	int registrarParadaPasajeroByLogRuta(int logRutaId, BigDecimal x, BigDecimal y, int pasajeroId,
			int estadoPasajeroId);

	@Transactional(readOnly = false)
	int finalizarRecorridoByLogRuta(int logRutaId, BigDecimal x, BigDecimal y);
}
