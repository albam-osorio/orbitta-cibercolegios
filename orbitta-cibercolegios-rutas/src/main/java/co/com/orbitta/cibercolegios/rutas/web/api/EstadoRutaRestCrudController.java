package co.com.orbitta.cibercolegios.rutas.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.orbitta.cibercolegios.constants.RutasRestConstants;
import co.com.orbitta.cibercolegios.dto.EstadoRutaDto;
import co.com.orbitta.cibercolegios.rutas.service.api.EstadoRutaCrudService;
import co.com.orbitta.core.web.api.controllers.RestCrudController;

@RestController
@RequestMapping(value = RutasRestConstants.estadoRuta, produces = MediaType.APPLICATION_JSON_VALUE)
public class EstadoRutaRestCrudController extends RestCrudController<EstadoRutaDto, Integer> {

	@Autowired
	private EstadoRutaCrudService service;

	@Override
	protected EstadoRutaCrudService getService() {
		return service;
	}
}
