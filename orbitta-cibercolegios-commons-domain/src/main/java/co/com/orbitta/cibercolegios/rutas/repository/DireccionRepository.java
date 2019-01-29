package co.com.orbitta.cibercolegios.rutas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.com.orbitta.cibercolegios.rutas.domain.Direccion;
import co.com.orbitta.core.data.jpa.repository.IdentifiedDomainObjectRepository;

public interface DireccionRepository extends IdentifiedDomainObjectRepository<Direccion, Integer> {
	@Query("SELECT DISTINCT b FROM Ruta a JOIN a.direccionSede b WHERE a.institucionId = :institucionId")
	List<Direccion> findAllByInstitucionId(@Param(value = "institucionId") int institucionId);
}
