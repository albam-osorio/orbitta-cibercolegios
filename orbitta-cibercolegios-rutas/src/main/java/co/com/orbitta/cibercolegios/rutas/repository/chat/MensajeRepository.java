package co.com.orbitta.cibercolegios.rutas.repository.chat;

import java.util.List;

import co.com.orbitta.cibercolegios.rutas.domain.chat.Mensaje;
import co.com.orbitta.core.data.jpa.repository.IdentifiedDomainObjectRepository;

public interface MensajeRepository extends IdentifiedDomainObjectRepository<Mensaje, Integer> {
	List<Mensaje> findAllByConversacionId(int conversacionId);
}
