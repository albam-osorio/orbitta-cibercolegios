package co.com.orbitta.cibercolegios.rutas.repository.tracking;

import java.time.LocalDateTime;
import java.util.Optional;

import co.com.orbitta.cibercolegios.rutas.domain.tracking.LogRuta;
import co.com.orbitta.core.data.jpa.repository.IdentifiedDomainObjectRepository;

public interface LogRutaRepository extends IdentifiedDomainObjectRepository<LogRuta, Integer> {
	
	Optional<LogRuta> findFirstByRutaIdAndFechaCreacionGreaterThanOrderByIdDesc(int rutaId, LocalDateTime fechaCreacion);
}
