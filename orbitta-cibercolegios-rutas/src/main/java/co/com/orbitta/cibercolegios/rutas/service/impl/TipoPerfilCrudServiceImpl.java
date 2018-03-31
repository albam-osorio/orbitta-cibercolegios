package co.com.orbitta.cibercolegios.rutas.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.dto.TipoPerfilDto;
import co.com.orbitta.cibercolegios.rutas.domain.TipoPerfil;
import co.com.orbitta.cibercolegios.rutas.repository.TipoPerfilRepository;
import co.com.orbitta.cibercolegios.rutas.service.api.TipoPerfilCrudService;
import co.com.orbitta.core.services.crud.impl.CrudServiceImpl;
import lombok.val;

@Service
public class TipoPerfilCrudServiceImpl extends CrudServiceImpl<TipoPerfil, TipoPerfilDto, Integer>
		implements TipoPerfilCrudService {

	@Autowired
	private TipoPerfilRepository repository;

	@Override
	protected TipoPerfilRepository getRepository() {
		return repository;
	}

	@Override
	public TipoPerfilDto asModel(TipoPerfil entity) {
		// @formatter:off
		val result = TipoPerfilDto
				.builder()
				.id(entity.getId())
				.descripcion(entity.getDescripcion())

				.build();
		// @formatter:on
		return result;
	}

	@Override
	protected TipoPerfil asEntity(TipoPerfilDto model, TipoPerfil entity) {

		entity.setDescripcion(model.getDescripcion());

		return entity;
	}

	@Override
	protected TipoPerfil newEntity() {
		return new TipoPerfil();
	}
}