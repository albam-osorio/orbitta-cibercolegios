package co.com.orbitta.cibercolegios.rutas.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.dto.ConversacionDto;
import co.com.orbitta.cibercolegios.rutas.domain.Conversacion;
import co.com.orbitta.cibercolegios.rutas.repository.ConversacionRepository;
import co.com.orbitta.cibercolegios.rutas.repository.readonly.UsuarioRepository;
import co.com.orbitta.cibercolegios.rutas.service.api.ConversacionCrudService;
import co.com.orbitta.core.services.crud.impl.CrudServiceImpl;
import lombok.val;

@Service
public class ConversacionCrudServiceImpl extends CrudServiceImpl<Conversacion, ConversacionDto, Integer>
		implements ConversacionCrudService {

	@Autowired
	private ConversacionRepository repository;

	@Autowired
	private UsuarioRepository usuarioRepository;

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
				.acudienteId(entity.getAcudiente().getId())
				.estudianteId(entity.getEstudiante().getId())

				.build();
		// @formatter:on
		return result;
	}

	@Override
	protected Conversacion asEntity(ConversacionDto model, Conversacion entity) {
		val acudiente = usuarioRepository.findById(model.getAcudienteId());
		val estudiante = usuarioRepository.findById(model.getEstudianteId());

		entity.setAcudiente(acudiente.get());
		entity.setEstudiante(estudiante.get());

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