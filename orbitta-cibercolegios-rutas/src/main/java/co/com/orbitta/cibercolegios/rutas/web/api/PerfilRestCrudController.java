package co.com.orbitta.cibercolegios.rutas.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.orbitta.cibercolegios.constants.RestConstants;
import co.com.orbitta.cibercolegios.dto.PerfilDto;
import co.com.orbitta.cibercolegios.rutas.service.api.PerfilCrudService;
import co.com.orbitta.core.web.api.controllers.RestCrudController;

@RestController
@RequestMapping(value = RestConstants.perfil, produces = MediaType.APPLICATION_JSON_VALUE)
public class PerfilRestCrudController extends RestCrudController<PerfilDto, PerfilDto, Integer> {

	@Autowired
	private PerfilCrudService service;

	@Override
	protected PerfilCrudService getService() {
		return service;
	}
}
