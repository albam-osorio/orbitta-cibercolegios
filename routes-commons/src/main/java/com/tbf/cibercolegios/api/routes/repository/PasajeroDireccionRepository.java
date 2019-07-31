package com.tbf.cibercolegios.api.routes.repository;

import java.util.List;

import com.tbf.cibercolegios.api.core.data.jpa.repository.IdentifiedDomainObjectRepository;
import com.tbf.cibercolegios.api.model.routes.PasajeroDireccion;

public interface PasajeroDireccionRepository extends IdentifiedDomainObjectRepository<PasajeroDireccion, Integer> {

	List<PasajeroDireccion> findAllByPasajeroId(int pasajeroId);

	List<PasajeroDireccion> findAllByPasajeroIdIn(List<Integer> pasajerosId);

	List<PasajeroDireccion> findAllByRutaId(int rutaId);

	List<PasajeroDireccion> findAllByRutaIdInAndActivoTrue(List<Integer> rutasId);
}