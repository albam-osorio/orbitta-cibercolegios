package co.com.orbitta.cibercolegios.rutas.service.impl.tracking;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.dto.EstadoPasajeroDto;
import co.com.orbitta.cibercolegios.dto.EstadoRutaDto;
import co.com.orbitta.cibercolegios.dto.LogRutaDto;
import co.com.orbitta.cibercolegios.dto.tracking.acudiente.DatosEstadoParadaDto;
import co.com.orbitta.cibercolegios.dto.tracking.acudiente.DatosParadaDto;
import co.com.orbitta.cibercolegios.enums.TipoEstadoRutaEnum;
import co.com.orbitta.cibercolegios.rutas.service.api.EstadoPasajeroCrudService;
import co.com.orbitta.cibercolegios.rutas.service.api.EstadoRutaCrudService;
import co.com.orbitta.cibercolegios.rutas.service.api.LogPasajeroCrudService;
import co.com.orbitta.cibercolegios.rutas.service.api.LogRutaCrudService;
import co.com.orbitta.cibercolegios.rutas.service.api.readonly.DireccionQueryService;
import co.com.orbitta.cibercolegios.rutas.service.api.readonly.InstitucionQueryService;
import co.com.orbitta.cibercolegios.rutas.service.api.readonly.PasajeroQueryService;
import co.com.orbitta.cibercolegios.rutas.service.api.readonly.RutaQueryService;
import co.com.orbitta.cibercolegios.rutas.service.api.readonly.UsuarioQueryService;
import co.com.orbitta.cibercolegios.rutas.service.api.tracking.AcudienteTrackingService;
import lombok.val;

@Service
public class AcudienteTrackingServiceImpl implements AcudienteTrackingService {

	@Autowired
	private InstitucionQueryService institucionService;

	@Autowired
	private RutaQueryService rutaService;

	@Autowired
	private UsuarioQueryService usuarioService;

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
	// -- Acudiente
	// -----------------------------------------------------------------------------------------------------------------
	@Override
	public DatosParadaDto findParadaByEstudianteId(int estudianteId) {
		val pasajero = pasajeroService.findOneById(estudianteId);
		val ruta = rutaService.findOneById(pasajero.getRutaId());
		val institucion = institucionService.findOneById(ruta.getInstitucionId());
		val monitor = usuarioService.findOneById(ruta.getMonitorId());
		val conductor = usuarioService.findOneById(ruta.getConductorId());

		boolean activa = false;
		val optional = logRutaService.findUltimoLogRutaByRutaId(ruta.getId());
		if (optional.isPresent()) {
			activa = rutaIsActiva(optional.get());
		}
		val estudiante = usuarioService.findOneById(pasajero.getUsuarioId());
		val totalParadas = pasajeroService.numeroParadas(pasajero.getRutaId());
		val direccion = direccionService.findOneById(pasajero.getDireccionId());

		// @formatter:off
		val result = DatosParadaDto
				.builder()
				.rutaId(ruta.getId())
				.codigo(ruta.getCodigo())
				.descripcion(ruta.getDescripcion())
				.marca(ruta.getMarca())
				.placa(ruta.getPlaca())
				.movil(ruta.getMovil())
				.capacidad(ruta.getCapacidad())
				.institucionId(institucion.getId())
				.institucionNombre(institucion.getDescripcion())
				.institucionX(ruta.getX())
				.institucionY(ruta.getY())
				.monitorId(monitor.getId())
				.monitorNombres(monitor.getNombre())
				.monitorApellidos(monitor.getApellido())
				.conductorId(conductor.getId())
				.conductorNombres(conductor.getNombre())
				.conductorApellidos(conductor.getApellido())
				.activa(activa)
				.usuarioRutaId(pasajero.getId())
				.estudianteId(estudiante.getId())
				.estudianteNombres(estudiante.getNombre())
				.estudianteApellidos(estudiante.getApellido())
				.totalParadas(totalParadas)
				.secuencia(pasajero.getSecuencia())
				.direccion(direccion.getDescripcion())
				.paradaX(direccion.getX())
				.paradaY(direccion.getY())
				.build();
		// @formatter:on

		return result;
	}

	@Override
	public DatosEstadoParadaDto findEstadoParadaByEstudianteId(int estudianteId) {
		val pasajero = pasajeroService.findOneById(estudianteId);
		val ruta = rutaService.findOneById(pasajero.getRutaId());

		EstadoRutaDto estadoRuta = null;
		EstadoPasajeroDto estadoPasajero = null;
		BigDecimal x = ruta.getX();
		BigDecimal y = ruta.getY();

		val opLogRuta = logRutaService.findUltimoLogRutaByRutaId(ruta.getId());
		if (opLogRuta.isPresent()) {
			val logRuta = opLogRuta.get();
			estadoRuta = estadoRutaService.findOneById(opLogRuta.get().getEstadoId());
			x = logRuta.getX();
			y = logRuta.getY();

			if (rutaIsActiva(logRuta)) {
				val opLogPasajero = logPasajeroService.findUltimoLogPasajerosByRutaIdAndPasajeroId(ruta.getId(),
						pasajero.getRutaId());
				if (opLogPasajero.isPresent()) {
					estadoPasajero = estadoPasajeroService.findOneById(opLogPasajero.get().getEstadoId());
				}
			}
		}

		if (estadoRuta == null) {
			estadoRuta = estadoRutaService.findEstadoFinPredeterminado();
		}

		if (estadoPasajero == null) {
			estadoPasajero = estadoPasajeroService.findEstadoInicioPredeterminado();
		}

		// @formatter:off
		val result = DatosEstadoParadaDto
				.builder()
				.estadoRutaId(estadoRuta.getId())
				.estadoRutaNombre(estadoRuta.getDescripcion())
				.estadoPasajeroId(estadoPasajero.getId())
				.estadoPasajeroNombre(estadoPasajero.getDescripcion())
				.x(x)
				.y(y)
				.build();
		// @formatter:on

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
}