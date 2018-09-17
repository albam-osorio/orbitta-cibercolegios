package co.com.orbitta.cibercolegios.rutas.service.api;


import java.util.List;

import co.com.orbitta.cibercolegios.rutas.dto.EstadoPasajeroDto;
import co.com.orbitta.core.services.crud.api.CrudService;

public interface EstadoPasajeroCrudService extends CrudService<EstadoPasajeroDto, Integer> {
	
	List<EstadoPasajeroDto> findAll();
	
	EstadoPasajeroDto findEstadoInicioPredeterminado();

}
