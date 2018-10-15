package co.com.orbitta.cibercolegios.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.com.orbitta.cibercolegios.domain.Direccion;

public interface DireccionRepository extends JpaRepository<Direccion, Integer> {
}
