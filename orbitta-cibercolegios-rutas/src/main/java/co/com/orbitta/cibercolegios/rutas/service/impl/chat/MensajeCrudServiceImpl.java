package co.com.orbitta.cibercolegios.rutas.service.impl.chat;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.rutas.domain.chat.Mensaje;
import co.com.orbitta.cibercolegios.rutas.dto.chat.DatosMensajeDto;
import co.com.orbitta.cibercolegios.rutas.dto.chat.MensajeDto;
import co.com.orbitta.cibercolegios.rutas.repository.chat.ConversacionRepository;
import co.com.orbitta.cibercolegios.rutas.repository.chat.MensajeRepository;
import co.com.orbitta.cibercolegios.rutas.service.api.chat.MensajeService;
import co.com.orbitta.core.services.crud.impl.CrudServiceImpl;
import lombok.val;

@Service
public class MensajeCrudServiceImpl extends CrudServiceImpl<Mensaje, MensajeDto, Integer>
		implements MensajeService {

	@Autowired
	private MensajeRepository repository;

	@Autowired
	private ConversacionRepository conversacionRepository;

	@Override
	protected MensajeRepository getRepository() {
		return repository;
	}

	@Override
	public MensajeDto asModel(Mensaje entity) {
		val result = new MensajeDto();

		result.setId(entity.getId());
		result.setConversacionId(entity.getConversacion().getId());
		result.setMonitorId(entity.getMonitorId());
		result.setOrigen(entity.getOrigen());
		result.setMensaje(entity.getMensaje());

		result.setVersion(entity.getVersion());
		result.setFechaCreacion(entity.getFechaCreacion());
		result.setFechaModificacion(entity.getFechaModificacion());

		return result;
	}

	@Override
	protected Mensaje mergeEntity(MensajeDto model, Mensaje entity) {
		val conversacion = conversacionRepository.findById(model.getConversacionId());

		entity.setConversacion(conversacion.get());
		entity.setMonitorId(model.getMonitorId());
		entity.setOrigen(model.getOrigen());
		entity.setMensaje(model.getMensaje());
		
		entity.setVersion(model.getVersion());

		return entity;
	}

	@Override
	protected Mensaje newEntity() {
		return new Mensaje();
	}

	@Override
	public List<DatosMensajeDto> findAllByConversacionId(int conversacionId) {
		val entities = getRepository().findAllByConversacionId(conversacionId);

		val result = entities.stream().map(entity -> asDto(entity)).collect(Collectors.toList());
		
		result.sort((a, b) -> Integer.compare(a.getMensajeId(), b.getMensajeId()));
		
		return result;
	}
	
	protected DatosMensajeDto asDto(Mensaje entity) {
		val result = new DatosMensajeDto();

		result.setMensajeId(entity.getId());
		result.setConversacionId(entity.getConversacion().getId());
		result.setOrigen(entity.getOrigen());
		result.setMensaje(entity.getMensaje());
		result.setFechaHora(entity.getFechaCreacion());
		
		return result;
	}
}