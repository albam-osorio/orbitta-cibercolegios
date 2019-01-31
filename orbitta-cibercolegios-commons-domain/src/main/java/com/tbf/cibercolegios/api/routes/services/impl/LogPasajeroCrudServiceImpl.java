package com.tbf.cibercolegios.api.routes.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tbf.cibercolegios.api.model.routes.LogPasajero;
import com.tbf.cibercolegios.api.routes.model.graph.LogPasajeroDto;
import com.tbf.cibercolegios.api.routes.repository.EstadoPasajeroRepository;
import com.tbf.cibercolegios.api.routes.repository.LogPasajeroRepository;
import com.tbf.cibercolegios.api.routes.repository.RutaRepository;
import com.tbf.cibercolegios.api.routes.services.api.LogPasajeroService;

import co.com.orbitta.core.services.crud.impl.CrudServiceImpl;
import lombok.val;

@Service
public class LogPasajeroCrudServiceImpl extends CrudServiceImpl<LogPasajero, LogPasajeroDto, Integer>
		implements LogPasajeroService {

	@Autowired
	private LogPasajeroRepository repository;

	@Autowired
	private RutaRepository rutaRepository;

	@Autowired
	private EstadoPasajeroRepository estadoRepository;

	@Override
	protected LogPasajeroRepository getRepository() {
		return repository;
	}

	@Override
	public LogPasajeroDto asModel(LogPasajero entity) {
		val model = newModel();
		mapModel(entity, model);
		
		model.setRutaId(entity.getRuta().getId());
		model.setUsuarioId(entity.getUsuarioId());
		model.setSentido(entity.getSentido());

		model.setEstadoId(entity.getEstado().getId());
		model.setEstadoDescripcion(entity.getEstado().getDescripcion());
		model.setTipoEstado(entity.getEstado().getTipo());

		model.setX(entity.getX());
		model.setY(entity.getY());
		
		return model;
	}

	@Override
	protected LogPasajero mergeEntity(LogPasajeroDto model, LogPasajero entity) {
		val ruta = rutaRepository.findById(model.getRutaId());
		val estado = estadoRepository.findById(model.getEstadoId());

		entity.setRuta(ruta.get());
		entity.setUsuarioId(model.getUsuarioId());
		entity.setSentido(entity.getSentido());
		entity.setEstado(estado.get());
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