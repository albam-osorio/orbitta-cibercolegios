package com.tbf.cibercolegios.api.routes.repository;

import java.util.List;

import com.tbf.cibercolegios.api.core.data.jpa.repository.IdentifiedDomainObjectRepository;
import com.tbf.cibercolegios.api.model.routes.PasajeroAcudiente;

public interface PasajeroAcudienteRepository extends IdentifiedDomainObjectRepository<PasajeroAcudiente, Integer> {

	List<PasajeroAcudiente> findAllByPasajeroId(int pasajeroId);

	List<PasajeroAcudiente> findAllByAcudienteId(int acudienteId);
}