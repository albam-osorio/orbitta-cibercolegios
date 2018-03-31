package co.com.orbitta.cibercolegios.rutas.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.dto.EstadoDto;
import co.com.orbitta.cibercolegios.enums.TipoEventoEnum;
import co.com.orbitta.cibercolegios.rutas.domain.Estado;
import co.com.orbitta.cibercolegios.rutas.repository.EstadoRepository;
import co.com.orbitta.cibercolegios.rutas.service.api.EstadoCrudService;
import co.com.orbitta.core.services.crud.impl.CrudServiceImpl;
import lombok.val;

@Service
public class EstadoCrudServiceImpl extends CrudServiceImpl<Estado, EstadoDto, Integer> implements EstadoCrudService {

	@Autowired
	private EstadoRepository repository;

	@Override
	protected EstadoRepository getRepository() {
		return repository;
	}

	@Override
	public EstadoDto asModel(Estado entity) {

		// @formatter:off
		val result = EstadoDto
				.builder()
				.id(entity.getId())
				.tipoEvento(entity.getTipoEvento())
				.descripcion(entity.getDescripcion())

				.build();
		// @formatter:on
		return result;
	}

	@Override
	protected Estado asEntity(EstadoDto model, Estado entity) {

		entity.setTipoEvento(model.getTipoEvento());
		entity.setDescripcion(model.getDescripcion());

		return entity;
	}

	@Override
	protected Estado newEntity() {
		return new Estado();
	}

	@Override
	public List<EstadoDto> findAllByTipoEvento(TipoEventoEnum tipoEvento) {
		val entities = getRepository().findAllByTipoEventoOrderByDescripcion(tipoEvento);

		val result = asModels(entities);
		return result;
	}

	@Override
	public Optional<EstadoDto> findByDescripcion(String descripcion) {
		val optional = getRepository().findByDescripcionIgnoreCase(descripcion);

		val result = asModel(optional);
		return result;
	}
}