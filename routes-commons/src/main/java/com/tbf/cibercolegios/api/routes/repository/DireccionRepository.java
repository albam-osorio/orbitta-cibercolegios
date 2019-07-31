package com.tbf.cibercolegios.api.routes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tbf.cibercolegios.api.core.data.jpa.repository.IdentifiedDomainObjectRepository;
import com.tbf.cibercolegios.api.model.routes.Direccion;

public interface DireccionRepository extends IdentifiedDomainObjectRepository<Direccion, Integer> {
	@Query("SELECT b FROM Direccion b WHERE b.id IN (SELECT DISTINCT a.direccionSedeId FROM Ruta a WHERE a.institucionId = :institucionId) ")
	List<Direccion> findAllByInstitucionId(@Param(value = "institucionId") int institucionId);
}
