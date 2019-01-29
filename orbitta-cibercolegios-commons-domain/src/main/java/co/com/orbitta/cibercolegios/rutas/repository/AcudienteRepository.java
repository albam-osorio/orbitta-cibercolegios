package co.com.orbitta.cibercolegios.rutas.repository;

import java.util.List;
import java.util.Optional;

import co.com.orbitta.cibercolegios.rutas.domain.Acudiente;
import co.com.orbitta.core.data.jpa.repository.IdentifiedDomainObjectRepository;

public interface AcudienteRepository extends IdentifiedDomainObjectRepository<Acudiente, Integer> {
	Optional<Acudiente> findByUsuarioId(int usuarioId);

	List<Acudiente> findAllByUsuarioIdIn(List<Integer> usuarioId);
}
