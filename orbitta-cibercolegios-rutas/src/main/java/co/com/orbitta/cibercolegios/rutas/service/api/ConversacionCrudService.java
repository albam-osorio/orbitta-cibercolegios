package co.com.orbitta.cibercolegios.rutas.service.api;

import java.util.List;
import java.util.Optional;

import co.com.orbitta.cibercolegios.dto.ConversacionDto;
import co.com.orbitta.core.services.crud.api.CrudService;

public interface ConversacionCrudService extends CrudService<ConversacionDto, Integer> {

	List<ConversacionDto> findAllByEstudianteId(int estudianteId);

	Optional<ConversacionDto> findByAcudienteIdAndEstudianteId(int acudienteId, int estudianteId);
}
