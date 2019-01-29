package co.com.orbitta.cibercolegios.rutas.repository;

import java.util.List;
import java.util.Optional;

import co.com.orbitta.cibercolegios.rutas.domain.EstadoRuta;
import co.com.orbitta.cibercolegios.rutas.enums.TipoEstadoRutaEnum;
import co.com.orbitta.core.data.jpa.repository.IdentifiedDomainObjectRepository;

public interface EstadoRutaRepository extends IdentifiedDomainObjectRepository<EstadoRuta, Integer> {

	List<EstadoRuta> findAllByTipoOrderByDescripcion(TipoEstadoRutaEnum tipoEvento);

	Optional<EstadoRuta> findFirstByTipoAndPredeterminado(TipoEstadoRutaEnum tipo, boolean predeterminado);
}