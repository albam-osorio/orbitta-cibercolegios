package co.com.orbitta.cibercolegios.rutas.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.dto.EstadoRutaDto;
import co.com.orbitta.cibercolegios.enums.TipoEstadoRutaEnum;
import co.com.orbitta.cibercolegios.rutas.domain.EstadoRuta;
import co.com.orbitta.cibercolegios.rutas.repository.EstadoRutaRepository;
import co.com.orbitta.cibercolegios.rutas.service.api.EstadoRutaCrudService;
import co.com.orbitta.core.services.crud.impl.CrudServiceImpl;
import lombok.val;

@Service
public class EstadoRutaCrudServiceImpl extends CrudServiceImpl<EstadoRuta, EstadoRutaDto, Integer> implements EstadoRutaCrudService {

	@Autowired
	private EstadoRutaRepository repository;

	@Override
	protected EstadoRutaRepository getRepository() {
		return repository;
	}

	@Override
	public EstadoRutaDto asModel(EstadoRuta entity) {

		// @formatter:off
		val result = EstadoRutaDto
				.builder()
				.id(entity.getId())
				.tipo(entity.getTipo())
				.descripcion(entity.getDescripcion())

				.build();
		// @formatter:on
		return result;
	}

	@Override
	protected EstadoRuta asEntity(EstadoRutaDto model, EstadoRuta entity) {

		entity.setTipo(model.getTipo());
		entity.setDescripcion(model.getDescripcion());

		return entity;
	}

	@Override
	protected EstadoRuta newEntity() {
		return new EstadoRuta();
	}

	@Override
	public List<EstadoRutaDto> findAllByTipoEvento(TipoEstadoRutaEnum tipoEvento) {
		val entities = getRepository().findAllByTipoOrderByDescripcion(tipoEvento);

		val result = asModels(entities);
		return result;
	}

	@Override
	public Optional<EstadoRutaDto> findByDescripcion(String descripcion) {
		val optional = getRepository().findByDescripcionIgnoreCase(descripcion);

		val result = asModel(optional);
		return result;
	}
}