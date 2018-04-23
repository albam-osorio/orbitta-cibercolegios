package co.com.orbitta.cibercolegios.rutas.service.api;

import co.com.orbitta.cibercolegios.dto.EstadoPasajeroDto;
import co.com.orbitta.core.services.crud.api.CrudService;

public interface EstadoPasajeroCrudService extends CrudService<EstadoPasajeroDto, Integer> {

	EstadoPasajeroDto findEstadoInicioPredeterminado();

}
