package com.tbf.cibercolegios.api.routes.repository;

import java.util.List;
import java.util.Optional;

import com.tbf.cibercolegios.api.model.routes.Conversacion;

import co.com.orbitta.core.data.jpa.repository.IdentifiedDomainObjectRepository;

public interface ConversacionRepository extends IdentifiedDomainObjectRepository<Conversacion, Integer> {

	Optional<Conversacion> findByRutaIdAndUsuarioAcudienteIdAndUsuarioPasajeroId(int rutaId, int usuarioAcudienteId, int usuarioPasajeroId);

	List<Conversacion> findByRutaId(int rutaId);
}
