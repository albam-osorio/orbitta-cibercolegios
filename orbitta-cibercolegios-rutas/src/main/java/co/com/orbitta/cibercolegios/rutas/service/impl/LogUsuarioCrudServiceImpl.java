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
public class LogUsuarioCrudServiceImpl extends CrudServiceImpl<LogUsuario, LogUsuarioDto, LogUsuarioDto, Integer>
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
	protected LogUsuarioDto getModelFromEntity(LogUsuario entity) {

		// @formatter:off
		val result = LogUsuarioDto
				.builder()
				.id(entity.getId())
				.fecha(entity.getFecha())
				.logId(entity.getLog().getId())
				.usuarioRutaId(entity.getUsuarioRuta().getId())
				.estadoId(entity.getEstado().getId())

				.build();
		// @formatter:on
		return result;
	}

	@Override
	protected LogUsuarioDto getItemModelFromEntity(LogUsuario entity) {
		return getModelFromEntity(entity);
	}

	@Override
	protected LogUsuario mapModelToEntity(LogUsuarioDto model, LogUsuario entity) {
		val log = logRepository.findById(model.getLogId());
		val usuarioRuta = usuarioRutaRepository.findById(model.getUsuarioRutaId());
		val estado = estadoRepository.findById(model.getEstadoId());

		
		entity.setFecha(model.getFecha());
		entity.setLog(log.get());
		entity.setUsuarioRuta(usuarioRuta.get());
		entity.setEstado(estado.get());

		return entity;
	}

	@Override
	protected LogUsuario getNewEntity() {
		return new LogUsuario();
	}
}