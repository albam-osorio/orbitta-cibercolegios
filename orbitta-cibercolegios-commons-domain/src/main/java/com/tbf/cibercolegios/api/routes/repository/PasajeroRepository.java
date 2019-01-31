package com.tbf.cibercolegios.api.routes.repository;

import java.util.List;
import java.util.Optional;

import com.tbf.cibercolegios.api.model.routes.Pasajero;

import co.com.orbitta.core.data.jpa.repository.IdentifiedDomainObjectRepository;

public interface PasajeroRepository extends IdentifiedDomainObjectRepository<Pasajero, Integer> {

	Optional<Pasajero> findByUsuarioId(int usuarioId);

	List<Pasajero> findAllByRutaId(Integer rutaId);

	long countByRutaId(Integer rutaId);
}