package co.com.orbitta.cibercolegios.rutas.service.impl.tracking;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.dto.EstadoDto;
import co.com.orbitta.cibercolegios.dto.LogDto;
import co.com.orbitta.cibercolegios.dto.LogUsuarioDto;
import co.com.orbitta.cibercolegios.dto.RutaDto;
import co.com.orbitta.cibercolegios.dto.UbicacionRutaDto;
import co.com.orbitta.cibercolegios.dto.UsuarioRutaDto;
import co.com.orbitta.cibercolegios.dto.tracking.DatosRutaDto;
import co.com.orbitta.cibercolegios.dto.tracking.PasajeroRutaDto;
import co.com.orbitta.cibercolegios.enums.TipoEventoEnum;
import co.com.orbitta.cibercolegios.rutas.service.api.DireccionUsuarioCrudService;
import co.com.orbitta.cibercolegios.rutas.service.api.EstadoCrudService;
import co.com.orbitta.cibercolegios.rutas.service.api.InstitucionCrudService;
import co.com.orbitta.cibercolegios.rutas.service.api.LogCrudService;
import co.com.orbitta.cibercolegios.rutas.service.api.LogUsuarioCrudService;
import co.com.orbitta.cibercolegios.rutas.service.api.RutaCrudService;
import co.com.orbitta.cibercolegios.rutas.service.api.UbicacionRutaCrudService;
import co.com.orbitta.cibercolegios.rutas.service.api.UsuarioCrudService;
import co.com.orbitta.cibercolegios.rutas.service.api.UsuarioRutaCrudService;
import co.com.orbitta.cibercolegios.rutas.service.api.tracking.TrackingService;
import lombok.val;

@Service
public class TrackingServiceImpl implements TrackingService {

	private static final int SENTIDO_HACIA_EL_COLEGIO = 1;

	private static final int SENTIDO_DESDE_EL_COLEGIO = 2;

	@Autowired
	private InstitucionCrudService institucionCrudService;

	@Autowired
	private EstadoCrudService estadoCrudService;

	@Autowired
	private LogCrudService logCrudService;

	@Autowired
	private UsuarioCrudService usuarioCrudService;

	@Autowired
	private DireccionUsuarioCrudService direccionUsuarioCrudService;

	@Autowired
	private RutaCrudService rutaCrudService;

	@Autowired
	private UsuarioRutaCrudService usuarioRutaCrudService;

	@Autowired
	private UbicacionRutaCrudService ubicacionRutaCrudService;

	@Autowired
	private LogUsuarioCrudService logUsuarioCrudService;

	// TODO iniciarRecorrido: ¿Se debe crear un registro inicial para cada pasajero
	// en la tabla LogUsuario?
	// TODO Despues de recogido/entregado un pasajero, en la ubicación de la ruta
	// queda en ese estado y como el metodo de registro de posición toma siempre el
	// estado anterior, cual es el plan para que el estado de recogida/entrega
	// desaparezca y despues de eso en cual estado debo colocarlo

	// TODO registrarEventoEnRecorrido: Validar que el estado sea uno de de tipo
	// evento RECORRIDO
	// TODO registrarParadaPasajero: Validar que el estado sea uno de de tipo
	// evento PARADA

	// TODO registrarParadaPasajero: Validar que el pasajero no haya sido
	// recogido/retornado previamente
	// TODO registrarParadaPasajero: En el retorno si un niño no fue entregado por
	// el colegio (i.e. se lo llevarón a una clinica), crearemos un estado especial
	// para indicar que el colegio no lo entregó y asi cerrar a cada pasajero

	// -----------------------------------------------------------------------------------------------------------------
	// --
	// -----------------------------------------------------------------------------------------------------------------
	@Override
	public DatosRutaDto iniciarRecorrido(int monitorId, int sentido, BigDecimal cx, BigDecimal cy) {
		val ruta = getRutaByMonitorId(monitorId);
		val estado = getEstadoInicioRecorrido(sentido);
		val fechaHora = LocalDateTime.now();

		val ubicacionRuta = createUbicacionRuta(ruta.getId(), sentido, fechaHora, estado.getId(), cx, cy);

		val result = getDatosRuta(ubicacionRuta.getId(), ruta, fechaHora.toLocalDate());
		return result;
	}

	@Override
	public int registrarPosicion(int ultimaUbicacionRutaId, BigDecimal cx, BigDecimal cy) {
		val ubicacion = getUltimaUbicacionRutaById(ultimaUbicacionRutaId);
		val fechaHora = LocalDateTime.now();

		val result = createUbicacionRuta(ubicacion.getRutaId(), ubicacion.getSentido(), fechaHora,
				ubicacion.getEstadoId(), cx, cy);

		return result.getId();
	}

