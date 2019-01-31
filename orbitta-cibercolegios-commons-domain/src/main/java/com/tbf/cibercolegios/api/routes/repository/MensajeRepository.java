package com.tbf.cibercolegios.api.routes.repository;

import java.util.List;

import com.tbf.cibercolegios.api.model.routes.Mensaje;

import co.com.orbitta.core.data.jpa.repository.IdentifiedDomainObjectRepository;

public interface MensajeRepository extends IdentifiedDomainObjectRepository<Mensaje, Integer> {
	List<Mensaje> findAllByConversacionId(int conversacionId);
}
