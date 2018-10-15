package co.com.orbitta.cibercolegios.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.com.orbitta.cibercolegios.domain.Pasajero;

public interface PasajeroRepository extends JpaRepository<Pasajero, Integer> {

	List<Pasajero> findAllByUsuarioId(Integer usuarioId);
	
	List<Pasajero> findAllByRutaId(Integer rutaId);
}
