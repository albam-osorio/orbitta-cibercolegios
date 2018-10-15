package co.com.orbitta.cibercolegios.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.com.orbitta.cibercolegios.domain.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
}
