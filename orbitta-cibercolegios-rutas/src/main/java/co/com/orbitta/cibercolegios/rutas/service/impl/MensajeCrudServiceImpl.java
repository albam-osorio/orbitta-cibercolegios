package co.com.orbitta.cibercolegios.rutas.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.dto.MensajeDto;
import co.com.orbitta.cibercolegios.rutas.domain.Mensaje;
import co.com.orbitta.cibercolegios.rutas.repository.ConversacionRepository;
import co.com.orbitta.cibercolegios.rutas.repository.MensajeRepository;
import co.com.orbitta.cibercolegios.rutas.repository.readonly.RutaRepository;
import co.com.orbitta.cibercolegios.rutas.repository.readonly.UsuarioRepository;
import co.com.orbitta.cibercolegios.rutas.service.api.MensajeCrudService;
import co.com.orbitta.core.services.crud.impl.CrudServiceImpl;
import lombok.val;

@Service
public class MensajeCrudServiceImpl extends CrudServiceImpl<Mensaje, MensajeDto, Integer>
		implements MensajeCrudService {

	@Autowired
	private MensajeRepository repository;

	@Autowired
	private ConversacionRepository conversacionRepository;

	@Autowired
	private RutaRepository rutaRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	protected MensajeRepository getRepository() {
		return repository;
	}

	@Override
	public MensajeDto asModel(Mensaje entity) {

		// @formatter:off
		val result = MensajeDto
				.builder()
				.id(entity.getId())
				.conversacionId(entity.getConversacion().getId())
				.rutaId(entity.getRuta().getId())
				.monitorId(entity.getMonitor().getId())
				.origen(entity.getOrigen())
				.fechaHora(entity.getFechaHora())
				.mensaje(entity.getMensaje())
				.build();
		// @formatter:on
		return result;
	}

	@Override
	protected Mensaje asEntity(MensajeDto model, Mensaje entity) {
		val conversacion = conversacionRepository.findById(model.getConversacionId());
		val ruta = rutaRepository.findById(model.getRutaId());
		val monitor = usuarioRepository.findById(model.getMonitorId());

		entity.setConversacion(conversacion.get());
		entity.setRuta(ruta.get());
		entity.setMonitor(monitor.get());
		entity.setOrigen(model.getOrigen());
		entity.setFechaHora(model.getFechaHora());
		entity.setMensaje(model.getMensaje());
		
		return entity;
	}

	@Override
	protected Mensaje newEntity() {
		return new Mensaje();
	}

	@Override
	public List<MensajeDto> findAllByConversacionId(int conversacionId) {
		val entities = getRepository().findAllByConversacionId(conversacionId);

		val result = asModels(entities);
		return result;
	}
}