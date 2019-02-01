package com.tbf.cibercolegios.api.routes.services.api;

import java.util.List;
import java.util.Optional;

import com.tbf.cibercolegios.api.core.services.api.CrudService;
import com.tbf.cibercolegios.api.routes.model.graph.ConversacionDto;
import com.tbf.cibercolegios.api.routes.model.graph.chat.DatosConversacionDto;

public interface ConversacionService extends CrudService<ConversacionDto, Integer> {

	Optional<DatosConversacionDto> findByRutaIdAndUsuarioAcudienteIdAndUsuarioPasajeroId(int rutaId, int acudienteId,
			int pasajeroId);

	List<DatosConversacionDto> findByRutaId(int rutaId);
}
