package co.com.orbitta.cibercolegios.ciber.web.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.orbitta.cibercolegios.ciber.constants.CiberRestConstants;
import co.com.orbitta.cibercolegios.ciber.dto.InstitucionDto;
import co.com.orbitta.cibercolegios.ciber.service.api.InstitucionQueryService;
import co.com.orbitta.core.web.api.controllers.QueryRestController;
import lombok.val;

@RestController
@RequestMapping(value = CiberRestConstants.instituciones, produces = MediaType.APPLICATION_JSON_VALUE)
public class InstitucionQueryRestController extends QueryRestController<InstitucionDto, Integer> {

	@Autowired
	private InstitucionQueryService service;

	@Override
	protected InstitucionQueryService getService() {
		return service;
	}

	@GetMapping(PATH_LIST)
	public ResponseEntity<List<InstitucionDto>> getAll() {
		val result = getService().findAll();
		return ResponseEntity.ok(result);
	}
}
