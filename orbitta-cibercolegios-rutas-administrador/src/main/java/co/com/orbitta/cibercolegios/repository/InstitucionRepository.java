package co.com.orbitta.cibercolegios.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.com.orbitta.cibercolegios.domain.Institucion;

public interface InstitucionRepository extends JpaRepository<Institucion, Integer> {
}
