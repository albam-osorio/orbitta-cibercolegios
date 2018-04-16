package co.com.orbitta.cibercolegios.rutas.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.orbitta.cibercolegios.constants.RutasRestConstants;
import co.com.orbitta.cibercolegios.dto.InstitucionDto;
import co.com.orbitta.cibercolegios.rutas.service.api.InstitucionCrudService;
import co.com.orbitta.core.web.api.controllers.RestCrudController;

@RestController
@RequestMapping(value = RutasRestConstants.institucion, produces = MediaType.APPLICATION_JSON_VALUE)
public class InsitucionRestCrudController extends RestCrudController<InstitucionDto, Integer> {

	@Autowired
	private InstitucionCrudService service;

	@Override
	protected InstitucionCrudService getService() {
		return service;
	}
}
