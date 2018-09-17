package co.com.orbitta.cibercolegios.rutas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.com.orbitta.cibercolegios.rutas.domain.Direccion;

public interface DireccionRepository extends JpaRepository<Direccion, Integer> {
}
