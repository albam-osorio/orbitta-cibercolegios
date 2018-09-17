package co.com.orbitta.cibercolegios.rutas.service.impl.chat;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.rutas.domain.chat.Conversacion;
import co.com.orbitta.cibercolegios.rutas.dto.chat.ConversacionDto;
import co.com.orbitta.cibercolegios.rutas.repository.chat.ConversacionRepository;
import co.com.orbitta.cibercolegios.rutas.service.api.chat.ConversacionCrudService;
import co.com.orbitta.core.services.crud.impl.CrudServiceImpl;
import lombok.val;

@Service
public class ConversacionCrudServiceImpl extends CrudServiceImpl<Conversacion, ConversacionDto, Integer>
		implements ConversacionCrudService {

	@Autowired
	private ConversacionRepository repository;

	@Override
	protected ConversacionRepository getRepository() {
		return repository;
	}

	@Override
	public ConversacionDto asModel(Conversacion entity) {

		// @formatter:off
		val result = ConversacionDto
				.builder()
				.id(entity.getId())
				.acudienteId(entity.getAcudienteId())
				.estudianteId(entity.getEstudianteId())
				.build();
		// @formatter:on
		return result;
	}

	@Override
	protected Conversacion mergeEntity(ConversacionDto model, Conversacion entity) {
		entity.setAcudienteId(model.getAcudienteId());
		entity.setEstudianteId(model.getEstudianteId());

		return entity;
	}

	@Override
	protected Conversacion newEntity() {
		return new Conversacion();
	}

	@Override
	public List<ConversacionDto> findAllByEstudianteId(int estudianteId) {
		val entities = getRepository().findAllByEstudianteId(estudianteId);

		val result = asModels(entities);
		return result;
	}

	@Override
	public Optional<ConversacionDto> findByAcudienteIdAndEstudianteId(int acudienteId, int estudianteId) {
		val optional = getRepository().findByAcudienteIdAndEstudianteId(acudienteId, estudianteId);
		
		val result = asModel(optional);
		return result;
	}
}