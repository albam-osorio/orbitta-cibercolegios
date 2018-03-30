package co.com.orbitta.cibercolegios.rutas.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.orbitta.cibercolegios.constants.RestConstants;
import co.com.orbitta.cibercolegios.dto.MensajeRutaDto;
import co.com.orbitta.cibercolegios.rutas.service.api.MensajeRutaCrudService;
import co.com.orbitta.core.web.api.controllers.RestCrudController;

@RestController
@RequestMapping(value = RestConstants.mensajeRuta, produces = MediaType.APPLICATION_JSON_VALUE)
public class MensajeRutaRestCrudController extends RestCrudController<MensajeRutaDto, MensajeRutaDto, Integer> {

	@Autowired
	private MensajeRutaCrudService service;

	@Override
	protected MensajeRutaCrudService getService() {
		return service;
	}
}
