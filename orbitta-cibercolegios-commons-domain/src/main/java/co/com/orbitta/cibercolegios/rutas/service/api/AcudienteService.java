package co.com.orbitta.cibercolegios.rutas.service.api;

import java.util.Optional;

import co.com.orbitta.cibercolegios.rutas.dto.AcudienteDto;
import co.com.orbitta.core.services.crud.api.CrudService;

public interface AcudienteService extends CrudService<AcudienteDto, Integer> {

	Optional<AcudienteDto> findByUsuarioId(int usuarioId);
}
