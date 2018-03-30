package co.com.orbitta.cibercolegios.rutas.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.dto.LogDto;
import co.com.orbitta.cibercolegios.rutas.domain.Log;
import co.com.orbitta.cibercolegios.rutas.repository.LogRepository;
import co.com.orbitta.cibercolegios.rutas.service.api.LogCrudService;
import co.com.orbitta.core.services.crud.impl.CrudServiceImpl;
import lombok.val;

@Service
public class LogCrudServiceImpl extends CrudServiceImpl<Log, LogDto, LogDto, Integer>
		implements LogCrudService {

	@Autowired
	private LogRepository repository;

	@Override
	protected LogRepository getRepository() {
		return repository;
	}

	@Override
	protected LogDto getModelFromEntity(Log entity) {

		// @formatter:off
		val result = LogDto
				.builder()
				.id(entity.getId())
				.descripcion(entity.getDescripcion())

				.build();
		// @formatter:on
		return result;
	}

	@Override
	protected LogDto getItemModelFromEntity(Log entity) {
		return getModelFromEntity(entity);
	}

	@Override
	protected Log mapModelToEntity(LogDto model, Log entity) {

		entity.setDescripcion(model.getDescripcion());

		return entity;
	}

	@Override
	protected Log getNewEntity() {
		return new Log();
	}
}