package co.com.orbitta.cibercolegios.rutas.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.orbitta.cibercolegios.constants.RestConstants;
import co.com.orbitta.cibercolegios.dto.LogUsuarioDto;
import co.com.orbitta.cibercolegios.rutas.service.api.LogUsuarioCrudService;
import co.com.orbitta.core.web.api.controllers.RestCrudController;

@RestController
@RequestMapping(value = RestConstants.logUsuario, produces = MediaType.APPLICATION_JSON_VALUE)
public class LogUsuarioRestCrudController extends RestCrudController<LogUsuarioDto, LogUsuarioDto, Integer> {

	@Autowired
	private LogUsuarioCrudService service;

	@Override
	protected LogUsuarioCrudService getService() {
		return service;
	}
}