	@Override
	public int registrarEventoEnRecorrido(int ultimaUbicacionRutaId, BigDecimal cx, BigDecimal cy, int estadoId) {
		val ubicacion = getUltimaUbicacionRutaById(ultimaUbicacionRutaId);
		val fechaHora = LocalDateTime.now();

		getEstadoById(estadoId);

		val result = createUbicacionRuta(ubicacion.getRutaId(), ubicacion.getSentido(), fechaHora, estadoId, cx, cy);

		return result.getId();
	}

	@Override
	public int registrarParadaPasajero(int ultimaUbicacionRutaId, BigDecimal cx, BigDecimal cy, int estadoId,
			int usuarioRutaId, int logId) {
		val ubicacion = getUltimaUbicacionRutaById(ultimaUbicacionRutaId);
		val fechaHora = LocalDateTime.now();

		val usuarioRuta = getUsuarioRutaById(usuarioRutaId);
		if (usuarioRuta.getRutaId() != ubicacion.getRutaId()) {
			// TODO error porque el pasajero no pertenece a la ruta
		}

		if (!usuarioRuta.getFecha().isEqual(ubicacion.getFechaHora().toLocalDate())) {
			// TODO error porque el pasajero no pertenece a la ruta por fecha
		}

		getEstadoById(estadoId);
		getLogById(logId);

		createLogUsuario(usuarioRutaId, ubicacion.getSentido(), fechaHora, estadoId, logId);

		val result = createUbicacionRuta(ubicacion.getRutaId(), ubicacion.getSentido(), fechaHora, estadoId, cx, cy);

		return result.getId();
	}

	@Override
	public int finalizarRecorrido(int ultimaUbicacionRutaId, BigDecimal cx, BigDecimal cy, int estadoId) {
		val ubicacion = getUltimaUbicacionRutaById(ultimaUbicacionRutaId);
		val fechaHora = LocalDateTime.now();

		val estado = getEstadoById(estadoId);
		if (estado.getTipoEvento() != TipoEventoEnum.FIN) {
			// TODO Error porque no se trata de un estado de finalizacion de ruta
		}

		val result = createUbicacionRuta(ubicacion.getRutaId(), ubicacion.getSentido(), fechaHora, estadoId, cx, cy);

		return result.getId();
	}

	@Override
	public DatosRutaDto getInformacionRuta(int rutaId) {
		val ruta = getRutaById(rutaId);
		val fecha = LocalDate.now();
		val optional = ubicacionRutaCrudService.findUltimaUbicacionByRutaIdAndFecha(rutaId, fecha);

		if(!optional.isPresent()) {
			//TODO que se retorna en estos casos?
		}
		val result = getDatosRuta(optional.get().getId(), ruta, fecha);
		return result;
	}

	@Override
	public void getEstadoRuta() {
		// TODO Auto-generated method stub

	}

	// -----------------------------------------------------------------------------------------------------------------
	// -- Builders
	// -----------------------------------------------------------------------------------------------------------------
	private UbicacionRutaDto createUbicacionRuta(int rutaId, int sentido, LocalDateTime fechaHora, int estadoId,
			BigDecimal cx, BigDecimal cy) {
		// @formatter:off
		val model = UbicacionRutaDto
				.builder()
				.rutaId(rutaId)
				.sentido(sentido)
				.fechaHora(fechaHora)
				.estadoId(estadoId)
				.ubicacionLon(cx)
				.ubicacionLat(cy)
				.ubicacionGeo(null)
				.build();
		// @formatter:on

		val result = ubicacionRutaCrudService.create(model);
		return result;
	}

	private LogUsuarioDto createLogUsuario(int usuarioRutaId, int sentido, LocalDateTime fechaHora, int estadoId,
			int logId) {
		// @formatter:off
		val model = LogUsuarioDto
				.builder()
				.usuarioRutaId(usuarioRutaId)
				.sentido(sentido)
				.fechaHora(fechaHora)
				.estadoId(estadoId)
				.logId(logId)
				.build();
		// @formatter:on

		val result = logUsuarioCrudService.create(model);
		return result;
	}

	private DatosRutaDto getDatosRuta(int ubicacionRutaId, RutaDto ruta, LocalDate fecha) {
		DatosRutaDto result;
		val institucion = institucionCrudService.findById(ruta.getInstitucionId()).get();
		val monitor = usuarioCrudService.findById(ruta.getMonitorId()).get();
		val conductor = usuarioCrudService.findById(ruta.getConductorId()).get();
		val pasajeros = getPasajeros(ruta.getId(), fecha);

		// @formatter:off
		result = DatosRutaDto
				.builder()
				.ubicacionRutaId(ubicacionRutaId)
				.ruta(ruta)
				.institucion(institucion)
				.monitor(monitor)
				.conductor(conductor)
				.pasajeros(pasajeros)
				.build();
		// @formatter:on

		return result;
	}

