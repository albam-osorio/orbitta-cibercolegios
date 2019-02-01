package com.tbf.cibercolegios.api.routes.repository;

import java.util.List;

import com.tbf.cibercolegios.api.core.data.jpa.repository.IdentifiedDomainObjectRepository;
import com.tbf.cibercolegios.api.model.routes.Mensaje;

public interface MensajeRepository extends IdentifiedDomainObjectRepository<Mensaje, Integer> {
	List<Mensaje> findAllByConversacionId(int conversacionId);
}
