package co.com.orbitta.cibercolegios.rutas.repository;

import java.util.Optional;

import co.com.orbitta.cibercolegios.rutas.domain.EstadoPasajero;
import co.com.orbitta.cibercolegios.rutas.enums.TipoEstadoPasajeroEnum;
import co.com.orbitta.core.data.jpa.repository.IdentifiedDomainObjectRepository;

public interface EstadoPasajeroRepository extends IdentifiedDomainObjectRepository<EstadoPasajero, Integer> {
	Optional<EstadoPasajero> findFirstByTipo(TipoEstadoPasajeroEnum tipo);
}
