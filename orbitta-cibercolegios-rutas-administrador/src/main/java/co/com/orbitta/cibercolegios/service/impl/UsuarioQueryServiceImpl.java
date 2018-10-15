package co.com.orbitta.cibercolegios.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.ciber.dto.UsuarioDto;
import co.com.orbitta.cibercolegios.domain.Usuario;
import co.com.orbitta.cibercolegios.repository.UsuarioRepository;
import co.com.orbitta.cibercolegios.service.api.UsuarioQueryService;
import co.com.orbitta.core.services.crud.impl.QueryServiceImpl;
import lombok.val;

@Service
public class UsuarioQueryServiceImpl extends QueryServiceImpl<Usuario, UsuarioDto, Integer>
		implements UsuarioQueryService {

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
				.nombre(entity.getNombre())
				.apellido(entity.getApellido())
				.genero(entity.getGenero())
				.email(entity.getEmail())

				.build();
		// @formatter:on
		return result;
	}
}