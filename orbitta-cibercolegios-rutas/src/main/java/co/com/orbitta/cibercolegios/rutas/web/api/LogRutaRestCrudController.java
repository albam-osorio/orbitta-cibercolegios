package co.com.orbitta.cibercolegios.rutas.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.orbitta.cibercolegios.rutas.constants.RutasRestConstants;
import co.com.orbitta.cibercolegios.rutas.dto.LogRutaDto;
import co.com.orbitta.cibercolegios.rutas.service.api.LogRutaCrudService;
import co.com.orbitta.core.web.api.controllers.CrudRestController;

@RestController
@RequestMapping(value = RutasRestConstants.logRuta, produces = MediaType.APPLICATION_JSON_VALUE)
public class LogRutaRestCrudController extends CrudRestController<LogRutaDto, Integer> {

	@Autowired
	private LogRutaCrudService service;

	@Override
	protected LogRutaCrudService getService() {
		return service;
	}
}
