package com.tbf.cibercolegios.api.routes.services.impl.tracking;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tbf.cibercolegios.api.routes.model.enums.RouteTypeStatus;
import com.tbf.cibercolegios.api.routes.model.graph.LogPasajeroDto;
import com.tbf.cibercolegios.api.routes.model.graph.LogRutaDto;
import com.tbf.cibercolegios.api.routes.model.graph.PasajeroDto;
import com.tbf.cibercolegios.api.routes.model.graph.RutaDto;
import com.tbf.cibercolegios.api.routes.model.graph.tracking.MonitorDatosRutaDto;
import com.tbf.cibercolegios.api.routes.services.api.EstadoPasajeroService;
import com.tbf.cibercolegios.api.routes.services.api.EstadoRutaService;
import com.tbf.cibercolegios.api.routes.services.api.LogPasajeroService;
import com.tbf.cibercolegios.api.routes.services.api.LogRutaService;
import com.tbf.cibercolegios.api.routes.services.api.PasajeroService;
import com.tbf.cibercolegios.api.routes.services.api.RutaService;
import com.tbf.cibercolegios.api.routes.services.api.ciber.CiberService;
import com.tbf.cibercolegios.api.routes.services.api.tracking.MonitorTrackingService;

import lombok.val;

@Service
public class MonitorTrackingServiceImpl implements MonitorTrackingService {

	@Autowired
	private RutaService rutaService;

	@Autowired
	private PasajeroService pasajeroService;

	@Autowired
	private CiberService ciberService;

	@Autowired
	private EstadoRutaService estadoRutaService;

	@Autowired
	private EstadoPasajeroService estadoPasajeroService;

	@Autowired
	private LogRutaService logRutaService;

	@Autowired
	private LogPasajeroService logPasajeroService;

	// -----------------------------------------------------------------------------------------------------------------
	// -- Monitor
	// -----------------------------------------------------------------------------------------------------------------

	@Override
	public void registrarMonitor(int monitorId, String token) {
		CheckUsuarioExistente(monitorId);

		val rutas = rutaService.findAllByMonitorId(monitorId);
		if (!rutas.isEmpty()) {
			for (val ruta : rutas) {
				ruta.setToken(token);
			}
			rutaService.update(rutas);
		} else {
			val format = "El monitor no esta asociado a ninguna ruta. No es posible registrar el token";
			val msg = String.format(format);
			throw new RuntimeException(msg);
		}
	}

	@Override
	public List<MonitorDatosRutaDto> findRutasByMonitorId(int monitorId) {
		val result = rutaService.findAllByMonitorIdAsMonitorDatosRuta(monitorId);
		return result;
	}

	@Override
	public Optional<MonitorDatosRutaDto> findRutaByRutaId(int rutaId) {
		val result = rutaService.findByIdAsMonitorDatosRuta(rutaId);
		return result;
	}

	@Override
	public MonitorDatosRutaDto iniciarRecorrido(int monitorId, int rutaId, BigDecimal x, BigDecimal y, int sentido,
			String token) {

		val ruta = rutaService.findOneById(rutaId);
		val pasajeros = pasajeroService.findAllByRutaId(rutaId);

		checkMonitorParaIniciarRecorrido(monitorId, ruta);
		checkRutaParaIniciarRecorrido(ruta, sentido);
		checkPasajerosParaIniciarRecorrido(ruta, pasajeros);

		val estadoRutaInicio = estadoRutaService.findEstadoInicioPredeterminado();
		val estadoRutaPredeterminado = estadoRutaService.findEstadoRecorridoPredeterminado();
		val estadoPasajeroInicio = estadoPasajeroService.findEstadoInicioPredeterminado();

		createLogRuta(rutaId, sentido, estadoRutaInicio.getId(), x, y);

		//ruta.setToken(token);
		ruta.setFechaUltimoRecorrido(LocalDate.now());
		ruta.setSentido(sentido);
		ruta.setEstadoId(estadoRutaPredeterminado.getId());
		createLogRuta(ruta, x, y);

		for (val pasajero : pasajeros) {
			createLogPasajero(pasajero, sentido, estadoPasajeroInicio.getId(), x, y);
		}

		val result = rutaService.findByIdAsMonitorDatosRuta(rutaId);
		return result.get();
	}

