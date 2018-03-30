package co.com.orbitta.cibercolegios.rutas.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.orbitta.cibercolegios.constants.RestConstants;
import co.com.orbitta.cibercolegios.dto.LogDto;
import co.com.orbitta.cibercolegios.rutas.service.api.LogCrudService;
import co.com.orbitta.core.web.api.controllers.RestCrudController;

@RestController
@RequestMapping(value = RestConstants.log, produces = MediaType.APPLICATION_JSON_VALUE)
public class LogRestCrudController extends RestCrudController<LogDto, LogDto, Integer> {

	@Autowired
	private LogCrudService service;

	@Override
	protected LogCrudService getService() {
		return service;
	}
}
