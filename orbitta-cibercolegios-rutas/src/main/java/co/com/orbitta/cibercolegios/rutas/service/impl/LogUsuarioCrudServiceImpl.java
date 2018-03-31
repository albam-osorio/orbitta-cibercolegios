package co.com.orbitta.cibercolegios.rutas.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.dto.LogUsuarioDto;
import co.com.orbitta.cibercolegios.rutas.domain.LogUsuario;
import co.com.orbitta.cibercolegios.rutas.repository.EstadoRepository;
import co.com.orbitta.cibercolegios.rutas.repository.LogRepository;
import co.com.orbitta.cibercolegios.rutas.repository.LogUsuarioRepository;
import co.com.orbitta.cibercolegios.rutas.repository.UsuarioRutaRepository;
import co.com.orbitta.cibercolegios.rutas.service.api.LogUsuarioCrudService;
import co.com.orbitta.core.services.crud.impl.CrudServiceImpl;
import lombok.val;

@Service
public class LogUsuarioCrudServiceImpl extends CrudServiceImpl<LogUsuario, LogUsuarioDto, Integer>
		implements LogUsuarioCrudService {

	@Autowired
	private LogUsuarioRepository repository;
	
	@Autowired
	private LogRepository logRepository;

	@Autowired
	private UsuarioRutaRepository usuarioRutaRepository;

	@Autowired
	private EstadoRepository estadoRepository;

	@Override
	protected LogUsuarioRepository getRepository() {
		return repository;
	}

	@Override
	public LogUsuarioDto asModel(LogUsuario entity) {

		// @formatter:off
		val result = LogUsuarioDto
				.builder()
				.id(entity.getId())
				.usuarioRutaId(entity.getUsuarioRuta().getId())
				.sentido(entity.getSentido())
				.fechaHora(entity.getFechaHora())
				.estadoId(entity.getEstado().getId())
				.logId(entity.getLog().getId())

				.build();
		// @formatter:on
		return result;
	}

	@Override
	protected LogUsuario asEntity(LogUsuarioDto model, LogUsuario entity) {
		val log = logRepository.findById(model.getLogId());
		val usuarioRuta = usuarioRutaRepository.findById(model.getUsuarioRutaId());
		val estado = estadoRepository.findById(model.getEstadoId());

		
		entity.setUsuarioRuta(usuarioRuta.get());
		entity.setSentido(entity.getSentido());
		entity.setFechaHora(model.getFechaHora());
		entity.setEstado(estado.get());
		entity.setLog(log.get());

		return entity;
	}

	@Override
	protected LogUsuario newEntity() {
		return new LogUsuario();
	}
}