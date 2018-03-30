package co.com.orbitta.cibercolegios.rutas.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.orbitta.cibercolegios.constants.RestConstants;
import co.com.orbitta.cibercolegios.dto.DireccionUsuarioDto;
import co.com.orbitta.cibercolegios.rutas.service.api.DireccionUsuarioCrudService;
import co.com.orbitta.core.web.api.controllers.RestCrudController;

@RestController
@RequestMapping(value = RestConstants.direccionUsuario, produces = MediaType.APPLICATION_JSON_VALUE)
public class DireccionUsuarioRestCrudController extends RestCrudController<DireccionUsuarioDto, DireccionUsuarioDto, Integer> {

	@Autowired
	private DireccionUsuarioCrudService service;

	@Override
	protected DireccionUsuarioCrudService getService() {
		return service;
	}
}
