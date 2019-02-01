package com.tbf.cibercolegios.api.routes.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import com.tbf.cibercolegios.api.core.data.jpa.repository.IdentifiedDomainObjectRepository;
import com.tbf.cibercolegios.api.model.routes.LogRuta;

public interface LogRutaRepository extends IdentifiedDomainObjectRepository<LogRuta, Integer> {
	
	Optional<LogRuta> findFirstByRutaIdAndFechaCreacionGreaterThanOrderByIdDesc(int rutaId, LocalDateTime fechaCreacion);
}
