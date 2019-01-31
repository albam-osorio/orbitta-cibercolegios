package com.tbf.cibercolegios.api.routes.repository;

import java.util.List;

import com.tbf.cibercolegios.api.model.routes.Ruta;

import co.com.orbitta.core.data.jpa.repository.IdentifiedDomainObjectRepository;

public interface RutaRepository extends IdentifiedDomainObjectRepository<Ruta, Integer> {

	List<Ruta> findAllByMonitorId(int monitorId);

	List<Ruta> findAllByInstitucionId(int institucionId);
}
