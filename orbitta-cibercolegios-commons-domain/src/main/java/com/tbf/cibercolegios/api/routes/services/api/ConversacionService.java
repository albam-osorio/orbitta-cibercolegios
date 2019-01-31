package com.tbf.cibercolegios.api.routes.services.api;

import java.util.List;
import java.util.Optional;

import com.tbf.cibercolegios.api.routes.model.graph.ConversacionDto;
import com.tbf.cibercolegios.api.routes.model.graph.chat.DatosConversacionDto;

import co.com.orbitta.core.services.crud.api.CrudService;

public interface ConversacionService extends CrudService<ConversacionDto, Integer> {

	Optional<DatosConversacionDto> findByRutaIdAndUsuarioAcudienteIdAndUsuarioPasajeroId(int rutaId, int acudienteId,
			int pasajeroId);

	List<DatosConversacionDto> findByRutaId(int rutaId);
}
