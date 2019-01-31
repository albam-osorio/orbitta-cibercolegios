package com.tbf.cibercolegios.api.routes.repository;

import java.util.Optional;

import com.tbf.cibercolegios.api.model.routes.EstadoPasajero;
import com.tbf.cibercolegios.api.routes.model.enums.PassengerTypeStatus;

import co.com.orbitta.core.data.jpa.repository.IdentifiedDomainObjectRepository;

public interface EstadoPasajeroRepository extends IdentifiedDomainObjectRepository<EstadoPasajero, Integer> {
	Optional<EstadoPasajero> findFirstByTipo(PassengerTypeStatus tipo);
}