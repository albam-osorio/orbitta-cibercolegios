package co.com.orbitta.cibercolegios.rutas.repository.chat;

import java.util.List;
import java.util.Optional;

import co.com.orbitta.cibercolegios.rutas.domain.chat.Conversacion;
import co.com.orbitta.core.data.jpa.repository.IdentifiedDomainObjectRepository;

public interface ConversacionRepository extends IdentifiedDomainObjectRepository<Conversacion, Integer> {

	Optional<Conversacion> findByRutaIdAndUsuarioAcudienteIdAndUsuarioPasajeroId(int rutaId, int usuarioAcudienteId, int usuarioPasajeroId);

	List<Conversacion> findByRutaId(int rutaId);
}