	@Override
	public int registrarPosicion(int rutaId, BigDecimal x, BigDecimal y) {
		val ruta = rutaService.findOneById(rutaId);

		checkRutaIsActiva(ruta);

		val result = createLogRuta(ruta, x, y);
		return result.getId();
	}

	@Override
	public int registrarEvento(int rutaId, BigDecimal x, BigDecimal y, int estadoId) {
		val ruta = rutaService.findOneById(rutaId);

		checkRutaIsActiva(ruta);
		checkEventoEsDeRecorrido(estadoId);

		ruta.setEstadoId(estadoId);
		val result = createLogRuta(ruta, x, y);
		return result.getId();
	}

	@Override
	public int registrarParadaPasajero(int rutaId, BigDecimal x, BigDecimal y, int usuarioId, int estadoPasajeroId) {
		val ruta = rutaService.findOneById(rutaId);
		val pasajero = pasajeroService.findByUsuarioId(usuarioId);

		checkRutaIsActiva(ruta);
		checkPasajeroParaRegistrarParada(ruta, pasajero);

		createLogPasajero(pasajero.get(), ruta.getSentido(), estadoPasajeroId, x, y);

		val result = createLogRuta(ruta, x, y);
		return result.getId();
	}

	@Override
	public int finalizarRecorrido(int rutaId, BigDecimal x, BigDecimal y) {
		val ruta = rutaService.findOneById(rutaId);

		checkRutaIsActiva(ruta);
		checkRutaParaFinalizarRecorrido(ruta);

		val estadoRutaFin = estadoRutaService.findEstadoFinPredeterminado();

		ruta.setEstadoId(estadoRutaFin.getId());
		val result = createLogRuta(ruta, x, y);
		return result.getId();
	}

	@Override
	public int registrarPosicionByLogRuta(int logRutaId, BigDecimal x, BigDecimal y) {
		val logRuta = findUltimoLogRuta(logRutaId);
		return registrarPosicion(logRuta.getRutaId(), x, y);
	}

	@Override
	public int registrarEventoByLogRuta(int logRutaId, BigDecimal x, BigDecimal y, int estadoId) {
		val logRuta = findUltimoLogRuta(logRutaId);
		return registrarEvento(logRuta.getRutaId(), x, y, estadoId);
	}

	@Override
	public int registrarParadaPasajeroByLogRuta(int logRutaId, BigDecimal x, BigDecimal y, int pasajeroId,
			int estadoPasajeroId) {
		val logRuta = findUltimoLogRuta(logRutaId);
		return registrarParadaPasajero(logRuta.getRutaId(), x, y, pasajeroId, estadoPasajeroId);
	}

	@Override
	public int finalizarRecorridoByLogRuta(int logRutaId, BigDecimal x, BigDecimal y) {
		val logRuta = findUltimoLogRuta(logRutaId);
		return finalizarRecorrido(logRuta.getRutaId(), x, y);
	}

	// -----------------------------------------------------------------------------------------------------------------
	// -- Checks
	// -----------------------------------------------------------------------------------------------------------------
	private void checkMonitorParaIniciarRecorrido(int monitorId, RutaDto ruta) {
		if (monitorId != ruta.getMonitorId()) {
			val format = "La ruta con id=%d y código %s no esta asignada a al monitor con id=%d.";
			val msg = String.format(format, ruta.getId(), ruta.getCodigo(), monitorId);
			throw new RuntimeException(msg);
		}
	}

