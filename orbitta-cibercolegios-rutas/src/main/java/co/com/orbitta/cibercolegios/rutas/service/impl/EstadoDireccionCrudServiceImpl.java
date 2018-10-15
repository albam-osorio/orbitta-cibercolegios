package co.com.orbitta.cibercolegios.rutas.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.rutas.domain.EstadoDireccion;
import co.com.orbitta.cibercolegios.rutas.dto.EstadoDireccionDto;
import co.com.orbitta.cibercolegios.rutas.repository.EstadoDireccionRepository;
import co.com.orbitta.cibercolegios.rutas.service.api.EstadoDireccionService;
import co.com.orbitta.core.services.crud.impl.CrudServiceImpl;
import lombok.val;

@Service
public class EstadoDireccionCrudServiceImpl extends CrudServiceImpl<EstadoDireccion, EstadoDireccionDto, Integer>
		implements EstadoDireccionService {

	@Autowired
	private EstadoDireccionRepository repository;

	@Override
	protected EstadoDireccionRepository getRepository() {
		return repository;
	}

	@Override
	public EstadoDireccionDto asModel(EstadoDireccion entity) {
		val result = new EstadoDireccionDto();

		result.setId(entity.getId());

		result.setDescripcion(entity.getDescripcion());

		result.setVersion(entity.getVersion());
		result.setFechaCreacion(entity.getFechaCreacion());
		result.setCreadoPor(entity.getCreadoPor());
		result.setFechaModificacion(entity.getFechaModificacion());
		result.setModificadoPor(entity.getModificadoPor());

		return result;
	}

	@Override
	protected EstadoDireccion mergeEntity(EstadoDireccionDto model, EstadoDireccion entity) {

		entity.setDescripcion(model.getDescripcion());
		
		entity.setVersion(model.getVersion());
		return entity;
	}

	@Override
	protected EstadoDireccion newEntity() {
		return new EstadoDireccion();
	}

	@Override
	public List<EstadoDireccionDto> findAll() {
		val entities = getRepository().findAll();

		val result = asModels(entities);
		return result;
	}
}