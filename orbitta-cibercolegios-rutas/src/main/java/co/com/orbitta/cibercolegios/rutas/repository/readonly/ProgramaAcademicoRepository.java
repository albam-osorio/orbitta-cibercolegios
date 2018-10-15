package co.com.orbitta.cibercolegios.rutas.repository.readonly;

import org.springframework.data.jpa.repository.JpaRepository;

import co.com.orbitta.cibercolegios.rutas.domain.readonly.ProgramaAcademico;

public interface ProgramaAcademicoRepository extends JpaRepository<ProgramaAcademico, Integer> {
}