	private void checkRutaParaIniciarRecorrido(RutaDto ruta, int sentido) {
		String msg = "";
		boolean error = false;

		if (ruta.getFechaUltimoRecorrido() != null) {
			val now = LocalDate.now();
			int compareTo = ruta.getFechaUltimoRecorrido().compareTo(now);

			if (compareTo == 0) {
				if (ruta.getSentido() == sentido) {
					if (ruta.getTipoEstado().isActiva()) {
						val format = "La ruta con id=%d y código %s ha iniciado el recorrido en sentido %s. No es posible iniciar el mismo tipo de recorrido el mismo día.";
						msg = String.format(format, ruta.getId(), ruta.getCodigo(), getDescripcionSentido(sentido));
						error = true;
					}

					if (ruta.getTipoEstado().isFinalizado()) {
						val format = "La ruta con id=%d y código %s ya ha finalizado su recorrido en sentido %s. No es posible iniciar el mismo tipo de recorrido el mismo día.";
						msg = String.format(format, ruta.getId(), ruta.getCodigo(), getDescripcionSentido(sentido));
						error = true;
					}
				} else {
					if (ruta.getSentido() == RutaDto.SENTIDO_RETORNO && ruta.getTipoEstado().isFinalizado()) {
						val format = "La ruta con id=%d y código %s ya ha finalizado su recorrido en sentido %s. No es posible iniciar un nuevo recorrido el mismo día.";
						msg = String.format(format, ruta.getId(), ruta.getCodigo(),
								getDescripcionSentido(RutaDto.SENTIDO_RETORNO));
						error = true;
					}
				}
			} else {
				if (compareTo < 0) {
//					if (ruta.getTipoEstado().isActiva()) {
//						val format = "La ruta con id=%d y código %s ya se encuentra activa. No es posible iniciar un nuevo recorrido hasta no haber finalizado el actual.";
//						msg = String.format(format, ruta.getId(), ruta.getCodigo());
//						error = true;
//					}
				} else {
					val format = "La ruta con id=%d y código %s se encuentra en un estado inconsistente. Tiene una fecha de último recorrido igual a %s y la fecha del sistema es igual a %s. CONTACTAR A SOPORTE TÉCNICO.";
					msg = String.format(format, ruta.getId(), ruta.getCodigo(),
							ruta.getFechaUltimoRecorrido().toString(), now.toString());
					error = true;
				}
			}
		}

		if (error) {
			throw new RuntimeException(msg);
		}
	}

	private void checkPasajerosParaIniciarRecorrido(RutaDto ruta, List<PasajeroDto> pasajeros) {
		String msg = "";
		boolean error = false;

		if (pasajeros.isEmpty()) {
			val format = "La ruta con id=%d y código %s no tiene pasajeros.";
			msg = String.format(format, ruta.getId(), ruta.getCodigo());
			error = true;
		} else {
			val list = new ArrayList<PasajeroDto>();
			for (val pasajero : pasajeros) {
				if (pasajero.getDireccionIdaId() == null || pasajero.getDireccionRetornoId() == null) {
					list.add(pasajero);
				}
			}

			if (!list.isEmpty()) {
				if (list.size() > 3) {
					val format = "La ruta con id=%d y código %s tiene %d pasajeros sin una de sus direcciones.Todos los pasajeros de la ruta deben tener configuradas correctamente sus direcciones.";
					msg = String.format(format, ruta.getId(), ruta.getCodigo());
					error = true;
				} else {
					val sb = new StringBuilder();
					val format = "El pasajero %s no tiene correctamente configuradas sus direcciones.";
					for (val pasajero : list) {
						val usuario = ciberService.findUsuarioById(pasajero.getUsuarioId()).get();
						sb.append(String.format(format, usuario.getNombreCompleto())).append("\n");
					}
					msg = sb.toString();
					error = true;
				}
			}
		}

		if (error) {
			throw new RuntimeException(msg);
		}
	}

	private void checkRutaIsActiva(RutaDto ruta) {
		if (!ruta.getTipoEstado().isActiva()) {
			val format = "La ruta con id=%d y código %s no se encuentra activa.";
			val msg = String.format(format, ruta.getId(), ruta.getCodigo());
			throw new RuntimeException(msg);
		}
	}

	private void checkEventoEsDeRecorrido(int estadoId) {
		val estado = estadoRutaService.findOneById(estadoId);
		if (estado.getTipo() != RouteTypeStatus.RECORRIDO) {
			val format = "El estado %s, no corresponde a un evento de RECORRIDO.";
			val msg = String.format(format, estado.getDescripcion());
			throw new RuntimeException(msg);
		}
	}

	private void checkPasajeroParaRegistrarParada(RutaDto ruta, Optional<PasajeroDto> optional) {
		if (optional.isPresent()) {
			val pasajero = optional.get();

			if (pasajero.getRutaId() != ruta.getId()) {
				val format = "El pasajero con id=%d, usuario id=%d y con nombre %s, no esta asignado a la ruta con código=%s.";
				val usuario = ciberService.findUsuarioById(pasajero.getUsuarioId()).get();
				val msg = String.format(format, pasajero.getId(), usuario.getId(), usuario.getNombreCompleto(),
						ruta.getCodigo());
				throw new RuntimeException(msg);
			} else {
				if (pasajero.getTipoEstado().isFinalizado()) {
					// TODO Por lo pronto no se ha definido una regla que impida la corrección del
					// estado de un pasajero.
					// val format = "El estado actual del pasajero es %s, no se puede registrar un
					// nuevo cambio de estado de este pasajero.";
					// val msg = String.format(format, pasajero.getEstadoDescripcion());
					// throw new RuntimeException(msg);
				}
			}
		} else {
			val msg = "No se encontró el pasajero.";
			throw new RuntimeException(msg);
		}
	}

