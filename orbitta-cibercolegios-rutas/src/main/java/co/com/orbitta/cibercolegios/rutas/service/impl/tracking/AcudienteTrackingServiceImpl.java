package co.com.orbitta.cibercolegios.rutas.service.impl.tracking;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.ciber.client.service.api.InstitucionLocalService;
import co.com.orbitta.cibercolegios.ciber.client.service.api.UsuarioLocalService;
import co.com.orbitta.cibercolegios.rutas.dto.LogPasajeroDto;
import co.com.orbitta.cibercolegios.rutas.dto.LogRutaDto;
import co.com.orbitta.cibercolegios.rutas.dto.tracking.acudiente.DatosEstadoParadaDto;
import co.com.orbitta.cibercolegios.rutas.dto.tracking.acudiente.DatosParadaDto;
import co.com.orbitta.cibercolegios.rutas.enums.TipoEstadoRutaEnum;
import co.com.orbitta.cibercolegios.rutas.service.api.DireccionQueryService;
import co.com.orbitta.cibercolegios.rutas.service.api.EstadoRutaCrudService;
import co.com.orbitta.cibercolegios.rutas.service.api.LogPasajeroCrudService;
import co.com.orbitta.cibercolegios.rutas.service.api.LogRutaCrudService;
import co.com.orbitta.cibercolegios.rutas.service.api.PasajeroQueryService;
import co.com.orbitta.cibercolegios.rutas.service.api.RutaQueryService;
import co.com.orbitta.cibercolegios.rutas.service.api.tracking.AcudienteTrackingService;
import lombok.val;

@Service
public class AcudienteTrackingServiceImpl implements AcudienteTrackingService {

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
	private LogRutaCrudService logRutaService;

	@Autowired
	private LogPasajeroCrudService logPasajeroService;

	// -----------------------------------------------------------------------------------------------------------------
	// -- Acudiente
	// -----------------------------------------------------------------------------------------------------------------
	@Override
	public Optional<DatosParadaDto> findParadaByUsuarioId(int usuarioId) {
		val optional = pasajeroService.findByUsuarioId(usuarioId);

		if (optional.isPresent()) {
			val pasajero = optional.get();

			val ruta = rutaService.findOneById(pasajero.getRutaId());
			val institucion = institucionService.findOneById(ruta.getInstitucionId());
			val monitor = usuarioService.findOneById(ruta.getMonitorId());
			val conductor = usuarioService.findOneById(ruta.getConductorId());
			val logRuta = logRutaService.findUltimoLogRutaByRutaId(ruta.getId());
			val logsPasajeros = logPasajeroService.findUltimosLogPasajerosByRutaId(ruta.getId());

			boolean activa = false;
			if (logRuta.isPresent()) {
				activa = rutaIsActiva(logRuta.get());
			}
			val estudiante = usuarioService.findOneById(pasajero.getUsuarioId());
			val direccion = direccionService.findOneById(pasajero.getDireccionId());

			val result = new DatosParadaDto();

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

			result.setEstudianteId(estudiante.getId());
			result.setSecuencia(pasajero.getSecuencia());
			result.setEstudianteNombres(estudiante.getNombre());
			result.setEstudianteApellidos(estudiante.getApellido());

			if (logRuta.isPresent()) {
				val log = logRuta.get();
				result.setEstadoRutaId(log.getEstadoId());
				result.setEstadoRutaNombre(log.getEstadoNombre());
				result.setTipoEstadoRuta(log.getTipoEstado());
				result.setRutaX(log.getX());
				result.setRutaY(log.getY());
			}

			{
				val o = logsPasajeros.stream().filter(a -> a.getPasajeroId() == pasajero.getId()).findFirst();
				if (o.isPresent()) {
					val l = o.get();
					result.setEstadoEstudianteId(l.getEstadoId());
					result.setEstadoEstudianteNombre(l.getEstadoNombre());
					result.setTipoEstadoEstudiante(l.getTipoEstado());
				}
			}

			result.setDireccion(direccion.getDescripcion());
			result.setParadaX(direccion.getX());
			result.setParadaY(direccion.getY());

			int totalParadas = logsPasajeros.size();

			result.setTotalParadas(totalParadas);
			// TODO
			result.setParadaActual(0);

			return Optional.of(result);
		} else {
			return Optional.empty();
		}
	}

	public int getParadaActual(List<LogPasajeroDto> list) {
		int result = 0;
		return result;
	}

	@Override
	public Optional<DatosEstadoParadaDto> findEstadoParadaByUsuarioId(int usuarioId) {
		val optional = pasajeroService.findByUsuarioId(usuarioId);

		if (optional.isPresent()) {
			val pasajero = optional.get();

			val ruta = rutaService.findOneById(pasajero.getRutaId());
			val logRuta = logRutaService.findUltimoLogRutaByRutaId(pasajero.getRutaId());

			val result = new DatosEstadoParadaDto();
					
			if (logRuta.isPresent()) {
				val l = logRuta.get();

				if (rutaIsActiva(l)) {
					val direccion = direccionService.findOneById(pasajero.getDireccionId());
					val logPasajero = logPasajeroService.findUltimoLogPasajerosByRutaIdAndPasajeroId(ruta.getId(),
							pasajero.getRutaId());
					if (logPasajero.isPresent()) {
						result.setEstadoEstudianteId(logPasajero.get().getEstadoId());
						result.setEstadoEstudianteNombre(logPasajero.get().getEstadoNombre());
						result.setTipoEstadoEstudiante(logPasajero.get().getTipoEstado());
						result.setParadaX(direccion.getX());
						result.setParadaY(direccion.getY());
					}
					
					result.setEstadoRutaId(l.getEstadoId());
					result.setEstadoRutaNombre(l.getEstadoNombre());
					result.setTipoEstadoRuta(l.getTipoEstado());
					result.setRutaX(l.getX());
					result.setRutaY(l.getY());
				}
			}

			return Optional.of(result);
		} else {
			return Optional.empty();
		}
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