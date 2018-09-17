package co.com.orbitta.cibercolegios.rutas.service.impl;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.rutas.domain.EstadoRuta;
import co.com.orbitta.cibercolegios.rutas.dto.EstadoRutaDto;
import co.com.orbitta.cibercolegios.rutas.enums.TipoEstadoRutaEnum;
import co.com.orbitta.cibercolegios.rutas.repository.EstadoRutaRepository;
import co.com.orbitta.cibercolegios.rutas.service.api.EstadoRutaCrudService;
import co.com.orbitta.core.services.crud.impl.CrudServiceImpl;
import lombok.val;

@Service
public class EstadoRutaCrudServiceImpl extends CrudServiceImpl<EstadoRuta, EstadoRutaDto, Integer>
		implements EstadoRutaCrudService {

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
				.predeterminado(entity.isPredeterminado())

				.build();
		// @formatter:on
		return result;
	}

	@Override
	protected EstadoRuta mergeEntity(EstadoRutaDto model, EstadoRuta entity) {

		entity.setTipo(model.getTipo());
		entity.setDescripcion(model.getDescripcion());
		entity.setPredeterminado(model.isPredeterminado());

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
	public List<EstadoRutaDto> findAllByTipoEvento(TipoEstadoRutaEnum tipoEvento) {
		val entities = getRepository().findAllByTipoOrderByDescripcion(tipoEvento);

		val result = asModels(entities);
		return result;
	}

	@Override
	public EstadoRutaDto findEstadoInicioPredeterminado() {
		val result = findEstadoPredeterminadoByTipo(TipoEstadoRutaEnum.INICIO);
		return result;
	}

	@Override
	public EstadoRutaDto findEstadoNormalPredeterminado() {
		val result = findEstadoPredeterminadoByTipo(TipoEstadoRutaEnum.RECORRIDO);
		return result;
	}

	@Override
	public EstadoRutaDto findEstadoFinPredeterminado() {
		val result = findEstadoPredeterminadoByTipo(TipoEstadoRutaEnum.FIN);
		return result;
	}

	private EstadoRutaDto findEstadoPredeterminadoByTipo(TipoEstadoRutaEnum tipo) {
		val optional = getRepository().findFirstByTipoAndPredeterminado(tipo, true);
		if (!optional.isPresent()) {
			throw new EntityNotFoundException("tipo = " + String.valueOf(tipo));
		}

		val result = asModel(optional.get());
		return result;
	}
}