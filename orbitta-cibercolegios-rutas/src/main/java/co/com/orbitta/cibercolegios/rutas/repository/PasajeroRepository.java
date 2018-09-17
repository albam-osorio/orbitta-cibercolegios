package co.com.orbitta.cibercolegios.rutas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.com.orbitta.cibercolegios.rutas.domain.Pasajero;

public interface PasajeroRepository extends JpaRepository<Pasajero, Integer> {

	List<Pasajero> findAllByRutaId(Integer rutaId);
}
