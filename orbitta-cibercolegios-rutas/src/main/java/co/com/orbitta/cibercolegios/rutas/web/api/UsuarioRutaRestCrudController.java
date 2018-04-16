package co.com.orbitta.cibercolegios.rutas.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.orbitta.cibercolegios.constants.RutasRestConstants;
import co.com.orbitta.cibercolegios.dto.UsuarioRutaDto;
import co.com.orbitta.cibercolegios.rutas.service.api.UsuarioRutaCrudService;
import co.com.orbitta.core.web.api.controllers.RestCrudController;

@RestController
@RequestMapping(value = RutasRestConstants.usuarioRuta, produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioRutaRestCrudController extends RestCrudController<UsuarioRutaDto, Integer> {

	@Autowired
	private UsuarioRutaCrudService service;

	@Override
	protected UsuarioRutaCrudService getService() {
		return service;
	}
}
