package co.com.orbitta.cibercolegios.rutas.service.impl;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.rutas.domain.EstadoPasajero;
import co.com.orbitta.cibercolegios.rutas.dto.EstadoPasajeroDto;
import co.com.orbitta.cibercolegios.rutas.enums.TipoEstadoPasajeroEnum;
import co.com.orbitta.cibercolegios.rutas.repository.EstadoPasajeroRepository;
import co.com.orbitta.cibercolegios.rutas.service.api.EstadoPasajeroService;
import co.com.orbitta.core.services.crud.impl.CrudServiceImpl;
import lombok.val;

@Service
public class EstadoPasajeroCrudServiceImpl extends CrudServiceImpl<EstadoPasajero, EstadoPasajeroDto, Integer>
		implements EstadoPasajeroService {

	@Autowired
	private EstadoPasajeroRepository repository;

	@Override
	protected EstadoPasajeroRepository getRepository() {
		return repository;
	}

	@Override
	public EstadoPasajeroDto asModel(EstadoPasajero entity) {
		val result = new EstadoPasajeroDto();

		result.setId(entity.getId());

		result.setDescripcion(entity.getDescripcion());
		result.setTipo(entity.getTipo());
		result.setAplicaSentidoIda(entity.isAplicaSentidoIda());
		result.setAplicaSentidoRetorno(entity.isAplicaSentidoRetorno());

		result.setVersion(entity.getVersion());
		result.setFechaCreacion(entity.getFechaCreacion());
		result.setCreadoPor(entity.getCreadoPor());
		result.setFechaModificacion(entity.getFechaModificacion());
		result.setModificadoPor(entity.getModificadoPor());

		return result;
	}

	@Override
	protected EstadoPasajero mergeEntity(EstadoPasajeroDto model, EstadoPasajero entity) {

		entity.setDescripcion(model.getDescripcion());
		entity.setTipo(model.getTipo());
		entity.setAplicaSentidoIda(model.isAplicaSentidoIda());
		entity.setAplicaSentidoRetorno(model.isAplicaSentidoRetorno());

		entity.setVersion(model.getVersion());
		return entity;
	}

	@Override
	protected EstadoPasajero newEntity() {
		return new EstadoPasajero();
	}

	@Override
	public List<EstadoPasajeroDto> findAll() {
		val entities = getRepository().findAll();

		val result = asModels(entities);
		return result;
	}

	@Override
	public EstadoPasajeroDto findEstadoInicioPredeterminado() {
		val optional = getRepository().findFirstByTipo(TipoEstadoPasajeroEnum.INICIO);
		if (!optional.isPresent()) {
			throw new EntityNotFoundException("tipo = " + String.valueOf(TipoEstadoPasajeroEnum.INICIO));
		}

		val result = asModel(optional.get());
		return result;
	}
}