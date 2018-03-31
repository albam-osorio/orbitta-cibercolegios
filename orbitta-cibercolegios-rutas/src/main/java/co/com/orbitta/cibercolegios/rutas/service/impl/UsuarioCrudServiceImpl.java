package co.com.orbitta.cibercolegios.rutas.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.dto.UsuarioDto;
import co.com.orbitta.cibercolegios.rutas.domain.Usuario;
import co.com.orbitta.cibercolegios.rutas.repository.UsuarioRepository;
import co.com.orbitta.cibercolegios.rutas.service.api.UsuarioCrudService;
import co.com.orbitta.core.services.crud.impl.CrudServiceImpl;
import lombok.val;

@Service
public class UsuarioCrudServiceImpl extends CrudServiceImpl<Usuario, UsuarioDto, Integer>
		implements UsuarioCrudService {

	@Autowired
	private UsuarioRepository repository;

	@Override
	protected UsuarioRepository getRepository() {
		return repository;
	}

	@Override
	public UsuarioDto asModel(Usuario entity) {

		// @formatter:off
		val result = UsuarioDto
				.builder()
				.id(entity.getId())
				.nombre1(entity.getNombre1())
				.nombre2(entity.getNombre2())
				.apellido1(entity.getApellido1())
				.apellido2(entity.getApellido2())
				.correo(entity.getCorreo())
				.sexo(entity.getSexo())
				

				.build();
		// @formatter:on
		return result;
	}

	@Override
	protected Usuario asEntity(UsuarioDto model, Usuario entity) {

		entity.setNombre1(model.getNombre1());
		entity.setNombre2(model.getNombre2());
		entity.setApellido1(model.getApellido1());
		entity.setApellido2(model.getApellido2());
		entity.setCorreo(model.getCorreo());
		entity.setSexo(model.getSexo());

		return entity;
	}

	@Override
	protected Usuario newEntity() {
		return new Usuario();
	}
}