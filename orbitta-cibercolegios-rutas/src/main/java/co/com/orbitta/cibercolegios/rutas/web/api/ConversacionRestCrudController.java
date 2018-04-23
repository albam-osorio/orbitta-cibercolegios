package co.com.orbitta.cibercolegios.rutas.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.orbitta.cibercolegios.constants.RutasRestConstants;
import co.com.orbitta.cibercolegios.dto.ConversacionDto;
import co.com.orbitta.cibercolegios.rutas.service.api.ConversacionCrudService;
import co.com.orbitta.core.web.api.controllers.RestCrudController;

@RestController
@RequestMapping(value = RutasRestConstants.conversacion, produces = MediaType.APPLICATION_JSON_VALUE)
public class ConversacionRestCrudController extends RestCrudController<ConversacionDto, Integer> {

	@Autowired
	private ConversacionCrudService service;

	@Override
	protected ConversacionCrudService getService() {
		return service;
	}
}
