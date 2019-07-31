package com.tbf.cibercolegios.api.routes.repository;

import java.util.List;

import com.tbf.cibercolegios.api.core.data.jpa.repository.IdentifiedDomainObjectRepository;
import com.tbf.cibercolegios.api.model.routes.Ruta;

public interface RutaRepository extends IdentifiedDomainObjectRepository<Ruta, Integer> {

	List<Ruta> findAllByInstitucionIdAndMonitorId(int institucionId, int monitorId);

	List<Ruta> findAllByInstitucionId(int institucionId);
}
