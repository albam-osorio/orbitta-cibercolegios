package co.com.orbitta.cibercolegios.rutas.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.orbitta.cibercolegios.constants.RestConstants;
import co.com.orbitta.cibercolegios.dto.EstadoDto;
import co.com.orbitta.cibercolegios.rutas.service.api.EstadoCrudService;
import co.com.orbitta.core.web.api.controllers.RestCrudController;

@RestController
@RequestMapping(value = RestConstants.estado, produces = MediaType.APPLICATION_JSON_VALUE)
public class EstadoRestCrudController extends RestCrudController<EstadoDto, Integer> {

	@Autowired
	private EstadoCrudService service;

	@Override
	protected EstadoCrudService getService() {
		return service;
	}
}
