package co.com.orbitta.cibercolegios.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;

import co.com.orbitta.cibercolegios.domain.LogPasajero;
import co.com.orbitta.core.data.jpa.repository.IdentifiedDomainObjectRepository;

public interface LogPasajeroRepository extends IdentifiedDomainObjectRepository<LogPasajero, Integer> {
	List<LogPasajero> findByPasajeroIdAndFechaHoraGreaterThanAndSentidoOrderByFechaHoraDesc(int pasajeroId,
			LocalDateTime fechaHora, int sentido);

	// @formatter:off
	@Query(value = " " + 
			" SELECT " + 
			"	MAX(a.ID_LOG_PASAJERO) " + 
			" FROM LOGS_X_PASAJERO a " + 
			" INNER JOIN USUARIOS_X_RUTA b ON " + 
			"	b.ID_USUARIOS_X_RUTA = a.ID_USUARIO_RUTA " + 
			" WHERE b.ID_RUTA = :rutaId " + 
			" AND a.FECHA_HORA > :fecha " + 
			" GROUP BY " + 
			"	a.ID_USUARIO_RUTA "
			, nativeQuery = true)
	List<BigDecimal> findAllMaxIdByRutaIdAndFechaHoraGreaterThan(int rutaId, LocalDateTime fecha);
	// @formatter:on

	// @formatter:off
	@Query(value = " " + 
			" SELECT " + 
			"	MAX(a.ID_LOG_PASAJERO) " + 
			" FROM LOGS_X_PASAJERO a " + 
			" INNER JOIN USUARIOS_X_RUTA b ON " + 
			"	b.ID_USUARIOS_X_RUTA = a.ID_USUARIO_RUTA " + 
			" WHERE b.ID_RUTA = :rutaId " + 
			" AND a.FECHA_HORA > :fecha " + 
			" AND a.ID_USUARIO_RUTA = :pasajeroId " +
			" GROUP BY " + 
			"	a.ID_USUARIO_RUTA "
			, nativeQuery = true)
	Optional<BigDecimal> findAllMaxIdByRutaIdAndFechaGreaterThanAndPasajeroId(int rutaId, int pasajeroId, LocalDateTime fecha);
	// @formatter:on
}
