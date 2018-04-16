package co.com.orbitta.cibercolegios.rutas.repository;

import java.time.LocalDateTime;
import java.util.List;

import co.com.orbitta.cibercolegios.rutas.domain.UsuarioRuta;
import co.com.orbitta.core.data.jpa.repository.BaseEntityRepository;

public interface UsuarioRutaRepository extends BaseEntityRepository<UsuarioRuta, Integer> {

	List<UsuarioRuta> findAllByRutaIdAndFechaHora(Integer id, LocalDateTime fechaHora);
}
