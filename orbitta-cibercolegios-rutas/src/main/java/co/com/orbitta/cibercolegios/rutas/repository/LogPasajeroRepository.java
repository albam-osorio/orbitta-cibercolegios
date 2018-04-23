package co.com.orbitta.cibercolegios.rutas.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;

import co.com.orbitta.cibercolegios.rutas.domain.LogPasajero;
import co.com.orbitta.core.data.jpa.repository.BaseEntityRepository;

public interface LogPasajeroRepository extends BaseEntityRepository<LogPasajero, Integer> {
	List<LogPasajero> findByPasajeroIdAndFechaHoraGreaterThanAndSentidoOrderByFechaHoraDesc(int pasajeroId,
			LocalDateTime fechaHora, int sentido);

	@Query(value = " SELECT MAX(ID_RUTA_LOG_PASAJERO) FROM ORBITTA.RUTA_LOG_PASAJERO a WHERE a.ID_RUTA = :rutaId AND a.FECHA_HORA > :fecha GROUP BY ID_USUARIO_RUTA ", nativeQuery = true)
	List<Integer> findAllCurrentByRutaId(int rutaId, LocalDateTime fecha);

	@Query(value = " SELECT MAX(ID_RUTA_LOG_PASAJERO) FROM ORBITTA.RUTA_LOG_PASAJERO a WHERE a.ID_RUTA = :rutaId AND a.ID_USUARIO_RUTA = :pasajeroId AND a.FECHA_HORA > :fecha ", nativeQuery = true)
	Optional<Integer> findCurrentByRutaIdAndPasajeroId(int rutaId, int pasajeroId, LocalDateTime fecha);

}
