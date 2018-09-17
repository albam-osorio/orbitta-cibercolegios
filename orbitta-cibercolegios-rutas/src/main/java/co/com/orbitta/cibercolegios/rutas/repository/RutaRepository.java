package co.com.orbitta.cibercolegios.rutas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.com.orbitta.cibercolegios.rutas.domain.Ruta;

public interface RutaRepository extends JpaRepository<Ruta, Integer> {

	List<Ruta> findAllByMonitorId(int monitorId);
}
