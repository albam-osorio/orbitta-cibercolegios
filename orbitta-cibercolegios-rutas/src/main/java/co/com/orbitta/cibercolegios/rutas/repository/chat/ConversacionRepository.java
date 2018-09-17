package co.com.orbitta.cibercolegios.rutas.repository.chat;

import java.util.List;
import java.util.Optional;

import co.com.orbitta.cibercolegios.rutas.domain.chat.Conversacion;
import co.com.orbitta.core.data.jpa.repository.IdentifiedDomainObjectRepository;

public interface ConversacionRepository extends IdentifiedDomainObjectRepository<Conversacion, Integer> {
	List<Conversacion> findAllByEstudianteId(int estudianteId);

	Optional<Conversacion> findByAcudienteIdAndEstudianteId(int acudienteId,int estudianteId);
}
