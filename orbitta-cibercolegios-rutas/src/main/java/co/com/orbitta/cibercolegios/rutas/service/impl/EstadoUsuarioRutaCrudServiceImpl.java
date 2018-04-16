package co.com.orbitta.cibercolegios.rutas.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.dto.EstadoUsuarioRutaDto;
import co.com.orbitta.cibercolegios.rutas.domain.EstadoUsuarioRuta;
import co.com.orbitta.cibercolegios.rutas.repository.EstadoUsuarioRutaRepository;
import co.com.orbitta.cibercolegios.rutas.service.api.EstadoUsuarioRutaCrudService;
import co.com.orbitta.core.services.crud.impl.CrudServiceImpl;
import lombok.val;

@Service
public class EstadoUsuarioRutaCrudServiceImpl extends CrudServiceImpl<EstadoUsuarioRuta, EstadoUsuarioRutaDto, Integer> implements EstadoUsuarioRutaCrudService {

	@Autowired
	private EstadoUsuarioRutaRepository repository;

	@Override
	protected EstadoUsuarioRutaRepository getRepository() {
		return repository;
	}

	@Override
	public EstadoUsuarioRutaDto asModel(EstadoUsuarioRuta entity) {

		// @formatter:off
		val result = EstadoUsuarioRutaDto
				.builder()
				.id(entity.getId())
				.descripcion(entity.getDescripcion())
				.aplicaIda(entity.isAplicaIda())
				.aplicaRetorno(entity.isAplicaRetorno())

				.build();
		// @formatter:on
		return result;
	}

	@Override
	protected EstadoUsuarioRuta asEntity(EstadoUsuarioRutaDto model, EstadoUsuarioRuta entity) {

		entity.setDescripcion(model.getDescripcion());
		entity.setAplicaIda(model.isAplicaIda());
		entity.setAplicaRetorno(model.isAplicaRetorno());

		return entity;
	}

	@Override
	protected EstadoUsuarioRuta newEntity() {
		return new EstadoUsuarioRuta();
	}
}