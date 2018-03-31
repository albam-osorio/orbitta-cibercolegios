package co.com.orbitta.cibercolegios.rutas.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.dto.DireccionUsuarioDto;
import co.com.orbitta.cibercolegios.rutas.domain.DireccionUsuario;
import co.com.orbitta.cibercolegios.rutas.repository.DireccionUsuarioRepository;
import co.com.orbitta.cibercolegios.rutas.repository.UsuarioRepository;
import co.com.orbitta.cibercolegios.rutas.service.api.DireccionUsuarioCrudService;
import co.com.orbitta.core.services.crud.impl.CrudServiceImpl;
import lombok.val;

@Service
public class DireccionUsuarioCrudServiceImpl extends CrudServiceImpl<DireccionUsuario, DireccionUsuarioDto, Integer>
		implements DireccionUsuarioCrudService {

	@Autowired
	private DireccionUsuarioRepository repository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	protected DireccionUsuarioRepository getRepository() {
		return repository;
	}

	@Override
	public DireccionUsuarioDto asModel(DireccionUsuario entity) {

		// @formatter:off
		val result = DireccionUsuarioDto
				.builder()
				.id(entity.getId())
				.usuarioId(entity.getUsuario().getId())
				.descripcion(entity.getDescripcion())
				.ubicacionLat(entity.getUbicacionLat())
				.ubicacionLon(entity.getUbicacionLon())
				.ubicacionGeo(entity.getUbicacionGeo())

				.build();
		// @formatter:on
		return result;
	}

	@Override
	protected DireccionUsuario asEntity(DireccionUsuarioDto model, DireccionUsuario entity) {
		val usuario = usuarioRepository.findById(model.getUsuarioId());

		entity.setUsuario(usuario.get());
		entity.setDescripcion(model.getDescripcion());
		entity.setUbicacionLat(model.getUbicacionLat());
		entity.setUbicacionLon(model.getUbicacionLon());
		entity.setUbicacionGeo(model.getUbicacionGeo());

		return entity;
	}

	@Override
	protected DireccionUsuario newEntity() {
		return new DireccionUsuario();
	}
}