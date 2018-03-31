package co.com.orbitta.cibercolegios.rutas.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.dto.PerfilDto;
import co.com.orbitta.cibercolegios.rutas.domain.Perfil;
import co.com.orbitta.cibercolegios.rutas.repository.PerfilRepository;
import co.com.orbitta.cibercolegios.rutas.repository.TipoPerfilRepository;
import co.com.orbitta.cibercolegios.rutas.repository.UsuarioRepository;
import co.com.orbitta.cibercolegios.rutas.service.api.PerfilCrudService;
import co.com.orbitta.core.services.crud.impl.CrudServiceImpl;
import lombok.val;

@Service
public class PerfilCrudServiceImpl extends CrudServiceImpl<Perfil, PerfilDto, Integer> implements PerfilCrudService {

	@Autowired
	private PerfilRepository repository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private TipoPerfilRepository tipoPerfilRepository;

	@Override
	protected PerfilRepository getRepository() {
		return repository;
	}

	@Override
	public PerfilDto asModel(Perfil entity) {

		// @formatter:off
		val result = PerfilDto
				.builder()
				.id(entity.getId())
				.tipoPerfilId(entity.getTipoPerfil().getId())
				.usuarioId(entity.getUsuario().getId())
				.perfilPadreId(entity.getPerfilPadreId())
				.perfilMadreId(entity.getPerfilMadreId())

				.build();
		// @formatter:on
		return result;
	}

	@Override
	protected Perfil asEntity(PerfilDto model, Perfil entity) {
		val tipoPerfil = tipoPerfilRepository.findById(model.getTipoPerfilId());
		val usuario = usuarioRepository.findById(model.getUsuarioId());

		entity.setTipoPerfil(tipoPerfil.get());
		entity.setUsuario(usuario.get());
		entity.setPerfilPadreId(model.getPerfilPadreId());
		entity.setPerfilMadreId(model.getPerfilMadreId());

		return entity;
	}

	@Override
	protected Perfil newEntity() {
		return new Perfil();
	}
}