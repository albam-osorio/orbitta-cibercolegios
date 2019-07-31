package com.tbf.cibercolegios.api.routes.services.impl;

import static java.util.stream.Collectors.toList;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.tbf.cibercolegios.api.ciber.model.graph.CiudadDto;
import com.tbf.cibercolegios.api.ciber.model.graph.UsuarioDto;
import com.tbf.cibercolegios.api.ciber.services.api.CiberService;
import com.tbf.cibercolegios.api.core.services.impl.CrudServiceImpl;
import com.tbf.cibercolegios.api.model.routes.Acudiente;
import com.tbf.cibercolegios.api.model.routes.Direccion;
import com.tbf.cibercolegios.api.model.routes.EstadoPasajero;
import com.tbf.cibercolegios.api.model.routes.EstadoRuta;
import com.tbf.cibercolegios.api.model.routes.Pasajero;
import com.tbf.cibercolegios.api.model.routes.PasajeroDireccion;
import com.tbf.cibercolegios.api.model.routes.Ruta;
import com.tbf.cibercolegios.api.model.routes.enums.CourseType;
import com.tbf.cibercolegios.api.model.routes.enums.PassengerTypeStatus;
import com.tbf.cibercolegios.api.model.routes.enums.RouteTypeStatus;
import com.tbf.cibercolegios.api.routes.model.graph.RutaDto;
import com.tbf.cibercolegios.api.routes.model.graph.tracking.DatosAcudienteDto;
import com.tbf.cibercolegios.api.routes.model.graph.tracking.DatosPasajeroDto;
import com.tbf.cibercolegios.api.routes.model.graph.tracking.MonitorDatosRutaDto;
import com.tbf.cibercolegios.api.routes.model.graph.web.RutaConCapacidadDto;
import com.tbf.cibercolegios.api.routes.repository.DireccionRepository;
import com.tbf.cibercolegios.api.routes.repository.EstadoPasajeroRepository;
import com.tbf.cibercolegios.api.routes.repository.EstadoRutaRepository;
import com.tbf.cibercolegios.api.routes.repository.PasajeroDireccionRepository;
import com.tbf.cibercolegios.api.routes.repository.PasajeroRepository;
import com.tbf.cibercolegios.api.routes.repository.RutaRepository;
import com.tbf.cibercolegios.api.routes.services.api.PasajeroService;
import com.tbf.cibercolegios.api.routes.services.api.RutaService;

import lombok.val;

@Service
public class RutaCrudServiceImpl extends CrudServiceImpl<Ruta, RutaDto, Integer> implements RutaService {

	@Autowired
	private RutaRepository repository;

	@Autowired
	private CiberService ciberService;

	@Autowired
	private PasajeroService pasajeroService;

	@Autowired
	private EstadoRutaRepository estadoRutaRepository;

	@Autowired
	private EstadoPasajeroRepository estadoPasajeroRepository;

	@Autowired
	private PasajeroDireccionRepository pasajeroDireccionRepository;

	@Autowired
	private PasajeroRepository pasajeroRepository;

	@Autowired
	private DireccionRepository direccionRepository;

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	protected RutaRepository getRepository() {
		return repository;
	}

