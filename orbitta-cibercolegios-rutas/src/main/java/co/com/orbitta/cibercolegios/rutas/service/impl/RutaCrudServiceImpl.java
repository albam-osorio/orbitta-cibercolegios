package co.com.orbitta.cibercolegios.rutas.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.rutas.domain.Direccion;
import co.com.orbitta.cibercolegios.rutas.domain.Pasajero;
import co.com.orbitta.cibercolegios.rutas.domain.Ruta;
import co.com.orbitta.cibercolegios.rutas.dto.RutaDto;
import co.com.orbitta.cibercolegios.rutas.dto.tracking.LogRutaDto;
import co.com.orbitta.cibercolegios.rutas.dto.tracking.monitor.DatosAcudienteDto;
import co.com.orbitta.cibercolegios.rutas.dto.tracking.monitor.DatosPasajeroDto;
import co.com.orbitta.cibercolegios.rutas.dto.tracking.monitor.MonitorDatosRutaDto;
import co.com.orbitta.cibercolegios.rutas.enums.TipoEstadoRutaEnum;
import co.com.orbitta.cibercolegios.rutas.repository.AcudienteRepository;
import co.com.orbitta.cibercolegios.rutas.repository.DireccionRepository;
import co.com.orbitta.cibercolegios.rutas.repository.EstadoRutaRepository;
import co.com.orbitta.cibercolegios.rutas.repository.PasajeroRepository;
import co.com.orbitta.cibercolegios.rutas.repository.RutaRepository;
import co.com.orbitta.cibercolegios.rutas.repository.readonly.CiudadRepository;
import co.com.orbitta.cibercolegios.rutas.repository.readonly.InstitucionRepository;
import co.com.orbitta.cibercolegios.rutas.repository.readonly.UsuarioRepository;
import co.com.orbitta.cibercolegios.rutas.service.api.RutaService;
import co.com.orbitta.cibercolegios.rutas.service.api.tracking.LogRutaService;
import co.com.orbitta.core.services.crud.impl.CrudServiceImpl;
import lombok.val;

@Service
public class RutaCrudServiceImpl extends CrudServiceImpl<Ruta, RutaDto, Integer> implements RutaService {

	@Autowired
	private RutaRepository repository;

	@Autowired
	private PasajeroRepository pasajeroRepository;

	@Autowired
	private DireccionRepository direccionRepository;

	@Autowired
	private EstadoRutaRepository estadoRutaRepository;

	@Autowired
	private InstitucionRepository institucionRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private CiudadRepository ciudadRepository;

	@Autowired
	private AcudienteRepository acudienteRepository;

	@Autowired
	private LogRutaService logRutaService;

	@Override
	protected RutaRepository getRepository() {
		return repository;
	}

	@Override
	public RutaDto asModel(Ruta entity) {

		val result = new RutaDto();

		result.setId(entity.getId());

		result.setInstitucionId(entity.getInstitucionId());
		result.setCodigo(entity.getCodigo());
		result.setDescripcion(entity.getDescripcion());
		result.setMarca(entity.getMarca());
		result.setPlaca(entity.getPlaca());
		result.setCapacidadMaxima(entity.getCapacidadMaxima());

		result.setMovil(entity.getMovil());
		result.setToken(entity.getToken());
		result.setConductorNombres(entity.getConductorNombres());

		result.setMonitorId(entity.getMonitorId());
		result.setDireccionSedeId(entity.getDireccionSede().getId());

		result.setFechaUltimoRecorrido(entity.getFechaUltimoRecorrido());
		result.setSentido(entity.getSentido());
		result.setEstadoId(entity.getEstado().getId());
		result.setTipoEstado(entity.getEstado().getTipo());
		result.setEstadoDescripcion(entity.getEstado().getDescripcion());
		result.setX(entity.getX());
		result.setY(entity.getY());

		result.setVersion(entity.getVersion());
		result.setFechaCreacion(entity.getFechaCreacion());
		result.setCreadoPor(entity.getCreadoPor());
		result.setFechaModificacion(entity.getFechaModificacion());
		result.setModificadoPor(entity.getModificadoPor());

		return result;
	}

