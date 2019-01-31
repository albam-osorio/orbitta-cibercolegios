package com.tbf.cibercolegios.api.routes.repository;

import java.util.List;
import java.util.Optional;

import com.tbf.cibercolegios.api.model.routes.Acudiente;

import co.com.orbitta.core.data.jpa.repository.IdentifiedDomainObjectRepository;

public interface AcudienteRepository extends IdentifiedDomainObjectRepository<Acudiente, Integer> {
	Optional<Acudiente> findByUsuarioId(int usuarioId);

	List<Acudiente> findAllByUsuarioIdIn(List<Integer> usuarioId);
}
