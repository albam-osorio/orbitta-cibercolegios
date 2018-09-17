package co.com.orbitta.cibercolegios.rutas.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.orbitta.cibercolegios.rutas.constants.RutasRestConstants;
import co.com.orbitta.cibercolegios.rutas.dto.chat.MensajeDto;
import co.com.orbitta.cibercolegios.rutas.service.api.chat.MensajeCrudService;
import co.com.orbitta.core.web.api.controllers.CrudRestController;

@RestController
@RequestMapping(value = RutasRestConstants.mensaje, produces = MediaType.APPLICATION_JSON_VALUE)
public class MensajeRestCrudController extends CrudRestController<MensajeDto, Integer> {

	@Autowired
	private MensajeCrudService service;

	@Override
	protected MensajeCrudService getService() {
		return service;
	}
}