	private void checkRutaParaFinalizarRecorrido(RutaDto ruta) {
		val pasajeros = pasajeroService.findAllByRutaId(ruta.getId());

		val sb = new StringBuilder();
		int n = 0;

		{
			val format = "El estado actual del pasajero %s es %s.";
			for (val pasajero : pasajeros) {
				if (!pasajero.getTipoEstado().isFinalizado()) {
					val usuario = ciberService.findUsuarioById(pasajero.getUsuarioId()).get();
					sb.append(String.format(format, usuario.getNombreCompleto(), pasajero.getEstadoDescripcion()))
							.append("\n");
					n++;
				}
			}
		}

		if (n > 3) {
			val format = "La ruta con id=%d y código %s tiene %d pasajeros en un estado no finalizado.Antes de finalizar el recorrido de la ruta, debe finalizar el de cada uno de sus pasajeros.";
			val msg = String.format(format, ruta.getId(), ruta.getCodigo(), n);
			throw new RuntimeException(msg);
		} else {
			if (n > 0) {
				val format = "Los siguientes pasajeros de la ruta con id=%d y código %s, aún no se les ha finalizado su recorrido: %s";
				val msg = String.format(format, ruta.getId(), ruta.getCodigo(), sb.toString());
				throw new RuntimeException(msg);
			}
		}
	}

	// -----------------------------------------------------------------------------------------------------------------
	// -- Logs
	// -----------------------------------------------------------------------------------------------------------------

	private LogRutaDto findUltimoLogRuta(int logRutaId) {
		val optional = logRutaService.findById(logRutaId);

		if (!optional.isPresent()) {
			throw new RuntimeException(
					"El consecutivo " + logRutaId + " no corresponde a un registro en log de la ruta");
		}

		val result = optional.get();

		return result;
	}

	private LogRutaDto createLogRuta(RutaDto ruta, BigDecimal x, BigDecimal y) {
		ruta.setX(x);
		ruta.setY(y);
		rutaService.update(ruta);

		val result = createLogRuta(ruta.getId(), ruta.getSentido(), ruta.getEstadoId(), x, y);
		return result;
	}

	private LogRutaDto createLogRuta(int rutaId, int sentido, Integer estadoId, BigDecimal x, BigDecimal y) {
		val model = new LogRutaDto();

		model.setRutaId(rutaId);
		model.setSentido(sentido);
		model.setEstadoId(estadoId);
		model.setX(x);
		model.setY(y);

		val result = logRutaService.create(model);
		return result;
	}

	private LogPasajeroDto createLogPasajero(PasajeroDto pasajero, int sentido, Integer estadoPasajeroId, BigDecimal x,
			BigDecimal y) {
		val model = new LogPasajeroDto();

		pasajero.setEstadoId(estadoPasajeroId);
		pasajeroService.update(pasajero);

		model.setRutaId(pasajero.getRutaId());
		model.setUsuarioId(pasajero.getUsuarioId());
		model.setSentido(sentido);
		model.setEstadoId(estadoPasajeroId);
		model.setX(x);
		model.setY(y);

		val result = logPasajeroService.create(model);
		return result;
	}

	protected String getDescripcionSentido(int sentido) {
		switch (sentido) {
		case RutaDto.SENTIDO_IDA:
			return "CAMINO AL COLEGIO";
		case RutaDto.SENTIDO_RETORNO:
			return "CAMINO A CASA";
		default:
			return "DESCONOCIDO";
		}
	}

	private void CheckUsuarioExistente(int usuarioId) {
		val optional = ciberService.findUsuarioById(usuarioId);
		if (optional.isPresent()) {
		} else {
			val format = "El usuario con id=%d no existe.";
			val msg = String.format(format, usuarioId);
			throw new RuntimeException(msg);
		}
	}
}