	private List<PasajeroRutaDto> getPasajeros(int rutaId, LocalDate fecha) {
		val result = new ArrayList<PasajeroRutaDto>();

		val usuariosRuta = usuarioRutaCrudService.findAllByRutaIdAndFecha(rutaId, fecha);

		// TODO se requiere que el numero de la parada del estudiante venga desde la BD
		int i = 1;
		for (val e : usuariosRuta) {
			val usuario = usuarioCrudService.findById(e.getUsuarioId()).get();
			val direccion = direccionUsuarioCrudService.findById(e.getDireccionUsuarioId()).get();

			// @formatter:off
			val pasajero = PasajeroRutaDto
					.builder()
					.usuarioRutaId(e.getId())
					.usuarioId(usuario.getId())
					.nombre1(usuario.getNombre1())
					.nombre2(usuario.getNombre2())
					.apellido1(usuario.getApellido1())
					.apellido2(usuario.getApellido2())
					.secuencia(i)
					.direccion(direccion.getDescripcion())
					.ubicacionLat(direccion.getUbicacionLat())
					.ubicacionLon(direccion.getUbicacionLon())
					.ubicacionGeo(direccion.getUbicacionGeo())
					.build();
			// @formatter:on

			result.add(pasajero);
		}

		return result;
	}

	// -----------------------------------------------------------------------------------------------------------------
	// --
	// -----------------------------------------------------------------------------------------------------------------
	private UbicacionRutaDto getUltimaUbicacionRutaById(int ubicacionRutaId) {
		val optional = ubicacionRutaCrudService.findById(ubicacionRutaId);

		if (!optional.isPresent()) {
			// TODO generar error porque no existe la ultima ubicación
		}

		val result = optional.get();
		val siguiente = ubicacionRutaCrudService.findSiguienteUbicacion(result.getRutaId(), result.getId());
		if (siguiente.isPresent()) {
			// TODO generar error porque existe otra ubicación
		}

		val estado = estadoCrudService.findById(result.getId()).get();
		if (estado.getTipoEvento() == TipoEventoEnum.FIN) {
			// TODO generar error porque el recorrido ya termino
		}

		return result;
	}

	// -----------------------------------------------------------------------------------------------------------------
	// --
	// -----------------------------------------------------------------------------------------------------------------
	private RutaDto getRutaById(int rutaId) {
		val optional = rutaCrudService.findById(rutaId);

		if (!optional.isPresent()) {
			// TODO error porque el monitor no tiene una ruta asignada
		}

		val result = optional.get();
		return result;
	}

	private RutaDto getRutaByMonitorId(int monitorId) {
		val optional = rutaCrudService.findByMonitorId(monitorId);

		if (!optional.isPresent()) {
			// TODO error porque el monitor no tiene una ruta asignada
		}

		val result = optional.get();
		return result;
	}

	protected EstadoDto getEstadoInicioRecorrido(int sentido) {
		String descripcion = "";

		switch (sentido) {
		case SENTIDO_HACIA_EL_COLEGIO:
			descripcion = EstadoDto.INICIANDO_RUTA_HACIA_EL_COLEGIO;
			break;
		case SENTIDO_DESDE_EL_COLEGIO:
			descripcion = EstadoDto.INICIANDO_RUTA_DESDE_EL_COLEGIO;
			break;
		default:
			break;
		}

		val optional = estadoCrudService.findByDescripcion(descripcion);
		if (!optional.isPresent()) {
			// TODO generar error si el estado no existe
		}

		val result = optional.get();
		return result;
	}

	private EstadoDto getEstadoById(int estadoId) {
		val optional = estadoCrudService.findById(estadoId);

		if (!optional.isPresent()) {
			// TODO error porque el monitor no tiene una ruta asignada
		}

		val result = optional.get();
		return result;
	}

	private UsuarioRutaDto getUsuarioRutaById(int usuarioRutaId) {
		val optional = usuarioRutaCrudService.findById(usuarioRutaId);

		if (!optional.isPresent()) {
			// TODO error porque el usuario no existe como pasajero
		}

		val result = optional.get();
		return result;
	}

	private LogDto getLogById(Integer logId) {
		val optional = logCrudService.findById(logId);

		if (!optional.isPresent()) {
			// TODO error porque el usuario no existe como pasajero
		}

		val result = optional.get();
		return result;
	}
}
