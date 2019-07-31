package com.tbf.cibercolegios.api.routes.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tbf.cibercolegios.api.core.services.impl.CrudServiceImpl;
import com.tbf.cibercolegios.api.model.routes.LogPasajero;
import com.tbf.cibercolegios.api.routes.model.graph.LogPasajeroDto;
import com.tbf.cibercolegios.api.routes.repository.LogPasajeroRepository;
import com.tbf.cibercolegios.api.routes.services.api.LogPasajeroService;

import lombok.val;

@Service
public class LogPasajeroCrudServiceImpl extends CrudServiceImpl<LogPasajero, LogPasajeroDto, Integer>
		implements LogPasajeroService {

	@Autowired
	private LogPasajeroRepository repository;

	@Override
	protected LogPasajeroRepository getRepository() {
		return repository;
	}

	@Override
	public LogPasajeroDto asModel(LogPasajero entity) {
		val model = newModel();
		mapModel(entity, model);
		
		model.setUsuarioId(entity.getUsuarioId());
		model.setSentido(entity.getSentido());
		model.setDireccionId(entity.getDireccionId());
		
		model.setRutaId(entity.getRutaId());
		model.setSecuencia(entity.getSecuencia());
		
		model.setEstadoId(entity.getEstadoId());
		model.setX(entity.getX());
		model.setY(entity.getY());
		
		return model;
	}

	@Override
	protected LogPasajero mergeEntity(LogPasajeroDto model, LogPasajero entity) {

		entity.setUsuarioId(model.getUsuarioId());
		entity.setSentido(model.getSentido());
		entity.setDireccionId(model.getDireccionId());
		
		entity.setRutaId(model.getRutaId());
		entity.setSecuencia(model.getSecuencia());
		
		entity.setEstadoId(model.getEstadoId());
		entity.setX(model.getX());
		entity.setY(model.getY());

		entity.setVersion(model.getVersion());

		return entity;
	}

	@Override
	protected LogPasajeroDto newModel() {
		return new LogPasajeroDto();
	}

	@Override
	protected LogPasajero newEntity() {
		return new LogPasajero();
	}
}