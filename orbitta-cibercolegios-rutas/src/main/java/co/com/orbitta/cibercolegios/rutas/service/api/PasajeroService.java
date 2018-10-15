package co.com.orbitta.cibercolegios.rutas.service.api;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import co.com.orbitta.cibercolegios.rutas.dto.PasajeroDto;
import co.com.orbitta.core.services.crud.api.CrudService;

public interface PasajeroService extends CrudService<PasajeroDto, Integer> {

	@Transactional(readOnly = true)
	Optional<PasajeroDto> findByUsuarioId(int usuarioId);

	@Transactional(readOnly = true)
	List<PasajeroDto> findAllByRutaId(int rutaId);
}