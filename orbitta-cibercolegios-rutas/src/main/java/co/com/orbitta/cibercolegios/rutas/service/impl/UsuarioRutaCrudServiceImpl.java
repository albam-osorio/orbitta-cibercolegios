package co.com.orbitta.cibercolegios.rutas.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.dto.UsuarioRutaDto;
import co.com.orbitta.cibercolegios.rutas.domain.UsuarioRuta;
import co.com.orbitta.cibercolegios.rutas.repository.DireccionUsuarioRepository;
import co.com.orbitta.cibercolegios.rutas.repository.RutaRepository;
import co.com.orbitta.cibercolegios.rutas.repository.UsuarioRepository;
import co.com.orbitta.cibercolegios.rutas.repository.UsuarioRutaRepository;
import co.com.orbitta.cibercolegios.rutas.service.api.UsuarioRutaCrudService;
import co.com.orbitta.core.services.crud.impl.CrudServiceImpl;
import lombok.val;

@Service
public class UsuarioRutaCrudServiceImpl extends CrudServiceImpl<UsuarioRuta, UsuarioRutaDto, UsuarioRutaDto, Integer>
		implements UsuarioRutaCrudService {

	@Autowired
	private UsuarioRutaRepository repository;

	private UsuarioRepository usuarioRepository;

	@Autowired
	private RutaRepository rutaRepository;

	@Autowired
	private DireccionUsuarioRepository direccionUsuarioRepository;


	@Override
	protected UsuarioRutaRepository getRepository() {
		return repository;
	}

	@Override
	protected UsuarioRutaDto getModelFromEntity(UsuarioRuta entity) {

		// @formatter:off
		val result = UsuarioRutaDto
				.builder()
				.id(entity.getId())
				.fecha(entity.getFecha())
				.usuarioId(entity.getUsuario().getId())
				.rutaId(entity.getRuta().getId())
				.direccionUsuarioId(entity.getDireccionUsuario().getId())

				.build();
		// @formatter:on
		return result;
	}

	@Override
	protected UsuarioRutaDto getItemModelFromEntity(UsuarioRuta entity) {
		return getModelFromEntity(entity);
	}

	@Override
	protected UsuarioRuta mapModelToEntity(UsuarioRutaDto model, UsuarioRuta entity) {
		val usuario = usuarioRepository.findById(model.getUsuarioId());
		val ruta = rutaRepository.findById(model.getRutaId());
		val direccionUsuario = direccionUsuarioRepository.findById(model.getDireccionUsuarioId());

		entity.setFecha(model.getFecha());
		entity.setUsuario(usuario.get());
		entity.setRuta(ruta.get());
		entity.setDireccionUsuario(direccionUsuario.get());

		return entity;
	}

	@Override
	protected UsuarioRuta getNewEntity() {
		return new UsuarioRuta();
	}
}