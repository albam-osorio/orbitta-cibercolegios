package co.com.orbitta.cibercolegios.rutas.service.api;

import java.util.List;
import java.util.Optional;

import co.com.orbitta.cibercolegios.rutas.dto.ConversacionDto;
import co.com.orbitta.cibercolegios.rutas.dto.chat.DatosConversacionDto;
import co.com.orbitta.core.services.crud.api.CrudService;

public interface ConversacionService extends CrudService<ConversacionDto, Integer> {

	Optional<DatosConversacionDto> findByRutaIdAndUsuarioAcudienteIdAndUsuarioPasajeroId(int rutaId, int acudienteId,
			int pasajeroId);

	List<DatosConversacionDto> findByRutaId(int rutaId);
}
