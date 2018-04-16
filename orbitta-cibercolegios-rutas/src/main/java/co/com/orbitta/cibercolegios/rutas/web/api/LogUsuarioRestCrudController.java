package co.com.orbitta.cibercolegios.rutas.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.orbitta.cibercolegios.constants.RutasRestConstants;
import co.com.orbitta.cibercolegios.dto.LogUsuarioRutaDto;
import co.com.orbitta.cibercolegios.rutas.service.api.LogUsuarioRutaCrudService;
import co.com.orbitta.core.web.api.controllers.RestCrudController;

@RestController
@RequestMapping(value = RutasRestConstants.logUsuario, produces = MediaType.APPLICATION_JSON_VALUE)
public class LogUsuarioRestCrudController extends RestCrudController<LogUsuarioRutaDto, Integer> {

	@Autowired
	private LogUsuarioRutaCrudService service;

	@Override
	protected LogUsuarioRutaCrudService getService() {
		return service;
	}
}
