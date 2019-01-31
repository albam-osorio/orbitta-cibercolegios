package com.tbf.cibercolegios.api.routes.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tbf.cibercolegios.api.model.routes.EstadoDireccion;
import com.tbf.cibercolegios.api.routes.model.graph.EstadoDireccionDto;
import com.tbf.cibercolegios.api.routes.repository.EstadoDireccionRepository;
import com.tbf.cibercolegios.api.routes.services.api.EstadoDireccionService;

import co.com.orbitta.core.services.crud.impl.CrudServiceImpl;
import lombok.val;

@Service
public class EstadoDireccionCrudServiceImpl extends CrudServiceImpl<EstadoDireccion, EstadoDireccionDto, Integer>
		implements EstadoDireccionService {

	@Autowired
	private EstadoDireccionRepository repository;

	@Override
	protected EstadoDireccionRepository getRepository() {
		return repository;
	}

	@Override
	public EstadoDireccionDto asModel(EstadoDireccion entity) {
		val model = newModel();
		mapModel(entity, model);

		model.setDescripcion(entity.getDescripcion());

		return model;
	}

	@Override
	protected EstadoDireccion mergeEntity(EstadoDireccionDto model, EstadoDireccion entity) {

		entity.setDescripcion(model.getDescripcion());
		
		entity.setVersion(model.getVersion());
		return entity;
	}

	@Override
	protected EstadoDireccion newEntity() {
		return new EstadoDireccion();
	}

	@Override
	public List<EstadoDireccionDto> findAll() {
		val entities = getRepository().findAll();

		val result = asModels(entities);
		return result;
	}

	@Override
	protected EstadoDireccionDto newModel() {
		return new EstadoDireccionDto();
	}
}