	@Override
	protected Ruta mergeEntity(RutaDto model, Ruta entity) {

		val direccion = direccionRepository.findById(model.getDireccionSedeId());
		val estado = estadoRutaRepository.findById(model.getEstadoId());

		entity.setInstitucionId(model.getInstitucionId());
		entity.setCodigo(model.getCodigo());
		entity.setDescripcion(model.getDescripcion());
		entity.setMarca(model.getMarca());
		entity.setPlaca(model.getPlaca());
		entity.setCapacidadMaxima(model.getCapacidadMaxima());

		entity.setMovil(model.getMovil());
		entity.setToken(model.getToken());
		entity.setConductorNombres(model.getConductorNombres());

		entity.setMonitorId(model.getMonitorId());
		entity.setDireccionSede(direccion.get());

		entity.setFechaUltimoRecorrido(model.getFechaUltimoRecorrido());
		entity.setSentido(model.getSentido());
		entity.setEstado(estado.get());
		entity.setX(model.getX());
		entity.setY(model.getY());

		entity.setVersion(model.getVersion());
		entity.setFechaCreacion(model.getFechaCreacion());
		entity.setCreadoPor(model.getCreadoPor());
		entity.setFechaModificacion(model.getFechaModificacion());
		entity.setModificadoPor(model.getModificadoPor());

		return entity;
	}

	@Override
	protected Ruta newEntity() {
		return new Ruta();
	}

	@Override
	public List<MonitorDatosRutaDto> findAllByMonitorId(int monitorId) {
		val result = new ArrayList<MonitorDatosRutaDto>();

		val entities = getRepository().findAllByMonitorId(monitorId);

		for (val entity : entities) {
			result.add(asMonitorDatosRuta(entity));
		}

		return result;
	}

	@Override
	public Optional<MonitorDatosRutaDto> findByIdAsMonitorDatosRuta(int id) {
		val optional = getRepository().findById(id);

		if (optional.isPresent()) {
			val result = asMonitorDatosRuta(optional.get());
			return Optional.of(result);
		} else {
			return Optional.empty();
		}
	}

	private MonitorDatosRutaDto asMonitorDatosRuta(Ruta ruta) {
		val today = LocalDate.now();

		boolean activa = false;
		LocalDate fechaUltimoRecorrido = ruta.getFechaUltimoRecorrido();
		Integer sentido = null;
		Integer estadoId = null;
		TipoEstadoRutaEnum tipoEstado = null;
		String estadoNombre = null;
		BigDecimal x = null;
		BigDecimal y = null;
		LogRutaDto logRuta = null;

		if (fechaUltimoRecorrido != null) {
			if (fechaUltimoRecorrido.equals(today)) {
				activa = ruta.getEstado().getTipo().isActiva();
				sentido = ruta.getSentido();

				estadoId = ruta.getEstado().getId();
				tipoEstado = ruta.getEstado().getTipo();
				estadoNombre = ruta.getEstado().getDescripcion();
				
				x = ruta.getX();
				y = ruta.getY();
				
				val optional = logRutaService.findUltimoLogRutaByRutaIdAndFechaUltimoRecorrido(ruta.getId(),
						ruta.getFechaUltimoRecorrido());
				if (optional.isPresent()) {
					logRuta = optional.get();
				}
			}
		}

		val institucion = institucionRepository.findById(ruta.getInstitucionId()).get();
		val monitor = usuarioRepository.findById(ruta.getMonitorId()).get();
		val pasajeros = getPasajeros(ruta.getId(), activa, sentido);

		val result = new MonitorDatosRutaDto();

		result.setRutaId(ruta.getId());

		result.setInstitucionId(institucion.getId());
		result.setInstitucionNombre(institucion.getNombre());
		result.setInstitucionX(ruta.getDireccionSede().getX());
		result.setInstitucionY(ruta.getDireccionSede().getY());

		result.setCodigo(ruta.getCodigo());
		result.setDescripcion(ruta.getDescripcion());
		result.setMarca(ruta.getMarca());
		result.setPlaca(ruta.getPlaca());
		result.setCapacidad(ruta.getCapacidadMaxima());

		result.setMovil(ruta.getMovil());
		result.setToken(ruta.getToken());

		result.setConductorId(0);
		result.setConductorNombres(ruta.getConductorNombres());
		result.setConductorApellidos("");

		result.setMonitorId(monitor.getId());
		result.setMonitorNombres(monitor.getNombre());
		result.setMonitorApellidos(monitor.getApellido());

		result.setActiva(activa);
		result.setFechaUltimoRecorrido(fechaUltimoRecorrido);
		result.setUltimoSentido(sentido);
		result.setEstadoId(estadoId);
		result.setTipoEstado(tipoEstado);
		result.setEstadoNombre(estadoNombre);
		result.setX(x);
		result.setY(y);

		result.setLogRuta(logRuta);
		result.setPasajeros(pasajeros);

		return result;
	}

