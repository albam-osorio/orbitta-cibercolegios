package co.com.orbitta.cibercolegios.rutas.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import co.com.orbitta.cibercolegios.rutas.domain.UbicacionRuta;
import co.com.orbitta.core.data.jpa.repository.BaseEntityRepository;

public interface UbicacionRutaRepository extends BaseEntityRepository<UbicacionRuta, Integer> {
	
	Optional<UbicacionRuta> findFirstByRutaIdAndIdGreaterThanOrderById(int rutaId, int id);
	
	Optional<UbicacionRuta> findFirstByRutaIdAndFechaHoraGreaterThanOrderByIdDesc(int rutaId, LocalDateTime fechaHora);
}
