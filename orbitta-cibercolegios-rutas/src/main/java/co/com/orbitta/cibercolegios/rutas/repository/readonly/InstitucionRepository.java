package co.com.orbitta.cibercolegios.rutas.repository.readonly;

import co.com.orbitta.cibercolegios.rutas.domain.readonly.Institucion;
import co.com.orbitta.core.data.jpa.repository.QueryRepository;

public interface InstitucionRepository extends QueryRepository<Institucion, Integer> {
}
