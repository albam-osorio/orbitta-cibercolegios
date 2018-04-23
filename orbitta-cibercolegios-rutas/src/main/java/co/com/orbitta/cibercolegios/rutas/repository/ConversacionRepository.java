package co.com.orbitta.cibercolegios.rutas.repository;

import java.util.List;
import java.util.Optional;

import co.com.orbitta.cibercolegios.rutas.domain.Conversacion;
import co.com.orbitta.core.data.jpa.repository.BaseEntityRepository;

public interface ConversacionRepository extends BaseEntityRepository<Conversacion, Integer> {
	List<Conversacion> findAllByEstudianteId(int estudianteId);

	Optional<Conversacion> findByAcudienteIdAndEstudianteId(int acudienteId,int estudianteId);
}
