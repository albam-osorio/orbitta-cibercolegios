package co.com.orbitta.cibercolegios.rutas.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.dto.InstitucionDto;
import co.com.orbitta.cibercolegios.rutas.domain.Institucion;
import co.com.orbitta.cibercolegios.rutas.repository.InstitucionRepository;
import co.com.orbitta.cibercolegios.rutas.service.api.InstitucionCrudService;
import co.com.orbitta.core.services.crud.impl.CrudServiceImpl;
import lombok.val;

@Service
public class InstitucionCrudServiceImpl extends CrudServiceImpl<Institucion, InstitucionDto, InstitucionDto, Integer>
		implements InstitucionCrudService {

	@Autowired
	private InstitucionRepository repository;


	@Override
	protected InstitucionRepository getRepository() {
		return repository;
	}

	@Override
	protected InstitucionDto getModelFromEntity(Institucion entity) {
		// @formatter:off
		val result = InstitucionDto
				.builder()
				.id(entity.getId())
				.descripcion(entity.getDescripcion())

				.build();
		// @formatter:on
		return result;
	}

	@Override
	protected InstitucionDto getItemModelFromEntity(Institucion entity) {
		return getModelFromEntity(entity);
	}

	@Override
	protected Institucion mapModelToEntity(InstitucionDto model, Institucion entity) {

		entity.setDescripcion(model.getDescripcion());

		return entity;
	}

	@Override
	protected Institucion getNewEntity() {
		return new Institucion();
	}
}