package co.com.orbitta.cibercolegios.ciber.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.orbitta.cibercolegios.ciber.constants.CiberRestConstants;
import co.com.orbitta.cibercolegios.ciber.dto.UsuarioDto;
import co.com.orbitta.cibercolegios.ciber.service.api.UsuarioQueryService;
import co.com.orbitta.core.web.api.controllers.QueryRestController;

@RestController
@RequestMapping(value = CiberRestConstants.usuarios, produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioQueryRestController extends QueryRestController<UsuarioDto, Integer> {

	@Autowired
	private UsuarioQueryService service;

	@Override
	protected UsuarioQueryService getService() {
		return service;
	}
}
