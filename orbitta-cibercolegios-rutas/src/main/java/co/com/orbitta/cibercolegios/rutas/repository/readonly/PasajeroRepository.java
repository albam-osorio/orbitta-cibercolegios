package co.com.orbitta.cibercolegios.rutas.repository.readonly;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import co.com.orbitta.cibercolegios.rutas.domain.readonly.Pasajero;
import co.com.orbitta.core.data.jpa.repository.QueryRepository;

public interface PasajeroRepository extends QueryRepository<Pasajero, Integer> {

	@Query(value = " SELECT MAX(a.ID_USUARIO_RUTA) FROM USUARIO_RUTA a WHERE a.ID_RUTA = :rutaId GROUP BY a.ID_USUARIO", nativeQuery = true)
	List<Integer> findAllCurrentByRutaId(Integer rutaId);
}
