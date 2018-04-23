package co.com.orbitta.cibercolegios.rutas.repository.readonly;

import co.com.orbitta.cibercolegios.rutas.domain.readonly.Usuario;
import co.com.orbitta.core.data.jpa.repository.QueryRepository;

public interface UsuarioRepository extends QueryRepository<Usuario, Integer> {
}
