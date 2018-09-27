package co.com.orbitta.cibercolegios.rutas.service.impl.tracking;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.ciber.client.service.api.InstitucionLocalService;
import co.com.orbitta.cibercolegios.ciber.client.service.api.UsuarioLocalService;
import co.com.orbitta.cibercolegios.rutas.dto.LogPasajeroDto;
import co.com.orbitta.cibercolegios.rutas.dto.LogRutaDto;
import co.com.orbitta.cibercolegios.rutas.dto.PasajeroDto;
import co.com.orbitta.cibercolegios.rutas.dto.RutaDto;
import co.com.orbitta.cibercolegios.rutas.dto.tracking.monitor.DatosPasajeroDto;
import co.com.orbitta.cibercolegios.rutas.dto.tracking.monitor.DatosRutaDto;
import co.com.orbitta.cibercolegios.rutas.enums.TipoEstadoPasajeroEnum;
import co.com.orbitta.cibercolegios.rutas.enums.TipoEstadoRutaEnum;
import co.com.orbitta.cibercolegios.rutas.service.api.DireccionQueryService;
import co.com.orbitta.cibercolegios.rutas.service.api.EstadoPasajeroCrudService;
import co.com.orbitta.cibercolegios.rutas.service.api.EstadoRutaCrudService;
import co.com.orbitta.cibercolegios.rutas.service.api.LogPasajeroCrudService;
import co.com.orbitta.cibercolegios.rutas.service.api.LogRutaCrudService;
import co.com.orbitta.cibercolegios.rutas.service.api.PasajeroQueryService;
import co.com.orbitta.cibercolegios.rutas.service.api.RutaQueryService;
import co.com.orbitta.cibercolegios.rutas.service.api.tracking.MonitorTrackingService;
import lombok.val;

@Service
public class MonitorTrackingServiceImpl implements MonitorTrackingService {

	private static final int SENTIDO_HACIA_EL_COLEGIO = 1;

	@Autowired
	private InstitucionLocalService institucionService;

	@Autowired
	private RutaQueryService rutaService;

	@Autowired
	private UsuarioLocalService usuarioService;

	@Autowired
	private DireccionQueryService direccionService;

	@Autowired
	private PasajeroQueryService pasajeroService;

	@Autowired
	private EstadoRutaCrudService estadoRutaService;

	@Autowired
	private EstadoPasajeroCrudService estadoPasajeroService;

	@Autowired
	private LogRutaCrudService logRutaService;

	@Autowired
	private LogPasajeroCrudService logPasajeroService;

	// -----------------------------------------------------------------------------------------------------------------
	// -- Monitor
	// -----------------------------------------------------------------------------------------------------------------
	@Override
	public List<DatosRutaDto> findRutasByMonitorId(int monitorId) {
		val result = new ArrayList<DatosRutaDto>();

		val rutas = rutaService.findAllByMonitorId(monitorId);

		for (val ruta : rutas) {
			result.add(asDatosRuta(ruta));
		}

		return result;
	}

	@Override
	public DatosRutaDto iniciarRecorrido(int monitorId, int rutaId, BigDecimal x, BigDecimal y, int sentido) {
		val optional = rutaService.findById(rutaId);

		if (optional.isPresent()) {
			val ruta = optional.get();
			if (ruta.getMonitorId() != monitorId) {
				throw new RuntimeException("La ruta no esta asignada a este monitor.");
			} else {
				boolean activa = rutaIsActiva(rutaId);
				if (activa) {
					throw new RuntimeException("La ruta ya esta activa");
				}
			}
		} else {
			throw new RuntimeException("La ruta con id " + rutaId + " no existe.");
		}

		val estadoRutaInicio = estadoRutaService.findEstadoInicioPredeterminado();
		val estadoRutaNormal = estadoRutaService.findEstadoNormalPredeterminado();
		val estadoPasajeroInicio = estadoPasajeroService.findEstadoInicioPredeterminado();
		val pasajeros = pasajeroService.findAllByRutaId(rutaId);

		createLogRuta(rutaId, sentido, estadoRutaInicio.getId(), x, y);
		createLogRuta(rutaId, sentido, estadoRutaNormal.getId(), x, y);
		for (val pasajero : pasajeros) {
			createLogPasajero(pasajero.getId(), sentido, estadoPasajeroInicio.getId());
		}

		val ruta = rutaService.findOneById(rutaId);
		val result = asDatosRuta(ruta);
		return result;
	}

	@Override
	public int registrarPosicion(int logRutaId, BigDecimal x, BigDecimal y) {
		val logRuta = findUltimoLogRuta(logRutaId);
		checkRutaIsActiva(logRuta);

		val estadoRutaId = logRuta.getEstadoId();
		val result = createLogRuta(logRuta.getRutaId(), logRuta.getSentido(), estadoRutaId, x, y);

		return result.getId();
	}

