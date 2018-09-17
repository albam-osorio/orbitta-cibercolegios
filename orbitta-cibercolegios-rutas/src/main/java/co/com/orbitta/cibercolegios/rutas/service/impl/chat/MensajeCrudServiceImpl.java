package co.com.orbitta.cibercolegios.rutas.service.impl.chat;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.rutas.domain.chat.Mensaje;
import co.com.orbitta.cibercolegios.rutas.dto.chat.MensajeDto;
import co.com.orbitta.cibercolegios.rutas.repository.RutaRepository;
import co.com.orbitta.cibercolegios.rutas.repository.chat.ConversacionRepository;
import co.com.orbitta.cibercolegios.rutas.repository.chat.MensajeRepository;
import co.com.orbitta.cibercolegios.rutas.service.api.chat.MensajeCrudService;
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
				.monitorId(entity.getMonitorId())
				.origen(entity.getOrigen())
				.fechaHora(entity.getFechaHora())
				.mensaje(entity.getMensaje())
				.build();
		// @formatter:on
		return result;
	}

	@Override
	protected Mensaje mergeEntity(MensajeDto model, Mensaje entity) {
		val conversacion = conversacionRepository.findById(model.getConversacionId());
		val ruta = rutaRepository.findById(model.getRutaId());

		entity.setConversacion(conversacion.get());
		entity.setRuta(ruta.get());
		entity.setMonitorId(model.getMonitorId());
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