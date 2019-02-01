package com.tbf.cibercolegios.api.routes.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tbf.cibercolegios.api.core.services.impl.CrudServiceImpl;
import com.tbf.cibercolegios.api.model.routes.Acudiente;
import com.tbf.cibercolegios.api.routes.model.graph.AcudienteDto;
import com.tbf.cibercolegios.api.routes.repository.AcudienteRepository;
import com.tbf.cibercolegios.api.routes.services.api.AcudienteService;

import lombok.val;

@Service
public class AcudienteCrudServiceImpl extends CrudServiceImpl<Acudiente, AcudienteDto, Integer>
		implements AcudienteService {

	@Autowired
	private AcudienteRepository repository;

	@Override
	protected AcudienteRepository getRepository() {
		return repository;
	}

	@Override
	public AcudienteDto asModel(Acudiente entity) {
		val model = newModel();
		mapModel(entity, model);

		model.setUsuarioId(entity.getUsuarioId());
		model.setToken(entity.getToken());

		return model;
	}

	@Override
	protected Acudiente mergeEntity(AcudienteDto model, Acudiente entity) {
		entity.setUsuarioId(model.getUsuarioId());
		entity.setToken(model.getToken());

		entity.setVersion(model.getVersion());
		return entity;
	}

	@Override
	protected Acudiente newEntity() {
		return new Acudiente();
	}

	@Override
	protected AcudienteDto newModel() {
		return new AcudienteDto();
	}

	@Override
	public Optional<AcudienteDto> findByUsuarioId(int usuarioId) {
		val optional = getRepository().findByUsuarioId(usuarioId);

		val result = asModel(optional);
		return result;
	}
}