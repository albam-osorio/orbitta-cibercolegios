package co.com.orbitta.cibercolegios.ciber.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.com.orbitta.cibercolegios.ciber.domain.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
}
