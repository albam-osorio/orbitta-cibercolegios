package com.tbf.cibercolegios.api.routes.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tbf.cibercolegios.api.core.services.impl.CrudServiceImpl;
import com.tbf.cibercolegios.api.model.routes.LogRuta;
import com.tbf.cibercolegios.api.routes.model.graph.LogRutaDto;
import com.tbf.cibercolegios.api.routes.repository.EstadoRutaRepository;
import com.tbf.cibercolegios.api.routes.repository.LogRutaRepository;
import com.tbf.cibercolegios.api.routes.services.api.LogRutaService;

import lombok.val;

@Service
public class LogRutaCrudServiceImpl extends CrudServiceImpl<LogRuta, LogRutaDto, Integer> implements LogRutaService {

	@Autowired
	private LogRutaRepository repository;

	@Autowired
	private EstadoRutaRepository estadoRepository;

	@Override
	protected LogRutaRepository getRepository() {
		return repository;
	}

	@Override
	public LogRutaDto asModel(LogRuta entity) {
		val model = newModel();
		mapModel(entity, model);
		
		model.setRutaId(entity.getRutaId());
		model.setMonitorId(entity.getMonitorId());
		model.setSentido(entity.getSentido());
		model.setEstadoId(entity.getEstadoId());
		
		model.setX(entity.getX());
		model.setY(entity.getY());

		return model;
	}

	@Override
	protected LogRuta mergeEntity(LogRutaDto model, LogRuta entity) {
		val estado = estadoRepository.findById(model.getEstadoId()).get();

		entity.setRutaId(model.getRutaId());
		entity.setMonitorId(model.getMonitorId());
		entity.setSentido(model.getSentido());
		entity.setEstadoId(estado.getId());
		entity.setX(model.getX());
		entity.setY(model.getY());

		entity.setVersion(model.getVersion());

		return entity;
	}

	@Override
	protected LogRutaDto newModel() {
		return new LogRutaDto();
	}
	
	@Override
	protected LogRuta newEntity() {
		return new LogRuta();
	}
}