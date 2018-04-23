package co.com.orbitta.cibercolegios.rutas.service.impl;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.dto.EstadoPasajeroDto;
import co.com.orbitta.cibercolegios.enums.TipoEstadoPasajeroEnum;
import co.com.orbitta.cibercolegios.rutas.domain.EstadoPasajero;
import co.com.orbitta.cibercolegios.rutas.repository.EstadoPasajeroRepository;
import co.com.orbitta.cibercolegios.rutas.service.api.EstadoPasajeroCrudService;
import co.com.orbitta.core.services.crud.impl.CrudServiceImpl;
import lombok.val;

@Service
public class EstadoPasajeroCrudServiceImpl extends CrudServiceImpl<EstadoPasajero, EstadoPasajeroDto, Integer>
		implements EstadoPasajeroCrudService {

	@Autowired
	private EstadoPasajeroRepository repository;

	@Override
	protected EstadoPasajeroRepository getRepository() {
		return repository;
	}

	@Override
	public EstadoPasajeroDto asModel(EstadoPasajero entity) {

		// @formatter:off
		val result = EstadoPasajeroDto
				.builder()
				.id(entity.getId())
				.tipo(entity.getTipo())
				.descripcion(entity.getDescripcion())
				.aplicaIda(entity.isAplicaSentidoIda())
				.aplicaRetorno(entity.isAplicaSentidoRetorno())

				.build();
		// @formatter:on
		return result;
	}

	@Override
	protected EstadoPasajero asEntity(EstadoPasajeroDto model, EstadoPasajero entity) {

		entity.setTipo(model.getTipo());
		entity.setDescripcion(model.getDescripcion());
		entity.setAplicaSentidoIda(model.isAplicaIda());
		entity.setAplicaSentidoRetorno(model.isAplicaRetorno());

		return entity;
	}

	@Override
	protected EstadoPasajero newEntity() {
		return new EstadoPasajero();
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