	private List<DatosPasajeroDto> getPasajeros(int rutaId, boolean activa, Integer sentido) {
		val list = new ArrayList<DatosPasajeroDto>();

		if (activa) {
			val pasajeros = pasajeroRepository.findAllByRutaId(rutaId);

			for (val pasajero : pasajeros) {
				val usuario = usuarioRepository.findById(pasajero.getUsuarioId()).get();
				val secuencia = getSecuencia(pasajero, sentido);
				val estado = pasajero.getEstado();
				val direccion = getDireccion(pasajero, sentido);
				val ciudadNombre = getCiudadNombre(direccion);

				val model = new DatosPasajeroDto();

				model.setUsuarioId(usuario.getId());
				model.setNombres(usuario.getNombre());
				model.setApellidos(usuario.getApellido());
				model.setSecuencia(secuencia);
				model.setEstadoId(estado.getId());
				model.setEstadoDescripcion(estado.getDescripcion());
				model.setTipoEstado(estado.getTipo());
				model.setCiudadNombre(ciudadNombre);
				model.setDireccion(direccion.getDireccion());
				model.setX(direccion.getX());
				model.setY(direccion.getY());

				model.setAcudientes(getAcudientes(pasajero));

				list.add(model);
			}
		}

		val result = list.stream().sorted((a, b) -> Integer.compare(a.getSecuencia(), b.getSecuencia()))
				.collect(Collectors.toList());
		return result;
	}

	private int getSecuencia(Pasajero pasajero, int sentido) {
		return (sentido == RutaDto.SENTIDO_IDA) ? pasajero.getSecuenciaIda() : pasajero.getSecuenciaRetorno();
	}

	private Direccion getDireccion(Pasajero pasajero, int sentido) {
		Direccion result;
		if (sentido == RutaDto.SENTIDO_IDA) {
			result = pasajero.getDireccionIda();
		} else {
			result = pasajero.getDireccionRetorno();
		}
		return result;
	}

	private String getCiudadNombre(Direccion direccion) {
		String result;

		val optional = ciudadRepository.findById(direccion.getCiudadId());
		if (optional.isPresent()) {
			result = optional.get().getNombre();
		} else {
			result = "NO DISPONIBLE";
		}
		return result;
	}

	private List<DatosAcudienteDto> getAcudientes(Pasajero pasajero) {
		val result = new ArrayList<DatosAcudienteDto>();

		val ids = pasajero.getAcudientes();
		if (!ids.isEmpty()) {
			val acudientes = acudienteRepository.findAllById(ids);
			for (val acudiente : acudientes) {
				if (acudiente.getToken() != null) {
					val optional = usuarioRepository.findById(acudiente.getUsuarioId());
					if (optional.isPresent()) {
						val model = new DatosAcudienteDto();
						model.setUsuarioId(acudiente.getUsuarioId());
						model.setNombres(optional.get().getNombre());
						model.setApellidos(optional.get().getApellido());
						model.setToken(acudiente.getToken());
						result.add(model);
					}
				}
			}
		}

		return result;
	}
}