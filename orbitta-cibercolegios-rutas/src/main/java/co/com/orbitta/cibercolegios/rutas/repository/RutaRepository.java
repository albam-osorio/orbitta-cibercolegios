package co.com.orbitta.cibercolegios.rutas.repository;

import java.util.Optional;

import co.com.orbitta.cibercolegios.rutas.domain.Ruta;
import co.com.orbitta.core.data.jpa.repository.BaseEntityRepository;

public interface RutaRepository extends BaseEntityRepository<Ruta, Integer> {

	Optional<Ruta> findByMonitorId(int monitorId);

}
