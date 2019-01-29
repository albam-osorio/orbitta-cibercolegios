package co.com.orbitta.cibercolegios.rutas.service.api;

import java.util.List;

import co.com.orbitta.cibercolegios.rutas.dto.DireccionDto;
import co.com.orbitta.core.services.crud.api.CrudService;

public interface DireccionService extends CrudService<DireccionDto, Integer> {

	List<DireccionDto> findAllByInstitucionId(int institucionId);
	
}
