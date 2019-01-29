package co.com.orbitta.cibercolegios.rutas.repository;

import java.util.List;
import java.util.Optional;

import co.com.orbitta.cibercolegios.rutas.domain.Pasajero;
import co.com.orbitta.core.data.jpa.repository.IdentifiedDomainObjectRepository;

public interface PasajeroRepository extends IdentifiedDomainObjectRepository<Pasajero, Integer> {

	Optional<Pasajero> findByUsuarioId(int usuarioId);

	List<Pasajero> findAllByRutaId(Integer rutaId);

	long countByRutaId(Integer rutaId);
}