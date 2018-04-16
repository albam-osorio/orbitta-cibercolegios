package co.com.orbitta.cibercolegios.rutas.repository;

import java.util.List;
import java.util.Optional;

import co.com.orbitta.cibercolegios.enums.TipoEstadoRutaEnum;
import co.com.orbitta.cibercolegios.rutas.domain.EstadoRuta;
import co.com.orbitta.core.data.jpa.repository.BaseEntityRepository;

public interface EstadoRutaRepository extends BaseEntityRepository<EstadoRuta, Integer> {

	List<EstadoRuta> findAllByTipoOrderByDescripcion(TipoEstadoRutaEnum tipoEvento);

	Optional<EstadoRuta> findByDescripcionIgnoreCase(String descripcion);

}
