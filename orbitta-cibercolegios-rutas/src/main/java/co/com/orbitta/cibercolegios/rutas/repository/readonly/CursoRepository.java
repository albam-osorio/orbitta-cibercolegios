package co.com.orbitta.cibercolegios.rutas.repository.readonly;

import org.springframework.data.jpa.repository.JpaRepository;

import co.com.orbitta.cibercolegios.rutas.domain.readonly.Curso;

public interface CursoRepository extends JpaRepository<Curso, Integer> {
}
