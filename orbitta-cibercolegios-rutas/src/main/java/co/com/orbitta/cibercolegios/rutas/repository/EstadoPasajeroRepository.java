package co.com.orbitta.cibercolegios.rutas.repository;

import java.util.Optional;

import co.com.orbitta.cibercolegios.enums.TipoEstadoPasajeroEnum;
import co.com.orbitta.cibercolegios.rutas.domain.EstadoPasajero;
import co.com.orbitta.core.data.jpa.repository.BaseEntityRepository;

public interface EstadoPasajeroRepository extends BaseEntityRepository<EstadoPasajero, Integer> {
	Optional<EstadoPasajero> findFirstByTipo(TipoEstadoPasajeroEnum tipo);
}
