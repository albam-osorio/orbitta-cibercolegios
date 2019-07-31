package com.tbf.cibercolegios.api.routes.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tbf.cibercolegios.api.core.services.impl.CrudServiceImpl;
import com.tbf.cibercolegios.api.model.routes.Mensaje;
import com.tbf.cibercolegios.api.routes.model.graph.MensajeDto;
import com.tbf.cibercolegios.api.routes.model.graph.chat.DatosMensajeDto;
import com.tbf.cibercolegios.api.routes.repository.MensajeRepository;
import com.tbf.cibercolegios.api.routes.services.api.MensajeService;

import lombok.val;

@Service
public class MensajeCrudServiceImpl extends CrudServiceImpl<Mensaje, MensajeDto, Integer> implements MensajeService {

	@Autowired
	private MensajeRepository repository;

	@Override
	protected MensajeRepository getRepository() {
		return repository;
	}

	@Override
	public MensajeDto asModel(Mensaje entity) {
		val model = newModel();
		mapModel(entity, model);

		model.setConversacionId(entity.getConversacionId());
		model.setMonitorId(entity.getMonitorId());
		model.setOrigen(entity.getOrigen());
		model.setMensaje(entity.getMensaje());

		return model;
	}

	@Override
	protected Mensaje mergeEntity(MensajeDto model, Mensaje entity) {
		entity.setConversacionId(model.getConversacionId());
		entity.setMonitorId(model.getMonitorId());
		entity.setOrigen(model.getOrigen());
		entity.setMensaje(model.getMensaje());

		entity.setVersion(model.getVersion());

		return entity;
	}

	@Override
	protected Mensaje newEntity() {
		return new Mensaje();
	}

	@Override
	public List<DatosMensajeDto> findAllByConversacionId(int conversacionId) {
		val entities = getRepository().findAllByConversacionId(conversacionId);

		val result = entities.stream().map(entity -> asDto(entity)).collect(Collectors.toList());

		result.sort((a, b) -> Integer.compare(a.getMensajeId(), b.getMensajeId()));

		return result;
	}

	protected DatosMensajeDto asDto(Mensaje entity) {
		val result = new DatosMensajeDto();

		result.setMensajeId(entity.getId());
		result.setConversacionId(entity.getConversacionId());
		result.setOrigen(entity.getOrigen());
		result.setMensaje(entity.getMensaje());
		result.setFechaHora(entity.getFechaCreacion());

		return result;
	}

	@Override
	protected MensajeDto newModel() {
		return new MensajeDto();
	}
}