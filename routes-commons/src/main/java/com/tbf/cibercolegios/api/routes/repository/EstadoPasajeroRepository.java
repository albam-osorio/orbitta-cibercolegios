package com.tbf.cibercolegios.api.routes.repository;

import java.util.Optional;

import com.tbf.cibercolegios.api.core.data.jpa.repository.IdentifiedDomainObjectRepository;
import com.tbf.cibercolegios.api.model.routes.EstadoPasajero;
import com.tbf.cibercolegios.api.model.routes.enums.PassengerTypeStatus;

public interface EstadoPasajeroRepository extends IdentifiedDomainObjectRepository<EstadoPasajero, Integer> {
	Optional<EstadoPasajero> findFirstByTipo(PassengerTypeStatus tipo);
}