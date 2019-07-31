package com.tbf.cibercolegios.api.routes.repository;

import java.util.Optional;

import com.tbf.cibercolegios.api.core.data.jpa.repository.IdentifiedDomainObjectRepository;
import com.tbf.cibercolegios.api.model.routes.Pasajero;

public interface PasajeroRepository extends IdentifiedDomainObjectRepository<Pasajero, Integer> {

	Optional<Pasajero> findByInstitucionIdAndUsuarioId(int insititucionId, int usuarioId);

}