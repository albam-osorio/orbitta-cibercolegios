package co.com.orbitta.cibercolegios.rutas.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.orbitta.cibercolegios.constants.RestConstants;
import co.com.orbitta.cibercolegios.dto.UsuarioDto;
import co.com.orbitta.cibercolegios.rutas.service.api.UsuarioCrudService;
import co.com.orbitta.core.web.api.controllers.RestCrudController;

@RestController
@RequestMapping(value = RestConstants.usuario, produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioRestCrudController extends RestCrudController<UsuarioDto, Integer> {

	@Autowired
	private UsuarioCrudService service;

	@Override
	protected UsuarioCrudService getService() {
		return service;
	}
}
