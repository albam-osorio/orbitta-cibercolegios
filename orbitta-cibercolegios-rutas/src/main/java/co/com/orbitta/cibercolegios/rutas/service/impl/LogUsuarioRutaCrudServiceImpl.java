package co.com.orbitta.cibercolegios.rutas.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.dto.LogUsuarioRutaDto;
import co.com.orbitta.cibercolegios.rutas.domain.LogUsuarioRuta;
import co.com.orbitta.cibercolegios.rutas.repository.EstadoUsuarioRutaRepository;
import co.com.orbitta.cibercolegios.rutas.repository.LogUsuarioRutaRepository;
import co.com.orbitta.cibercolegios.rutas.repository.UsuarioRutaRepository;
import co.com.orbitta.cibercolegios.rutas.service.api.LogUsuarioRutaCrudService;
import co.com.orbitta.core.services.crud.impl.CrudServiceImpl;
import lombok.val;

@Service
public class LogUsuarioRutaCrudServiceImpl extends CrudServiceImpl<LogUsuarioRuta, LogUsuarioRutaDto, Integer>
		implements LogUsuarioRutaCrudService {

	@Autowired
	private LogUsuarioRutaRepository repository;
	
	@Autowired
	private UsuarioRutaRepository usuarioRutaRepository;

	@Autowired
	private EstadoUsuarioRutaRepository estadoUsuarioRutaRepository;

	@Override
	protected LogUsuarioRutaRepository getRepository() {
		return repository;
	}

	@Override
	public LogUsuarioRutaDto asModel(LogUsuarioRuta entity) {

		// @formatter:off
		val result = LogUsuarioRutaDto
				.builder()
				.id(entity.getId())
				.usuarioRutaId(entity.getUsuarioRuta().getId())
				.sentido(entity.getSentido())
				.fechaHora(entity.getFechaHora())
				.estadoId(entity.getEstadoUsuarioRuta().getId())

				.build();
		// @formatter:on
		return result;
	}

	@Override
	protected LogUsuarioRuta asEntity(LogUsuarioRutaDto model, LogUsuarioRuta entity) {
		val usuarioRuta = usuarioRutaRepository.findById(model.getUsuarioRutaId());
		val estadoUsuarioRuta = estadoUsuarioRutaRepository.findById(model.getEstadoId());

		entity.setUsuarioRuta(usuarioRuta.get());
		entity.setSentido(entity.getSentido());
		entity.setFechaHora(model.getFechaHora());
		entity.setEstadoUsuarioRuta(estadoUsuarioRuta.get());

		return entity;
	}

	@Override
	protected LogUsuarioRuta newEntity() {
		return new LogUsuarioRuta();
	}
}