package com.tbf.cibercolegios.api.routes.services.impl;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tbf.cibercolegios.api.core.services.impl.CrudServiceImpl;
import com.tbf.cibercolegios.api.model.routes.Acudiente;
import com.tbf.cibercolegios.api.model.routes.Direccion;
import com.tbf.cibercolegios.api.model.routes.Pasajero;
import com.tbf.cibercolegios.api.model.routes.PasajeroAcudiente;
import com.tbf.cibercolegios.api.model.routes.PasajeroDireccion;
import com.tbf.cibercolegios.api.model.routes.enums.CourseType;
import com.tbf.cibercolegios.api.routes.model.graph.DireccionDto;
import com.tbf.cibercolegios.api.routes.model.graph.PasajeroDto;
import com.tbf.cibercolegios.api.routes.repository.AcudienteRepository;
import com.tbf.cibercolegios.api.routes.repository.DireccionRepository;
import com.tbf.cibercolegios.api.routes.repository.PasajeroAcudienteRepository;
import com.tbf.cibercolegios.api.routes.repository.PasajeroDireccionRepository;
import com.tbf.cibercolegios.api.routes.repository.PasajeroRepository;
import com.tbf.cibercolegios.api.routes.services.api.PasajeroService;

import lombok.val;

@Service
public class PasajeroCrudServiceImpl extends CrudServiceImpl<Pasajero, PasajeroDto, Integer>
		implements PasajeroService {

	@Autowired
	private PasajeroRepository repository;

	@Autowired
	private PasajeroDireccionRepository pasajeroDireccionRepository;

	@Autowired
	private PasajeroAcudienteRepository pasajeroAcudienteRepository;

	@Autowired
	private DireccionRepository direccionRepository;

	@Autowired
	private AcudienteRepository acudienteRepository;

	@Override
	protected PasajeroRepository getRepository() {
		return repository;
	}

	@Override
	public PasajeroDto asModel(Pasajero entity) {
		val model = newModel();
		mapModel(entity, model);

		model.setInstitucionId(entity.getInstitucionId());
		model.setUsuarioId(entity.getUsuarioId());

		model.setFechaUltimoEvento(entity.getFechaUltimoEvento());
		model.setRutaId(entity.getRutaId());
		model.setSecuencia(entity.getSecuencia());
		model.setSentido(entity.getSentido());
		model.setDireccionId(entity.getDireccionId());
		model.setEstadoId(entity.getEstadoId());
		model.setX(entity.getX());
		model.setY(entity.getY());

		return model;
	}

	@Override
	protected PasajeroDto newModel() {
		return new PasajeroDto();
	}

	@Override
	protected Pasajero mergeEntity(PasajeroDto model, Pasajero entity) {
		entity.setInstitucionId(model.getInstitucionId());
		entity.setUsuarioId(model.getUsuarioId());

		entity.setFechaUltimoEvento(model.getFechaUltimoEvento());
		entity.setRutaId(model.getRutaId());
		entity.setSecuencia(model.getSecuencia());
		entity.setSentido(model.getSentido());
		entity.setDireccionId(model.getDireccionId());
		entity.setEstadoId(model.getEstadoId());
		entity.setX(model.getX());
		entity.setY(model.getY());

		entity.setVersion(model.getVersion());
		return entity;
	}

	@Override
	protected Pasajero newEntity() {
		return new Pasajero();
	}

	// -----------------------------------------------------------------------------------
	// --
	// -----------------------------------------------------------------------------------
	@Override
	public Optional<PasajeroDto> findByInstitucionIdAndUsuarioId(int insititucionId, int usuarioId) {
		val optional = getRepository().findByInstitucionIdAndUsuarioId(insititucionId, usuarioId);
		return asModel(optional);
	}

	@Override
	public List<PasajeroDto> findAllByRutaId(int rutaId) {
		val list = this.pasajeroDireccionRepository.findAllByRutaId(rutaId);
		val ids = list.stream().map(PasajeroDireccion::getPasajeroId).distinct().collect(toList());
		return findAllById(ids);
	}

	@Override
	public List<PasajeroDireccion> findAllPasajeroDireccionByRutaId(int rutaId) {
		return this.pasajeroDireccionRepository.findAllByRutaId(rutaId);
	}

	@Override
	public List<PasajeroDireccion> findAllPasajeroDireccionByPasajeroId(int pasajeroId) {
		return this.pasajeroDireccionRepository.findAllByPasajeroId(pasajeroId);
	}

	@Override
	public List<PasajeroDireccion> findAllPasajeroDireccionByPasajeroIdIn(List<Integer> pasajerosId) {
		return this.pasajeroDireccionRepository.findAllByPasajeroIdIn(pasajerosId);
	}

	@Override
	public Map<Integer, List<Acudiente>> findAllAcudientesIdByPasajeroIdIn(List<Integer> pasajerosId) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	// -----------------------------------------------------------------------------------
	// --
	// -----------------------------------------------------------------------------------
	@Override
	public void create(PasajeroDto model, Optional<DireccionDto> direccionIda, Optional<DireccionDto> direccionRetorno,
			List<Integer> usuariosAcudientesId, boolean activo) {
		val result = create(model);

		int correlacion = 0;

		if (direccionIda.isPresent()) {
			createDireccion(result.getId(), correlacion, CourseType.SENTIDO_IDA.getIntValue(), direccionIda.get(),
					activo);
		}

		if (direccionRetorno.isPresent()) {
			createDireccion(result.getId(), correlacion, CourseType.SENTIDO_RETORNO.getIntValue(),
					direccionRetorno.get(), activo);
		}

		val acudientesId = crearAcudientes(result.getId(), usuariosAcudientesId);
		asociarAcudientes(result.getId(), acudientesId);
	}

	@Override
	public void update(PasajeroDto pasajero, Optional<DireccionDto> direccionIda,
			Optional<DireccionDto> direccionRetorno, List<Integer> usuariosAcudientesId, boolean activo) {
		int correlacion = 0;
		val list = findAllPasajeroDireccionByPasajeroId(pasajero.getId());
		val max = list.stream().map(PasajeroDireccion::getCorrelacion).max(Integer::compareTo);
		activo &= !list.stream().anyMatch(a -> a.isActivo());

		if (max.isPresent()) {
			correlacion = max.get() + 1;
		}

		if (direccionIda.isPresent()) {
			createDireccion(pasajero.getId(), correlacion, CourseType.SENTIDO_IDA.getIntValue(), direccionIda.get(),
					activo);
		}

		if (direccionRetorno.isPresent()) {
			createDireccion(pasajero.getId(), correlacion, CourseType.SENTIDO_RETORNO.getIntValue(),
					direccionRetorno.get(), activo);
		}

		val acudientesId = crearAcudientes(pasajero.getId(), usuariosAcudientesId);
		asociarAcudientes(pasajero.getId(), acudientesId);
	}

	private void createDireccion(int pasajeroId, int correlacion, int sentido, DireccionDto direccion, boolean activo) {
		val direccionId = direccionRepository.save(asDireccion(direccion)).getId();

		val entity = new PasajeroDireccion();
		entity.setPasajeroId(pasajeroId);
		entity.setCorrelacion(correlacion);
		entity.setSentido(sentido);
		entity.setDireccionId(direccionId);
		entity.setActivo(activo);

		pasajeroDireccionRepository.save(entity);
	}

	private Direccion asDireccion(DireccionDto direccion) {
		val entity = new Direccion();

		entity.setInstitucionId(direccion.getInstitucionId());
		entity.setEstadoId(direccion.getEstadoId());
		entity.setPaisId(direccion.getPaisId());
		entity.setDepartamentoId(direccion.getDepartamentoId());
		entity.setCiudadId(direccion.getCiudadId());
		entity.setDireccion(direccion.getDireccion());
		entity.setX(null);
		entity.setY(null);

		return entity;
	}

	private List<Integer> crearAcudientes(int pasajeroId, List<Integer> usuariosId) {
		// Se buscan los acudientes existentes por su id de usuario
		val acudientes = acudienteRepository.findAllByUsuarioIdIn(usuariosId);

		val insert = new ArrayList<Acudiente>();
		for (val usuarioId : usuariosId) {
			val optional = acudientes.stream().filter(a -> a.getUsuarioId() == usuarioId).findFirst();

			// Por cada id de usuario de acudiente que aun no exista en la tabla de
			// acudientes
			if (!optional.isPresent()) {
				val entity = new Acudiente();
				entity.setUsuarioId(usuarioId);
				insert.add(entity);
			}
		}

		if (!insert.isEmpty()) {
			acudientes.addAll(acudienteRepository.saveAll(insert));
		}

		return acudientes.stream().map(a -> a.getId()).collect(toList());
	}

	private void asociarAcudientes(int pasajeroId, List<Integer> acudientesId) {
		val existentes = pasajeroAcudienteRepository.findAllByPasajeroId(pasajeroId);

		val existentesId = existentes.stream().map(PasajeroAcudiente::getAcudienteId).distinct().collect(toList());

		// Buscar los acudientesId que no estan en existentes --> INSERT
		List<PasajeroAcudiente> insert = acudientesId.stream().filter(i -> !existentesId.contains(i))
				.map(acudienteId -> new PasajeroAcudiente(pasajeroId, acudienteId)).collect(toList());
		pasajeroAcudienteRepository.saveAll(insert);

		val delete = existentes.stream().filter(i -> !acudientesId.contains(i.getAcudienteId())).collect(toList());
		pasajeroAcudienteRepository.deleteAll(delete);
	}
}