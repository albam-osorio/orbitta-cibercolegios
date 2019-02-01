package com.tbf.cibercolegios.api.routes.services.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tbf.cibercolegios.api.ciber.services.api.CiberService;
import com.tbf.cibercolegios.api.core.services.impl.CrudServiceImpl;
import com.tbf.cibercolegios.api.model.routes.Direccion;
import com.tbf.cibercolegios.api.model.routes.Pasajero;
import com.tbf.cibercolegios.api.model.routes.Ruta;
import com.tbf.cibercolegios.api.model.routes.enums.RouteTypeStatus;
import com.tbf.cibercolegios.api.routes.model.graph.LogRutaDto;
import com.tbf.cibercolegios.api.routes.model.graph.RutaDto;
import com.tbf.cibercolegios.api.routes.model.graph.tracking.DatosAcudienteDto;
import com.tbf.cibercolegios.api.routes.model.graph.tracking.DatosPasajeroDto;
import com.tbf.cibercolegios.api.routes.model.graph.tracking.MonitorDatosRutaDto;
import com.tbf.cibercolegios.api.routes.repository.AcudienteRepository;
import com.tbf.cibercolegios.api.routes.repository.DireccionRepository;
import com.tbf.cibercolegios.api.routes.repository.EstadoRutaRepository;
import com.tbf.cibercolegios.api.routes.repository.PasajeroRepository;
import com.tbf.cibercolegios.api.routes.repository.RutaRepository;
import com.tbf.cibercolegios.api.routes.services.api.LogRutaService;
import com.tbf.cibercolegios.api.routes.services.api.RutaService;

import lombok.val;

@Service
public class RutaCrudServiceImpl extends CrudServiceImpl<Ruta, RutaDto, Integer> implements RutaService {

	@Autowired
	private CiberService ciberService;

	@Autowired
	private RutaRepository repository;

	@Autowired
	private PasajeroRepository pasajeroRepository;

	@Autowired
	private DireccionRepository direccionRepository;

	@Autowired
	private EstadoRutaRepository estadoRutaRepository;

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
		val model = newModel();
		mapModel(entity, model);

		model.setInstitucionId(entity.getInstitucionId());
		model.setCodigo(entity.getCodigo());
		model.setDescripcion(entity.getDescripcion());
		model.setMarca(entity.getMarca());
		model.setPlaca(entity.getPlaca());
		model.setCapacidadMaxima(entity.getCapacidadMaxima());

		model.setMovil(entity.getMovil());
		model.setToken(entity.getToken());
		model.setConductorNombres(entity.getConductorNombres());

		model.setMonitorId(entity.getMonitorId());
		model.setDireccionSedeId(entity.getDireccionSede().getId());

		model.setFechaUltimoRecorrido(entity.getFechaUltimoRecorrido());
		model.setSentido(entity.getSentido());
		model.setEstadoId(entity.getEstado().getId());
		model.setTipoEstado(entity.getEstado().getTipo());
		model.setEstadoDescripcion(entity.getEstado().getDescripcion());
		model.setX(entity.getX());
		model.setY(entity.getY());

		return model;
	}

	@Override
	protected RutaDto newModel() {
		return new RutaDto();
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
	public List<RutaDto> findAllByMonitorId(int monitorId) {
		val result = new ArrayList<RutaDto>();

		val entities = getRepository().findAllByMonitorId(monitorId);

		for (val entity : entities) {
			result.add(asModel(entity));
		}

		return result;
	}

	
	@Override
	public List<RutaDto> findAllByInstitucionId(int institucionId) {
		val result = new ArrayList<RutaDto>();

		val entities = getRepository().findAllByInstitucionId(institucionId);

		for (val entity : entities) {
			result.add(asModel(entity));
		}

		return result;
	}

	@Override
	public List<MonitorDatosRutaDto> findAllByMonitorIdAsMonitorDatosRuta(int monitorId) {
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
		Integer sentido = RutaDto.SENTIDO_IDA;

		val estado = estadoRutaRepository.findFirstByTipoAndPredeterminado(RouteTypeStatus.INACTIVO, true);
		Integer estadoId = estado.get().getId();
		RouteTypeStatus tipoEstado = estado.get().getTipo();
		String estadoNombre = estado.get().getDescripcion();
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

		val institucion = ciberService.findInstitucionById(ruta.getInstitucionId()).get();
		val monitor = ciberService.findUsuarioById(ruta.getMonitorId()).get();
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
		activa = true;
		if (activa) {
			val pasajeros = pasajeroRepository.findAllByRutaId(rutaId);

			for (val pasajero : pasajeros) {
				val usuario = ciberService.findUsuarioById(pasajero.getUsuarioId()).get();
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

		val optional = ciberService.findCiudadByPk(direccion.getPaisId(), direccion.getDepartamentoId(),
				direccion.getCiudadId());
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
					val optional = ciberService.findUsuarioById(acudiente.getUsuarioId());
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

	@Override
	public List<MonitorDatosRutaDto> findAllByInstitucionIdAsMonitorDatosRuta(int institucionId) {
		val result = new ArrayList<MonitorDatosRutaDto>();

		val entities = getRepository().findAllByInstitucionId(institucionId);

		for (val entity : entities) {
			result.add(asMonitorDatosRuta(entity));
		}

		return result;
	}
}