package co.com.orbitta.cibercolegios.rutas.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import co.com.orbitta.cibercolegios.rutas.domain.LogRuta;
import co.com.orbitta.core.data.jpa.repository.BaseEntityRepository;

public interface LogRutaRepository extends BaseEntityRepository<LogRuta, Integer> {
	
	Optional<LogRuta> findFirstByRutaIdAndFechaHoraGreaterThanOrderByIdDesc(int rutaId, LocalDateTime fechaHora);
}