	@Override
	public RutaDto asModel(Ruta entity) {
		val model = newModel();
		mapModel(entity, model);

		model.setCodigo(entity.getCodigo());
		model.setDescripcion(entity.getDescripcion());
		model.setMarca(entity.getMarca());
		model.setPlaca(entity.getPlaca());
		model.setCapacidadMaxima(entity.getCapacidadMaxima());

		model.setInstitucionId(entity.getInstitucionId());
		model.setDireccionSedeId(entity.getDireccionSedeId());

		model.setConductorNombres(entity.getConductorNombres());
		model.setMonitorId(entity.getMonitorId());
		model.setMovil(entity.getMovil());
		model.setToken(entity.getToken());

		model.setFechaUltimoEvento(entity.getFechaUltimoEvento());
		model.setSentido(entity.getSentido());
		model.setEstadoId(entity.getEstadoId());
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

		entity.setCodigo(model.getCodigo());
		entity.setDescripcion(model.getDescripcion());
		entity.setMarca(model.getMarca());
		entity.setPlaca(model.getPlaca());
		entity.setCapacidadMaxima(model.getCapacidadMaxima());

		entity.setInstitucionId(model.getInstitucionId());
		entity.setDireccionSedeId(model.getDireccionSedeId());

		entity.setConductorNombres(model.getConductorNombres());
		entity.setMonitorId(model.getMonitorId());
		entity.setMovil(model.getMovil());
		entity.setToken(model.getToken());

		entity.setFechaUltimoEvento(model.getFechaUltimoEvento());
		entity.setSentido(model.getSentido());
		entity.setEstadoId(model.getEstadoId());
		entity.setX(model.getX());
		entity.setY(model.getY());

		return entity;
	}

	@Override
	protected Ruta newEntity() {
		return new Ruta();
	}

	// -----------------------------------------------------------------------------------
	// --
	// -----------------------------------------------------------------------------------
	@Override
	public List<RutaDto> findAllByInstitucionId(int institucionId) {
		val entities = getRepository().findAllByInstitucionId(institucionId);
		return asModels(entities);
	}

	@Override
	public List<RutaDto> findAllByInstitucionIdAndMonitorId(int institucionId, int monitorId) {
		val entities = getRepository().findAllByInstitucionIdAndMonitorId(institucionId, monitorId);
		return asModels(entities);
	}

	@Override
	public Optional<RutaConCapacidadDto> findRutasConCapacidadById(int rutaId) {
		// @formatter:off
		String sql = "" +
				"SELECT a.ID_RUTA,\r\n" + 
				"       a.DESCRIPCION,\r\n" + 
				"       COUNT(DISTINCT CASE WHEN b.SENTIDO = 1 THEN b.ID_PASAJERO ELSE NULL END) AS OCUPACION_AM,\r\n" + 
				"       COUNT(DISTINCT CASE WHEN b.SENTIDO = 2 THEN b.ID_PASAJERO ELSE NULL END) AS OCUPACION_PM,\r\n" + 
				"       a.CAPACIDAD_MAXIMA\r\n" + 
				"  FROM RUTAS a\r\n" + 
				"  LEFT OUTER JOIN PASAJEROS_DIRECCIONES b\r\n" + 
				"    ON b.ID_RUTA = a.ID_RUTA\r\n" + 
				"   AND b.ACTIVO = 1\r\n" + 
				" WHERE 1 = 1 \r\n" + 
				" AND a.ID_RUTA = :ID_RUTA\r\n" + 
				" GROUP BY a.ID_RUTA, a.DESCRIPCION, a.CAPACIDAD_MAXIMA\r\n" + 
				" ORDER BY a.DESCRIPCION\r\n" + 
				"";
		
		// @formatter:on

		val paramMap = new HashMap<String, Object>();
		paramMap.put("ID_RUTA", rutaId);

		return jdbcTemplate.query(sql, paramMap, rutaConCapacidadRowMapper()).stream().findFirst();
	}

