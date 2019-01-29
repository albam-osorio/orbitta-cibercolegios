package co.com.orbitta.cibercolegios.rutas.service.api;

import java.util.List;

import co.com.orbitta.cibercolegios.rutas.dto.EstadoDireccionDto;
import co.com.orbitta.core.services.crud.api.CrudService;

public interface EstadoDireccionService extends CrudService<EstadoDireccionDto, Integer> {

	List<EstadoDireccionDto> findAll();
}