	@Override
	public int registrarEvento(int logRutaId, BigDecimal x, BigDecimal y, int estadoRutaId) {
		val logRuta = findUltimoLogRuta(logRutaId);
		checkRutaIsActiva(logRuta);
		checkEventoEsDeRecorrido(estadoRutaId);

		val result = createLogRuta(logRuta.getRutaId(), logRuta.getSentido(), estadoRutaId, x, y);

		return result.getId();
	}

	private void checkEventoEsDeRecorrido(int estadoRutaId) {
		val estado = estadoRutaService.findOneById(estadoRutaId);
		if (estado.getTipo() != TipoEstadoRutaEnum.RECORRIDO) {
			throw new RuntimeException(
					"El estado " + estado.getDescripcion() + ", no corresponde a un evento de RECORRIDO.");
		}
	}

	@Override
	public int registrarParadaPasajero(int logRutaId, BigDecimal x, BigDecimal y, int pasajeroId,
			int estadoPasajeroId) {
		val logRuta = findUltimoLogRuta(logRutaId);
		checkRutaIsActiva(logRuta);

		val pasajero = findPasajeroById(pasajeroId);

		if (pasajero.getRutaId() != logRuta.getRutaId()) {
			throw new RuntimeException("El estudiante no pertenece a la ruta.");
		}

		createLogPasajero(pasajero.getId(), logRuta.getSentido(), estadoPasajeroId);

		val result = registrarPosicion(logRutaId, x, y);
		return result;
	}

	@Override
	public int finalizarRecorrido(int logRutaId, BigDecimal x, BigDecimal y) {
		val logRuta = findUltimoLogRuta(logRutaId);
		checkRutaIsActiva(logRuta);
		checkTodosLosPasajerosTienenUnEstadoFinal(logRuta);

		val estadoRutaFin = estadoRutaService.findEstadoFinPredeterminado();

		val result = createLogRuta(logRuta.getRutaId(), logRuta.getSentido(), estadoRutaFin.getId(), x, y);

		return result.getId();
	}

	private void checkTodosLosPasajerosTienenUnEstadoFinal(LogRutaDto logRuta) {
		val logs = logPasajeroService.findUltimosLogPasajerosByRutaId(logRuta.getRutaId());

		for (val log : logs) {
			val estado = estadoPasajeroService.findOneById(log.getEstadoId());
			if (!estado.getTipo().equals(TipoEstadoPasajeroEnum.FIN)) {
				if (log.getSentido() == SENTIDO_HACIA_EL_COLEGIO) {
					throw new RuntimeException("No ha finalizado el estado de recogida de todos los estudiantes.");
				} else {
					throw new RuntimeException("No ha finalizado el estado de entrega de todos los estudiantes.");
				}
			}
		}
	}

	@Override
	public Optional<DatosRutaDto> findEstadoRutaByRutaId(int rutaId) {
		val optional = rutaService.findById(rutaId);
		if (optional.isPresent()) {
			val ruta = optional.get();
			val result = asDatosRuta(ruta);
			return Optional.of(result);
		} else {
			return Optional.empty();
		}
	}

	// -----------------------------------------------------------------------------------------------------------------
	// -- Builders
	// -----------------------------------------------------------------------------------------------------------------
	private LogRutaDto createLogRuta(int rutaId, int sentido, int estadoId, BigDecimal x, BigDecimal y) {
		val fechaHora = LocalDateTime.now();

		val model = new LogRutaDto();

		model.setRutaId(rutaId);
		model.setFechaHora(fechaHora);
		model.setSentido(sentido);
		model.setEstadoId(estadoId);
		model.setX(x);
		model.setY(y);

		val result = logRutaService.create(model);
		return result;
	}

	private LogPasajeroDto createLogPasajero(int pasajeroId, int sentido, int estadoId) {
		val fechaHora = LocalDateTime.now();

		val model = new LogPasajeroDto();

		model.setPasajeroId(pasajeroId);
		model.setSentido(sentido);
		model.setFechaHora(fechaHora);
		model.setEstadoId(estadoId);

		val result = logPasajeroService.create(model);
		return result;
	}

