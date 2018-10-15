package co.com.orbitta.cibercolegios.repository;

import java.util.List;

import co.com.orbitta.cibercolegios.domain.Ruta;
import co.com.orbitta.core.data.jpa.repository.IdentifiedDomainObjectRepository;

public interface RutaRepository extends IdentifiedDomainObjectRepository<Ruta, Integer> {

	List<Ruta> findAllByMonitorId(int monitorId);
	
	List<Ruta> findAllByInstitucionId(int institucionId);
}