	@Override
	public List<RutaConCapacidadDto> findAllRutasConCapacidadByInstitucionId(int institucionId, Integer pasajeroId) {
		// @formatter:off
		String sql = "" +
				"SELECT a.ID_RUTA,\r\n" + 
				"       a.DESCRIPCION,\r\n" + 
				"       COUNT(DISTINCT CASE WHEN b.SENTIDO = 1 AND (:ID_PASAJERO IS NULL OR b.ID_PASAJERO <> :ID_PASAJERO) THEN b.ID_PASAJERO ELSE NULL END) AS OCUPACION_AM,\r\n" + 
				"       COUNT(DISTINCT CASE WHEN b.SENTIDO = 2 AND (:ID_PASAJERO IS NULL OR b.ID_PASAJERO <> :ID_PASAJERO) THEN b.ID_PASAJERO ELSE NULL END) AS OCUPACION_PM,\r\n" + 
				"       a.CAPACIDAD_MAXIMA\r\n" + 
				"  FROM RUTAS a\r\n" + 
				"  LEFT OUTER JOIN PASAJEROS_DIRECCIONES b\r\n" + 
				"    ON b.ID_RUTA = a.ID_RUTA\r\n" + 
				"   AND b.ACTIVO = 1\r\n" + 
				" WHERE 1 = 1 \r\n" + 
				" AND a.ID_INSTITUCION = :ID_INSTITUCION\r\n" + 
				" GROUP BY a.ID_RUTA, a.DESCRIPCION, a.CAPACIDAD_MAXIMA\r\n" + 
				" ORDER BY a.DESCRIPCION\r\n" + 
				"";
		
		// @formatter:on

		val paramMap = new HashMap<String, Object>();
		paramMap.put("ID_INSTITUCION", institucionId);
		paramMap.put("ID_PASAJERO", pasajeroId);

		return jdbcTemplate.query(sql, paramMap, rutaConCapacidadRowMapper());
	}

	private RowMapper<RutaConCapacidadDto> rutaConCapacidadRowMapper() {
		return (rs, rowNum) -> {
			RutaConCapacidadDto r = new RutaConCapacidadDto();

			r.setId(rs.getInt("ID_RUTA"));
			r.setDescripcion(rs.getString("DESCRIPCION"));
			r.setOcupacionAm(rs.getInt("OCUPACION_AM"));
			r.setOcupacionPm(rs.getInt("OCUPACION_PM"));
			r.setCapacidadMaxima(rs.getInt("CAPACIDAD_MAXIMA"));

			return r;
		};
	}

