package com.tbf.cibercolegios.api.routes.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tbf.cibercolegios.api.core.services.impl.CrudServiceImpl;
import com.tbf.cibercolegios.api.model.routes.Direccion;
import com.tbf.cibercolegios.api.routes.model.graph.DireccionDto;
import com.tbf.cibercolegios.api.routes.repository.DireccionRepository;
import com.tbf.cibercolegios.api.routes.services.api.DireccionService;

import lombok.val;

@Service
public class DireccionCrudServiceImpl extends CrudServiceImpl<Direccion, DireccionDto, Integer>
		implements DireccionService {

	@Autowired
	private DireccionRepository repository;

	@Override
	protected DireccionRepository getRepository() {
		return repository;
	}

	@Override
	public DireccionDto asModel(Direccion entity) {
		val model = newModel();

		mapModel(entity, model);
		model.setInstitucionId(entity.getInstitucionId());
		model.setPaisId(entity.getPaisId());
		model.setDepartamentoId(entity.getDepartamentoId());
		model.setCiudadId(entity.getCiudadId());
		model.setDireccion(entity.getDireccion());
		model.setX(entity.getX());
		model.setY(entity.getY());
		model.setEstadoId(entity.getEstadoId());

		return model;
	}

	@Override
	protected Direccion mergeEntity(DireccionDto model, Direccion entity) {
		entity.setInstitucionId(model.getInstitucionId());
		entity.setPaisId(model.getPaisId());
		entity.setDepartamentoId(model.getDepartamentoId());
		entity.setCiudadId(model.getCiudadId());
		entity.setDireccion(model.getDireccion());
		entity.setX(model.getX());
		entity.setY(model.getY());
		entity.setEstadoId(model.getEstadoId());
		
		entity.setVersion(model.getVersion());
		return entity;
	}

	@Override
	protected Direccion newEntity() {
		return new Direccion();
	}

	@Override
	protected DireccionDto newModel() {
		return new DireccionDto();
	}

	@Override
	public List<DireccionDto> findAllByInstitucionId(int institucionId) {
		val result = new ArrayList<DireccionDto>();

		val entities = getRepository().findAllByInstitucionId(institucionId);

		for (val entity : entities) {
			result.add(asModel(entity));
		}

		return result;
	}
}