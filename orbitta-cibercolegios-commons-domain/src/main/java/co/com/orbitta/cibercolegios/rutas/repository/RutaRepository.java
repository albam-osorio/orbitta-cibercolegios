package co.com.orbitta.cibercolegios.rutas.repository;

import java.util.List;

import co.com.orbitta.cibercolegios.rutas.domain.Ruta;
import co.com.orbitta.core.data.jpa.repository.IdentifiedDomainObjectRepository;

public interface RutaRepository extends IdentifiedDomainObjectRepository<Ruta, Integer> {

	List<Ruta> findAllByMonitorId(int monitorId);

	List<Ruta> findAllByInstitucionId(int institucionId);
}
