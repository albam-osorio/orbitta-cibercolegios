package co.com.orbitta.cibercolegios.rutas.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.orbitta.cibercolegios.constants.RestConstants;
import co.com.orbitta.cibercolegios.dto.TipoPerfilDto;
import co.com.orbitta.cibercolegios.rutas.service.api.TipoPerfilCrudService;
import co.com.orbitta.core.web.api.controllers.RestCrudController;

@RestController
@RequestMapping(value = RestConstants.tipoPerfil, produces = MediaType.APPLICATION_JSON_VALUE)
public class TipoPerfilRestCrudController extends RestCrudController<TipoPerfilDto, TipoPerfilDto, Integer> {

	@Autowired
	private TipoPerfilCrudService service;

	@Override
	protected TipoPerfilCrudService getService() {
		return service;
	}
}
