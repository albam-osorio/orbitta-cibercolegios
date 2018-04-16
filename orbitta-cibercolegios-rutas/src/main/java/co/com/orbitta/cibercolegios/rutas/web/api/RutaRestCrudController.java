package co.com.orbitta.cibercolegios.rutas.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.orbitta.cibercolegios.constants.RutasRestConstants;
import co.com.orbitta.cibercolegios.dto.RutaDto;
import co.com.orbitta.cibercolegios.rutas.service.api.RutaCrudService;
import co.com.orbitta.core.web.api.controllers.RestCrudController;

@RestController
@RequestMapping(value = RutasRestConstants.ruta, produces = MediaType.APPLICATION_JSON_VALUE)
public class RutaRestCrudController extends RestCrudController<RutaDto, Integer> {

	@Autowired
	private RutaCrudService service;

	@Override
	protected RutaCrudService getService() {
		return service;
	}
}
