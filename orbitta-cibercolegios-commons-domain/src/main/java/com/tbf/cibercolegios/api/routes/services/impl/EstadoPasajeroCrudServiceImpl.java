package com.tbf.cibercolegios.api.routes.services.impl;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tbf.cibercolegios.api.model.routes.EstadoPasajero;
import com.tbf.cibercolegios.api.routes.model.enums.PassengerTypeStatus;
import com.tbf.cibercolegios.api.routes.model.graph.EstadoPasajeroDto;
import com.tbf.cibercolegios.api.routes.repository.EstadoPasajeroRepository;
import com.tbf.cibercolegios.api.routes.services.api.EstadoPasajeroService;

import co.com.orbitta.core.services.crud.impl.CrudServiceImpl;
import lombok.val;

@Service
public class EstadoPasajeroCrudServiceImpl extends CrudServiceImpl<EstadoPasajero, EstadoPasajeroDto, Integer>
		implements EstadoPasajeroService {

	@Autowired
	private EstadoPasajeroRepository repository;

	@Override
	protected EstadoPasajeroRepository getRepository() {
		return repository;
	}

	@Override
	public EstadoPasajeroDto asModel(EstadoPasajero entity) {
		val model = newModel();
		mapModel(entity, model);

		model.setDescripcion(entity.getDescripcion());
		model.setTipo(entity.getTipo());
		model.setAplicaSentidoIda(entity.isAplicaSentidoIda());
		model.setAplicaSentidoRetorno(entity.isAplicaSentidoRetorno());

		return model;
	}

	@Override
	protected EstadoPasajero mergeEntity(EstadoPasajeroDto model, EstadoPasajero entity) {

		entity.setDescripcion(model.getDescripcion());
		entity.setTipo(model.getTipo());
		entity.setAplicaSentidoIda(model.isAplicaSentidoIda());
		entity.setAplicaSentidoRetorno(model.isAplicaSentidoRetorno());

		entity.setVersion(model.getVersion());
		return entity;
	}

	@Override
	protected EstadoPasajero newEntity() {
		return new EstadoPasajero();
	}

	@Override
	public List<EstadoPasajeroDto> findAll() {
		val entities = getRepository().findAll();

		val result = asModels(entities);
		return result;
	}

	@Override
	public EstadoPasajeroDto findEstadoInicioPredeterminado() {
		val optional = getRepository().findFirstByTipo(PassengerTypeStatus.INICIO);
		if (!optional.isPresent()) {
			throw new EntityNotFoundException("tipo = " + String.valueOf(PassengerTypeStatus.INICIO));
		}

		val result = asModel(optional.get());
		return result;
	}

	@Override
	protected EstadoPasajeroDto newModel() {
		return new EstadoPasajeroDto();
	}
}