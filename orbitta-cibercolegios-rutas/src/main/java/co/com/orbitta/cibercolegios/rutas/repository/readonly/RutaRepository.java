package co.com.orbitta.cibercolegios.rutas.repository.readonly;

import java.util.List;

import co.com.orbitta.cibercolegios.rutas.domain.readonly.Ruta;
import co.com.orbitta.core.data.jpa.repository.QueryRepository;

public interface RutaRepository extends QueryRepository<Ruta, Integer> {

	List<Ruta> findAllByMonitorId(int monitorId);
}
