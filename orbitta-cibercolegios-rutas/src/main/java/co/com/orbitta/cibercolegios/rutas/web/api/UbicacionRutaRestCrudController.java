package co.com.orbitta.cibercolegios.rutas.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.orbitta.cibercolegios.constants.RestConstants;
import co.com.orbitta.cibercolegios.dto.UbicacionRutaDto;
import co.com.orbitta.cibercolegios.rutas.service.api.UbicacionRutaCrudService;
import co.com.orbitta.core.web.api.controllers.RestCrudController;

@RestController
@RequestMapping(value = RestConstants.ubicacionRuta, produces = MediaType.APPLICATION_JSON_VALUE)
public class UbicacionRutaRestCrudController extends RestCrudController<UbicacionRutaDto, UbicacionRutaDto, Integer> {

	@Autowired
	private UbicacionRutaCrudService service;

	@Override
	protected UbicacionRutaCrudService getService() {
		return service;
	}
}