	// -----------------------------------------------------------------------------------------------------------------
	// --
	// -----------------------------------------------------------------------------------------------------------------
	private DatosRutaDto asDatosRuta(RutaDto ruta) {
		LogRutaDto logRuta = null;
		boolean activa = false;
		int sentido = SENTIDO_HACIA_EL_COLEGIO;

		// Obtener el ultimo log del dia de la ruta.
		// Si no hay => la ruta no esta activa y se puede iniciar un nuevo recorrido.
		// Si hay => la ruta puede o no estar activa dependiendo de su ultimo estado
		val optional = logRutaService.findUltimoLogRutaByRutaId(ruta.getId());
		if (optional.isPresent()) {
			logRuta = optional.get();
			activa = rutaIsActiva(logRuta);
			sentido = logRuta.getSentido();
		}

		val institucion = institucionService.findOneById(ruta.getInstitucionId());
		val monitor = usuarioService.findOneById(ruta.getMonitorId());
		val conductor = usuarioService.findOneById(ruta.getConductorId());
		val pasajeros = new ArrayList<DatosPasajeroDto>();

		if (activa) {
			pasajeros.addAll(getPasajeros(ruta.getId(), sentido));
		}

		val result = new DatosRutaDto();

		result.setRutaId(ruta.getId());
		result.setCodigo(ruta.getCodigo());
		result.setDescripcion(ruta.getDescripcion());
		result.setMarca(ruta.getMarca());
		result.setPlaca(ruta.getPlaca());
		result.setMovil(ruta.getMovil());
		result.setCapacidad(ruta.getCapacidad());
		result.setInstitucionId(institucion.getId());
		result.setInstitucionNombre(institucion.getNombre());
		result.setInstitucionX(ruta.getX());
		result.setInstitucionY(ruta.getY());
		result.setMonitorId(monitor.getId());
		result.setMonitorNombres(monitor.getNombre());
		result.setMonitorApellidos(monitor.getApellido());
		result.setConductorId(conductor.getId());
		result.setConductorNombres(conductor.getNombre());
		result.setConductorApellidos(conductor.getApellido());
		result.setActiva(activa);
		result.setLogRuta(logRuta);
		result.setPasajeros(pasajeros);

		return result;
	}

	private List<DatosPasajeroDto> getPasajeros(int rutaId, int sentido) {
		val list = new ArrayList<DatosPasajeroDto>();

		val logs = logPasajeroService.findUltimosLogPasajerosByRutaId(rutaId);

		for (val log : logs) {
			val pasajero = pasajeroService.findOneById(log.getPasajeroId());
			val usuario = usuarioService.findOneById(pasajero.getUsuarioId());
			val direccion = direccionService.findOneById(pasajero.getDireccionId());
			val estado = estadoPasajeroService.findOneById(log.getEstadoId());

			val model = new DatosPasajeroDto();

			model.setUsuarioId(usuario.getId());
			model.setSecuencia(pasajero.getSecuencia());
			model.setNombres(usuario.getNombre());
			model.setApellidos(usuario.getApellido());
			model.setEstadoId(estado.getId());
			model.setEstadoDescripcion(estado.getDescripcion());
			model.setTipoEstado(estado.getTipo());
			model.setDireccion(direccion.getDescripcion());
			model.setX(direccion.getX());
			model.setY(direccion.getY());

			list.add(model);
		}

		// @formatter:off
		val result = list.stream()
				.sorted((a, b) -> Integer.compare(a.getSecuencia(), b.getSecuencia()))
				.collect(Collectors.toList());
		// @formatter:on
		return result;
	}

	private boolean rutaIsActiva(int rutaId) {
		boolean result = false;

		val optional = logRutaService.findUltimoLogRutaByRutaId(rutaId);
		if (optional.isPresent()) {
			result = rutaIsActiva(optional.get());
		}

		return result;
	}

	private boolean rutaIsActiva(LogRutaDto logRuta) {
		boolean result = false;

		val estadoRuta = estadoRutaService.findOneById(logRuta.getEstadoId());
		if (!estadoRuta.getTipo().equals(TipoEstadoRutaEnum.FIN)) {
			result = true;
		}

		return result;
	}

	private void checkRutaIsActiva(LogRutaDto logRuta) {
		val estado = estadoRutaService.findById(logRuta.getEstadoId()).get();
		if (estado.getTipo() == TipoEstadoRutaEnum.FIN) {
			throw new RuntimeException("La ruta no se encuentra activa.");
		}
	}

	// -----------------------------------------------------------------------------------------------------------------
	// --
	// -----------------------------------------------------------------------------------------------------------------
	private LogRutaDto findUltimoLogRuta(int logRutaId) {
		val optional = logRutaService.findById(logRutaId);

		if (!optional.isPresent()) {
			throw new RuntimeException(
					"El consecutivo " + logRutaId + " no corresponde a un registro en log de la ruta");
		}

		val result = optional.get();
		val siguiente = logRutaService.findUltimoLogRutaByRutaId(result.getRutaId());

		if (siguiente.isPresent()) {
			if (!siguiente.get().getId().equals(result.getId())) {
				throw new RuntimeException("El consecutivo " + logRutaId
						+ " no es el ultimo registro en log de la ruta. EL ultimo log tiene de la ruta tiene id ="
						+ siguiente.get().getId());
			}
		}

		return result;
	}

	private PasajeroDto findPasajeroById(int pasajeroId) {
		val optional = pasajeroService.findById(pasajeroId);

		if (!optional.isPresent()) {
			throw new RuntimeException("El estudiante con id " + pasajeroId + " no existe.");
		}

		val result = optional.get();
		return result;
	}
}