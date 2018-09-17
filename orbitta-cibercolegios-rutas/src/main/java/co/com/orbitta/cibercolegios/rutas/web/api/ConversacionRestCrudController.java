package co.com.orbitta.cibercolegios.rutas.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.orbitta.cibercolegios.rutas.constants.RutasRestConstants;
import co.com.orbitta.cibercolegios.rutas.dto.chat.ConversacionDto;
import co.com.orbitta.cibercolegios.rutas.service.api.chat.ConversacionCrudService;
import co.com.orbitta.core.web.api.controllers.CrudRestController;

@RestController
@RequestMapping(value = RutasRestConstants.conversacion, produces = MediaType.APPLICATION_JSON_VALUE)
public class ConversacionRestCrudController extends CrudRestController<ConversacionDto, Integer> {

	@Autowired
	private ConversacionCrudService service;

	@Override
	protected ConversacionCrudService getService() {
		return service;
	}
}
