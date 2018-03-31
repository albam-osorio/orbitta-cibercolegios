package co.com.orbitta.cibercolegios.rutas.repository;

import java.util.List;
import java.util.Optional;

import co.com.orbitta.cibercolegios.enums.TipoEventoEnum;
import co.com.orbitta.cibercolegios.rutas.domain.Estado;
import co.com.orbitta.core.data.jpa.repository.BaseEntityRepository;

public interface EstadoRepository extends BaseEntityRepository<Estado, Integer> {

	List<Estado> findAllByTipoEventoOrderByDescripcion(TipoEventoEnum tipoEvento);

	Optional<Estado> findByDescripcionIgnoreCase(String descripcion);

}
