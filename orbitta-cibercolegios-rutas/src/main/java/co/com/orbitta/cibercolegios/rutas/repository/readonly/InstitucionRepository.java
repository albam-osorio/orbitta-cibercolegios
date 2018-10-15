package co.com.orbitta.cibercolegios.rutas.repository.readonly;

import org.springframework.data.jpa.repository.JpaRepository;

import co.com.orbitta.cibercolegios.rutas.domain.readonly.Institucion;

public interface InstitucionRepository extends JpaRepository<Institucion, Integer> {
}
