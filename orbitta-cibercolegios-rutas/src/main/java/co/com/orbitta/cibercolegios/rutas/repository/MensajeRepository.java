package co.com.orbitta.cibercolegios.rutas.repository;

import java.util.List;

import co.com.orbitta.cibercolegios.rutas.domain.Mensaje;
import co.com.orbitta.core.data.jpa.repository.BaseEntityRepository;

public interface MensajeRepository extends BaseEntityRepository<Mensaje, Integer> {
	List<Mensaje> findAllByConversacionId(int conversacionId);
}
