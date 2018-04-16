package co.com.orbitta.cibercolegios.rutas.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.orbitta.cibercolegios.constants.RutasRestConstants;
import co.com.orbitta.cibercolegios.dto.EstadoUsuarioRutaDto;
import co.com.orbitta.cibercolegios.rutas.service.api.EstadoUsuarioRutaCrudService;
import co.com.orbitta.core.web.api.controllers.RestCrudController;

@RestController
@RequestMapping(value = RutasRestConstants.log, produces = MediaType.APPLICATION_JSON_VALUE)
public class LogRestCrudController extends RestCrudController<EstadoUsuarioRutaDto, Integer> {

	@Autowired
	private EstadoUsuarioRutaCrudService service;

	@Override
	protected EstadoUsuarioRutaCrudService getService() {
		return service;
	}
}
