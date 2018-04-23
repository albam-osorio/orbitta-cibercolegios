package co.com.orbitta.cibercolegios.rutas.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.orbitta.cibercolegios.constants.RutasRestConstants;
import co.com.orbitta.cibercolegios.dto.EstadoPasajeroDto;
import co.com.orbitta.cibercolegios.rutas.service.api.EstadoPasajeroCrudService;
import co.com.orbitta.core.web.api.controllers.RestCrudController;

@RestController
@RequestMapping(value = RutasRestConstants.estadoPasajero, produces = MediaType.APPLICATION_JSON_VALUE)
public class EstadoPasajeroRestCrudController extends RestCrudController<EstadoPasajeroDto, Integer> {

	@Autowired
	private EstadoPasajeroCrudService service;

	@Override
	protected EstadoPasajeroCrudService getService() {
		return service;
	}
}
