package co.com.orbitta.cibercolegios.rutas.service.impl.tracking;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.dto.EstadoRutaDto;
import co.com.orbitta.cibercolegios.dto.EstadoUsuarioRutaDto;
import co.com.orbitta.cibercolegios.dto.LogRutaDto;
import co.com.orbitta.cibercolegios.dto.LogUsuarioRutaDto;
import co.com.orbitta.cibercolegios.dto.RutaDto;
import co.com.orbitta.cibercolegios.dto.UsuarioRutaDto;
import co.com.orbitta.cibercolegios.dto.tracking.DatosRutaDto;
import co.com.orbitta.cibercolegios.dto.tracking.DatosUsuarioRutaDto;
import co.com.orbitta.cibercolegios.enums.TipoEstadoRutaEnum;
import co.com.orbitta.cibercolegios.rutas.service.api.DireccionUsuarioCrudService;
import co.com.orbitta.cibercolegios.rutas.service.api.EstadoRutaCrudService;
import co.com.orbitta.cibercolegios.rutas.service.api.EstadoUsuarioRutaCrudService;
import co.com.orbitta.cibercolegios.rutas.service.api.InstitucionCrudService;
import co.com.orbitta.cibercolegios.rutas.service.api.LogRutaCrudService;
import co.com.orbitta.cibercolegios.rutas.service.api.LogUsuarioRutaCrudService;
import co.com.orbitta.cibercolegios.rutas.service.api.RutaCrudService;
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
	private EstadoRutaCrudService estadoRutaCrudService;

	@Autowired
	private EstadoUsuarioRutaCrudService estadoUsuarioRutaCrudService;

	@Autowired
	private RutaCrudService rutaCrudService;

	@Autowired
	private UsuarioRutaCrudService usuarioRutaCrudService;

	@Autowired
	private UsuarioCrudService usuarioCrudService;

	@Autowired
	private DireccionUsuarioCrudService direccionUsuarioCrudService;

	@Autowired
	private LogRutaCrudService logRutaCrudService;

	@Autowired
	private LogUsuarioRutaCrudService logUsuarioRutaCrudService;

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
	// -- Monitor
	// -----------------------------------------------------------------------------------------------------------------
	@Override
	public List<DatosRutaDto> getRutasByMonitorId(int monitorId) {
		val result = new ArrayList<DatosRutaDto>();

		val rutas = rutaCrudService.findAllByMonitorId(monitorId);

		for (val ruta : rutas) {
			result.add(getRuta(ruta));
		}

		return result;
	}

	private DatosRutaDto getRuta(RutaDto ruta) {
		LogRutaDto logRuta = null;
		boolean activa = false;
		Integer ultimoSentido = null;

		// Obtener el ultimo log del dia de la ruta.
		// Si no hay => la ruta no esta activa y se debe iniciar un nuevo recorrido
		// Si hay => la ruta puede o no estar activa depende de si su ultimo estado es
		// de FIN
		// Si no es de FIN => la ruta esta activa, se debe continuar con el recorrido.
		// Sino => la ruta no esta activa y se debe iniciar un nuevo recorrido
		val optional = logRutaCrudService.findIdUltimoLogRutaDelDiaByRutaId(ruta.getId());
		if (optional.isPresent()) {
			logRuta = logRutaCrudService.findOneById(optional.get());

			val estadoRuta = estadoRutaCrudService.findOneById(logRuta.getId());
			if (!estadoRuta.getTipo().equals(TipoEstadoRutaEnum.FIN)) {
				activa = true;
			}
			ultimoSentido = logRuta.getSentido();
		}

		val institucion = institucionCrudService.findOneById(ruta.getInstitucionId());
		val monitor = usuarioCrudService.findOneById(ruta.getMonitorId());
		val conductor = usuarioCrudService.findOneById(ruta.getConductorId());
		val pasajeros = new ArrayList<DatosUsuarioRutaDto>();
		if (activa) {
			pasajeros.addAll(getPasajeros(ruta.getId()));
		}

		// @formatter:off
		val result = DatosRutaDto
				.builder()
				.ruta(ruta)
				.logRuta(logRuta)
				.activa(activa)
				.ultimoSentido(ultimoSentido)
				.institucion(institucion)
				.monitor(monitor)
				.conductor(conductor)
				.numeroPasajeros(pasajeros.size())
				.pasajeros(pasajeros)
				.build();
		// @formatter:on

		return result;
	}

	@Override
	public DatosRutaDto iniciarRecorrido(int rutaId, int sentido, BigDecimal x, BigDecimal y) {
		// val ruta = getRutasByMonitorId(monitorId);
		// val estado = getEstadoInicioRecorrido(sentido);
		// val fechaHora = LocalDateTime.now();
		//
		// val ubicacionRuta = createUbicacionRuta(ruta.getId(), sentido, fechaHora,
		// estado.getId(), cx, cy);
		//
		// val result = getDatosRuta(ubicacionRuta.getId(), ruta,
		// fechaHora.toLocalDate());
		// return result;
		return null;
	}

	@Override
	public int registrarPosicion(int logRutaId, BigDecimal x, BigDecimal y) {
		// val ubicacion = getUltimaUbicacionRutaById(ultimaUbicacionRutaId);
		// val fechaHora = LocalDateTime.now();
		//
		// val result = createUbicacionRuta(ubicacion.getRutaId(),
		// ubicacion.getSentido(), fechaHora,
		// ubicacion.getEstadoId(), cx, cy);
		//
		// return result.getId();
		return 0;
	}

	@Override
	public int registrarEvento(int logRutaId, BigDecimal x, BigDecimal y, int estadoRutaId) {
		// val ubicacion = getUltimaUbicacionRutaById(ultimaUbicacionRutaId);
		// val fechaHora = LocalDateTime.now();
		//
		// getEstadoById(estadoId);
		//
		// val result = createUbicacionRuta(ubicacion.getRutaId(),
		// ubicacion.getSentido(), fechaHora, estadoId, cx, cy);
		//
		// return result.getId();
		return 0;
	}

	@Override
	public int registrarParadaPasajero(int logRutaId, BigDecimal x, BigDecimal y, int usuarioRutaId,
			int estadoUsuarioRutaId) {
		// val ubicacion = getUltimaUbicacionRutaById(ultimaUbicacionRutaId);
		// val fechaHora = LocalDateTime.now();
		//
		// val usuarioRuta = getUsuarioRutaById(usuarioRutaId);
		// if (usuarioRuta.getRutaId() != ubicacion.getRutaId()) {
		// // TODO error porque el pasajero no pertenece a la ruta
		// }
		//
		// if (!usuarioRuta.getFecha().isEqual(ubicacion.getFechaHora().toLocalDate()))
		// {
		// // TODO error porque el pasajero no pertenece a la ruta por fecha
		// }
		//
		// getEstadoById(estadoId);
		// getLogById(logId);
		//
		// createLogUsuario(usuarioRutaId, ubicacion.getSentido(), fechaHora, estadoId,
		// logId);
		//
		// val result = createUbicacionRuta(ubicacion.getRutaId(),
		// ubicacion.getSentido(), fechaHora, estadoId, cx, cy);
		//
		// return result.getId();
		return 0;
	}

	@Override
	public int finalizarRecorrido(int logRutaId, BigDecimal x, BigDecimal y, int estadoRutaId) {
		// val ubicacion = getUltimaUbicacionRutaById(ultimaUbicacionRutaId);
		// val fechaHora = LocalDateTime.now();
		//
		// val estado = getEstadoById(estadoId);
		// if (estado.getTipoEvento() != TipoEstadoRutaEnum.FIN) {
		// // TODO Error porque no se trata de un estado de finalizacion de ruta
		// }
		//
		// val result = createUbicacionRuta(ubicacion.getRutaId(),
		// ubicacion.getSentido(), fechaHora, estadoId, cx, cy);
		//
		// return result.getId();
		return 0;
	}

	// -----------------------------------------------------------------------------------------------------------------
	// -- Builders
	// -----------------------------------------------------------------------------------------------------------------
	private LogRutaDto createUbicacionRuta(int rutaId, int sentido, LocalDateTime fechaHora, int estadoId, BigDecimal x,
			BigDecimal y) {
		// @formatter:off
		val model = LogRutaDto
				.builder()
				.rutaId(rutaId)
				.sentido(sentido)
				.fechaHora(fechaHora)
				.estadoId(estadoId)
				.x(x)
				.y(y)
				.build();
		// @formatter:on

		val result = logRutaCrudService.create(model);
		return result;
	}

	private LogUsuarioRutaDto createLogUsuario(int usuarioRutaId, int sentido, LocalDateTime fechaHora, int estadoId,
			int logId) {
		// @formatter:off
		val model = LogUsuarioRutaDto
				.builder()
				.usuarioRutaId(usuarioRutaId)
				.sentido(sentido)
				.fechaHora(fechaHora)
				.estadoId(estadoId)
				.build();
		// @formatter:on

		val result = logUsuarioRutaCrudService.create(model);
		return result;
	}

	private List<DatosUsuarioRutaDto> getPasajeros(int rutaId) {
		val result = new ArrayList<DatosUsuarioRutaDto>();

		val usuariosRuta = usuarioRutaCrudService.findAllByRutaId(rutaId);

		// TODO se requiere que el numero de la parada del estudiante venga desde la BD
		int i = 1;
		for (val e : usuariosRuta) {
			val usuario = usuarioCrudService.findById(e.getUsuarioId()).get();
			val direccion = direccionUsuarioCrudService.findById(e.getDireccionUsuarioId()).get();

			// @formatter:off
			val pasajero = DatosUsuarioRutaDto
					.builder()
					.usuarioRutaId(e.getId())
					.usuarioId(usuario.getId())
					.nombre1(usuario.getNombre1())
					.nombre2(usuario.getNombre2())
					.apellido1(usuario.getApellido1())
					.apellido2(usuario.getApellido2())
					.secuencia(i)
					.direccion(direccion.getDescripcion())
					.x(direccion.getX())
					.y(direccion.getY())
					.build();
			// @formatter:on

			result.add(pasajero);
		}

		return result;
	}

	// -----------------------------------------------------------------------------------------------------------------
	// --
	// -----------------------------------------------------------------------------------------------------------------
	private LogRutaDto getUltimaUbicacionRutaById(int ubicacionRutaId) {
		val optional = logRutaCrudService.findById(ubicacionRutaId);

		if (!optional.isPresent()) {
			// TODO generar error porque no existe la ultima ubicación
		}

		val result = optional.get();
		val siguiente = logRutaCrudService.findSiguienteUbicacion(result.getRutaId(), result.getId());
		if (siguiente.isPresent()) {
			// TODO generar error porque existe otra ubicación
		}

		val estado = estadoRutaCrudService.findById(result.getId()).get();
		if (estado.getTipo() == TipoEstadoRutaEnum.FIN) {
			// TODO generar error porque el recorrido ya termino
		}

		return result;
	}

	// -----------------------------------------------------------------------------------------------------------------
	// --
	// -----------------------------------------------------------------------------------------------------------------

	protected EstadoRutaDto getEstadoInicioRecorrido(int sentido) {
//		String descripcion = "";
//
//		switch (sentido) {
//		case SENTIDO_HACIA_EL_COLEGIO:
//			descripcion = EstadoRutaDto.INICIANDO_RUTA_HACIA_EL_COLEGIO;
//			break;
//		case SENTIDO_DESDE_EL_COLEGIO:
//			descripcion = EstadoRutaDto.INICIANDO_RUTA_DESDE_EL_COLEGIO;
//			break;
//		default:
//			break;
//		}
//
//		val optional = estadoRutaCrudService.findByDescripcion(descripcion);
//		if (!optional.isPresent()) {
//			// TODO generar error si el estado no existe
//		}
//
//		val result = optional.get();
//		return result;
		return null;
	}

	private EstadoRutaDto getEstadoById(int estadoId) {
		val optional = estadoRutaCrudService.findById(estadoId);

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

	private EstadoUsuarioRutaDto getLogById(Integer logId) {
		val optional = estadoUsuarioRutaCrudService.findById(logId);

		if (!optional.isPresent()) {
			// TODO error porque el usuario no existe como pasajero
		}

		val result = optional.get();
		return result;
	}

	@Override
	public DatosRutaDto getRutaByUsuarioId(int usuarioId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DatosUsuarioRutaDto getEstadoUsuarioRutaById(int usuarioRutaId) {
		// TODO Auto-generated method stub
		return null;
	}
}
