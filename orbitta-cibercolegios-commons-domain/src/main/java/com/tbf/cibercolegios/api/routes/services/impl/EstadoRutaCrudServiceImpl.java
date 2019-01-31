package com.tbf.cibercolegios.api.routes.services.impl;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tbf.cibercolegios.api.model.routes.EstadoRuta;
import com.tbf.cibercolegios.api.routes.model.enums.RouteTypeStatus;
import com.tbf.cibercolegios.api.routes.model.graph.EstadoRutaDto;
import com.tbf.cibercolegios.api.routes.repository.EstadoRutaRepository;
import com.tbf.cibercolegios.api.routes.services.api.EstadoRutaService;

import co.com.orbitta.core.services.crud.impl.CrudServiceImpl;
import lombok.val;

@Service
public class EstadoRutaCrudServiceImpl extends CrudServiceImpl<EstadoRuta, EstadoRutaDto, Integer>
		implements EstadoRutaService {

	@Autowired
	private EstadoRutaRepository repository;

	@Override
	protected EstadoRutaRepository getRepository() {
		return repository;
	}

	@Override
	public EstadoRutaDto asModel(EstadoRuta entity) {
		val model = newModel();
		mapModel(entity, model);
		
		model.setDescripcion(entity.getDescripcion());
		model.setTipo(entity.getTipo());
		model.setPredeterminado(entity.isPredeterminado());
		
		return model;
	}

	@Override
	protected EstadoRuta mergeEntity(EstadoRutaDto model, EstadoRuta entity) {

		entity.setDescripcion(model.getDescripcion());
		entity.setTipo(model.getTipo());
		entity.setPredeterminado(model.isPredeterminado());

		entity.setVersion(model.getVersion());
		return entity;
	}

	@Override
	protected EstadoRuta newEntity() {
		return new EstadoRuta();
	}

	@Override
	public List<EstadoRutaDto> findAll() {
		val entities = getRepository().findAll();

		val result = asModels(entities);
		return result;
	}

	@Override
	public List<EstadoRutaDto> findAllByTipoEvento(RouteTypeStatus tipoEvento) {
		val entities = getRepository().findAllByTipoOrderByDescripcion(tipoEvento);

		val result = asModels(entities);
		return result;
	}

	@Override
	public EstadoRutaDto findEstadoInicioPredeterminado() {
		val result = findEstadoPredeterminadoByTipo(RouteTypeStatus.INICIO);
		return result;
	}

	@Override
	public EstadoRutaDto findEstadoRecorridoPredeterminado() {
		val result = findEstadoPredeterminadoByTipo(RouteTypeStatus.RECORRIDO);
		return result;
	}

	@Override
	public EstadoRutaDto findEstadoFinPredeterminado() {
		val result = findEstadoPredeterminadoByTipo(RouteTypeStatus.FIN);
		return result;
	}

	private EstadoRutaDto findEstadoPredeterminadoByTipo(RouteTypeStatus tipo) {
		val optional = getRepository().findFirstByTipoAndPredeterminado(tipo, true);
		if (!optional.isPresent()) {
			throw new EntityNotFoundException("tipo = " + String.valueOf(tipo));
		}

		val result = asModel(optional.get());
		return result;
	}

	@Override
	protected EstadoRutaDto newModel() {
		return new EstadoRutaDto();
	}
}