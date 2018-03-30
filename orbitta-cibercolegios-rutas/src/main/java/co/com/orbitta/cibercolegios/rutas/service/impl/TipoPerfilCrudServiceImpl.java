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
public class TipoPerfilCrudServiceImpl extends CrudServiceImpl<TipoPerfil, TipoPerfilDto, TipoPerfilDto, Integer>
		implements TipoPerfilCrudService {

	@Autowired
	private TipoPerfilRepository repository;

	@Override
	protected TipoPerfilRepository getRepository() {
		return repository;
	}

	@Override
	protected TipoPerfilDto getModelFromEntity(TipoPerfil entity) {
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
	protected TipoPerfilDto getItemModelFromEntity(TipoPerfil entity) {
		return getModelFromEntity(entity);
	}

	@Override
	protected TipoPerfil mapModelToEntity(TipoPerfilDto model, TipoPerfil entity) {

		entity.setDescripcion(model.getDescripcion());

		return entity;
	}

	@Override
	protected TipoPerfil getNewEntity() {
		return new TipoPerfil();
	}
}