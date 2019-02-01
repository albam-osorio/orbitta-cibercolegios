package com.tbf.cibercolegios.api.routes.repository;

import java.util.List;
import java.util.Optional;

import com.tbf.cibercolegios.api.core.data.jpa.repository.IdentifiedDomainObjectRepository;
import com.tbf.cibercolegios.api.model.routes.EstadoRuta;
import com.tbf.cibercolegios.api.model.routes.enums.RouteTypeStatus;

public interface EstadoRutaRepository extends IdentifiedDomainObjectRepository<EstadoRuta, Integer> {

	List<EstadoRuta> findAllByTipoOrderByDescripcion(RouteTypeStatus tipoEvento);

	Optional<EstadoRuta> findFirstByTipoAndPredeterminado(RouteTypeStatus tipo, boolean predeterminado);
}