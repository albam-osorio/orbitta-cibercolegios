package co.com.orbitta.cibercolegios.rutas.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import co.com.orbitta.cibercolegios.rutas.domain.LogRuta;
import co.com.orbitta.core.data.jpa.repository.IdentifiedDomainObjectRepository;

public interface LogRutaRepository extends IdentifiedDomainObjectRepository<LogRuta, Integer> {
	
	Optional<LogRuta> findFirstByRutaIdAndFechaHoraGreaterThanOrderByIdDesc(int rutaId, LocalDateTime fechaHora);
}
