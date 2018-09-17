package co.com.orbitta.cibercolegios.rutas.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.orbitta.cibercolegios.rutas.constants.RutasRestConstants;
import co.com.orbitta.cibercolegios.rutas.dto.LogPasajeroDto;
import co.com.orbitta.cibercolegios.rutas.service.api.LogPasajeroCrudService;
import co.com.orbitta.core.web.api.controllers.CrudRestController;

@RestController
@RequestMapping(value = RutasRestConstants.logPasajero, produces = MediaType.APPLICATION_JSON_VALUE)
public class LogPasajeroRestCrudController extends CrudRestController<LogPasajeroDto, Integer> {

	@Autowired
	private LogPasajeroCrudService service;

	@Override
	protected LogPasajeroCrudService getService() {
		return service;
	}
}
