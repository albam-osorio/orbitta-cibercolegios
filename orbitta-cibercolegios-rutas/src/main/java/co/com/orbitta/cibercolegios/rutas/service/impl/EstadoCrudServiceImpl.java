package co.com.orbitta.cibercolegios.rutas.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.dto.EstadoDto;
import co.com.orbitta.cibercolegios.rutas.domain.Estado;
import co.com.orbitta.cibercolegios.rutas.repository.EstadoRepository;
import co.com.orbitta.cibercolegios.rutas.service.api.EstadoCrudService;
import co.com.orbitta.core.services.crud.impl.CrudServiceImpl;
import lombok.val;

@Service
public class EstadoCrudServiceImpl extends CrudServiceImpl<Estado, EstadoDto, EstadoDto, Integer>
		implements EstadoCrudService {

	@Autowired
	private EstadoRepository repository;

	@Override
	protected EstadoRepository getRepository() {
		return repository;
	}

	@Override
	protected EstadoDto getModelFromEntity(Estado entity) {

		// @formatter:off
		val result = EstadoDto
				.builder()
				.id(entity.getId())
				.descripcion(entity.getDescripcion())

				.build();
		// @formatter:on
		return result;
	}

	@Override
	protected EstadoDto getItemModelFromEntity(Estado entity) {
		return getModelFromEntity(entity);
	}

	@Override
	protected Estado mapModelToEntity(EstadoDto model, Estado entity) {

		entity.setDescripcion(model.getDescripcion());

		return entity;
	}

	@Override
	protected Estado getNewEntity() {
		return new Estado();
	}
}