	// -----------------------------------------------------------------------------------
	// --
	// -----------------------------------------------------------------------------------
	@Override
	public Optional<MonitorDatosRutaDto> findByIdAsMonitorDatosRuta(int id) {
		val optional = getRepository().findById(id);

		if (optional.isPresent()) {
			val list = new ArrayList<Ruta>();
			list.add(optional.get());
			return asMonitorDatosRuta(list).stream().findFirst();
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<MonitorDatosRutaDto> findAllByInstitucionIdAsMonitorDatosRuta(int institucionId) {
		val entities = getRepository().findAllByInstitucionId(institucionId);

		return asMonitorDatosRuta(entities);
	}

	@Override
	public List<MonitorDatosRutaDto> findAllByInstitucionIdAndMonitorIdAsMonitorDatosRuta(int institucionId,
			int monitorId) {
		val entities = getRepository().findAllByInstitucionIdAndMonitorId(institucionId, monitorId);

		return asMonitorDatosRuta(entities);
	}

	// -----------------------------------------------------------------------------------
	// --
	// -----------------------------------------------------------------------------------
	private List<MonitorDatosRutaDto> asMonitorDatosRuta(List<Ruta> rutas) {
		List<MonitorDatosRutaDto> result = new ArrayList<>();

		val today = LocalDate.now();
		val usuariosId = new ArrayList<Integer>();
		val direccionesId = new ArrayList<Integer>();

		val estadosRutas = estadoRutaRepository.findAll();
		val estadoRutaInactivo = estadosRutas.stream()
				.filter(a -> a.getTipo().equals(RouteTypeStatus.INACTIVO) && a.isPredeterminado()).findFirst().get();

		val estadosPasajeros = estadoPasajeroRepository.findAll();

		val institucionId = rutas.stream().map(Ruta::getInstitucionId).findFirst().get();
		val institucion = ciberService.findInstitucionById(institucionId).get();

		val rutasId = rutas.stream().map(Ruta::getId).distinct().collect(toList());
		val pasajerosDirecciones = pasajeroDireccionRepository.findAllByRutaIdInAndActivoTrue(rutasId);

		val pasajerosId = pasajerosDirecciones.stream().map(PasajeroDireccion::getPasajeroId).collect(toList());
		val pasajeros = pasajeroRepository.findAllById(pasajerosId);

		val acudientes = pasajeroService.findAllAcudientesIdByPasajeroIdIn(pasajerosId);

		usuariosId.addAll(rutas.stream().map(Ruta::getMonitorId).distinct().collect(toList()));
		usuariosId.addAll(pasajeros.stream().map(Pasajero::getUsuarioId).distinct().collect(toList()));
		usuariosId.addAll(acudientes.values().stream().flatMap(a -> a.stream()).map(Acudiente::getUsuarioId).distinct()
				.collect(toList()));

		direccionesId.addAll(rutas.stream().map(Ruta::getDireccionSedeId).distinct().collect(toList()));
		direccionesId.addAll(
				pasajerosDirecciones.stream().map(PasajeroDireccion::getDireccionId).distinct().collect(toList()));

		val usuarios = ciberService.findAllUsuariosByUsuarioIdIn(usuariosId);
		val direcciones = direccionRepository.findAllById(direccionesId.stream().distinct().collect(toList()));
		val ciudades = getCiudades(direcciones);

		rutas.forEach(ruta -> {
			LocalDate fechaUltimoRecorrido = ruta.getFechaUltimoEvento().toLocalDate();
			Integer sentido = CourseType.SENTIDO_IDA.getIntValue();
			EstadoRuta estadoRuta = estadoRutaInactivo;
			BigDecimal x = null;
			BigDecimal y = null;
			boolean rutaActiva = false;

			if (fechaUltimoRecorrido != null) {
				if (fechaUltimoRecorrido.equals(today)) {
					val optional = estadosRutas.stream().filter(a -> a.getId().equals(ruta.getEstadoId())).findFirst();

					if (optional.isPresent()) {
						sentido = ruta.getSentido();
						estadoRuta = optional.get();
						x = ruta.getX();
						y = ruta.getY();
						rutaActiva = estadoRuta.getTipo().isActiva();
					}
				}
			}

			Direccion direccionSede = direcciones.stream().filter(a -> a.getId().equals(ruta.getDireccionSedeId()))
					.findFirst().get();
			UsuarioDto monitor = usuarios.stream().filter(a -> a.getId().equals(ruta.getMonitorId())).findFirst().get();

			MonitorDatosRutaDto model = new MonitorDatosRutaDto();

			model.setRutaId(ruta.getId());

			model.setInstitucionId(institucion.getId());
			model.setInstitucionNombre(institucion.getNombre());
			model.setInstitucionX(direccionSede.getX());
			model.setInstitucionY(direccionSede.getY());

			model.setCodigo(ruta.getCodigo());
			model.setDescripcion(ruta.getDescripcion());
			model.setMarca(ruta.getMarca());
			model.setPlaca(ruta.getPlaca());
			model.setCapacidad(ruta.getCapacidadMaxima());

			model.setMovil(ruta.getMovil());
			model.setToken(ruta.getToken());

			model.setConductorId(0);
			model.setConductorNombres(ruta.getConductorNombres());
			model.setConductorApellidos("");

			model.setMonitorId(monitor.getId());
			model.setMonitorNombres(monitor.getNombre());
			model.setMonitorApellidos(monitor.getApellido());

			model.setActiva(rutaActiva);
			model.setFechaUltimoRecorrido(fechaUltimoRecorrido);
			model.setUltimoSentido(sentido);
			model.setEstadoId(estadoRuta.getId());
			model.setTipoEstado(estadoRuta.getTipo());
			model.setEstadoNombre(estadoRuta.getDescripcion());
			model.setX(x);
			model.setY(y);

			val s = sentido;
			val pd = pasajerosDirecciones.stream()
					.filter(a -> a.getRutaId() == ruta.getId() && a.getSentido() == s && a.isActivo())
					.collect(toList());

			model.setPasajeros(getPasajeros(ruta.getId(), sentido, rutaActiva, pasajeros, pd, direcciones, ciudades,
					estadosPasajeros, acudientes, usuarios));

			result.add(model);
		});

		return result;
	}

	private List<DatosPasajeroDto> getPasajeros(int rutaId, int sentido, boolean rutaActiva, List<Pasajero> pasajeros,
			List<PasajeroDireccion> pasajerosDirecciones, List<Direccion> direcciones, List<CiudadDto> ciudades,
			List<EstadoPasajero> estadosPasajeros, Map<Integer, List<Acudiente>> acudientes,
			List<UsuarioDto> usuarios) {

		val list = new ArrayList<DatosPasajeroDto>();

		val estadoPasajeroInactivo = estadosPasajeros.stream()
				.filter(a -> a.getTipo().equals(PassengerTypeStatus.INACTIVO)).findFirst().get();

		for (val pd : pasajerosDirecciones) {

			val pasajero = pasajeros.stream().filter(a -> a.getId() == pd.getPasajeroId()).findFirst().get();
			val usuario = usuarios.stream().filter(a -> a.getId().equals(pasajero.getUsuarioId())).findFirst().get();
			val direccion = direcciones.stream().filter(a -> a.getId().equals(pasajero.getDireccionId())).findFirst()
					.get();
			val ciudad = ciudades.stream()
					.filter(a -> a.getPaisId() == direccion.getPaisId()
							&& a.getDepartamentoId() == direccion.getDepartamentoId()
							&& a.getCiudadId() == direccion.getCiudadId())
					.findFirst().get();

			val secuencia = pd.getSecuencia();

			EstadoPasajero estadoPasajero = estadoPasajeroInactivo;
			if (rutaActiva) {
				// if (pasajero.getEstadoId() != null) {
				// estadoPasajero = estadosPasajeros.stream().filter(a -> a.getId() ==
				// pasajero.getEstadoId())
				// .findFirst().get();
				// }
			}

			val model = new DatosPasajeroDto();

			model.setUsuarioId(usuario.getId());
			model.setNombres(usuario.getNombre());
			model.setApellidos(usuario.getApellido());
			model.setSecuencia(secuencia);
			model.setEstadoId(estadoPasajero.getId());
			model.setEstadoDescripcion(estadoPasajero.getDescripcion());
			model.setTipoEstado(estadoPasajero.getTipo());
			model.setCiudadNombre(ciudad.getNombre());
			model.setDireccion(direccion.getDireccion());
			model.setX(direccion.getX());
			model.setY(direccion.getY());

			model.setAcudientes(getAcudientes(acudientes.get(pasajero.getId()), usuarios));

			list.add(model);
		}

		val result = list.stream().sorted((a, b) -> Integer.compare(a.getSecuencia(), b.getSecuencia()))
				.collect(Collectors.toList());
		return result;
	}

	private List<CiudadDto> getCiudades(List<Direccion> direcciones) {
		val result = new ArrayList<CiudadDto>();

		val paisesId = direcciones.stream().map(Direccion::getPaisId).distinct().collect(toList());

		paisesId.forEach(paisId -> {
			val departamentosId = direcciones.stream().filter(a -> a.getPaisId() == paisId)
					.map(Direccion::getDepartamentoId).distinct().collect(toList());

			departamentosId.forEach(departamentoId -> {
				val ciudadesId = direcciones.stream()
						.filter(a -> a.getPaisId() == paisId && a.getDepartamentoId() == departamentoId)
						.map(Direccion::getCiudadId).distinct().collect(toList());

				ciudadesId.forEach(ciudadId -> {
					result.add(ciberService.findCiudadByCiudadId(paisId, departamentoId, ciudadId).get());
				});
			});
		});

		return result;
	}

	private List<DatosAcudienteDto> getAcudientes(List<Acudiente> acudientes, List<UsuarioDto> usuarios) {
		val result = new ArrayList<DatosAcudienteDto>();

		for (val acudiente : acudientes) {
			if (acudiente.getToken() != null) {
				val optional = usuarios.stream().filter(a -> a.getId() == acudiente.getUsuarioId()).findFirst();

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

		return result;
	}
}