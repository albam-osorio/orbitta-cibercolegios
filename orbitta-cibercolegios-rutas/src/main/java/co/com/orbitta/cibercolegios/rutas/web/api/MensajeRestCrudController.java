package co.com.orbitta.cibercolegios.rutas.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.orbitta.cibercolegios.constants.RutasRestConstants;
import co.com.orbitta.cibercolegios.dto.MensajeDto;
import co.com.orbitta.cibercolegios.rutas.service.api.MensajeCrudService;
import co.com.orbitta.core.web.api.controllers.RestCrudController;

@RestController
@RequestMapping(value = RutasRestConstants.mensaje, produces = MediaType.APPLICATION_JSON_VALUE)
public class MensajeRestCrudController extends RestCrudController<MensajeDto, Integer> {

	@Autowired
	private MensajeCrudService service;

	@Override
	protected MensajeCrudService getService() {
		return service;
	}
}
