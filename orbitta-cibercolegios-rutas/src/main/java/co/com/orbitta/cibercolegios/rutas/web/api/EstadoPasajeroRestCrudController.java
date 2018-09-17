package co.com.orbitta.cibercolegios.rutas.web.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.orbitta.cibercolegios.rutas.constants.RutasRestConstants;
import co.com.orbitta.cibercolegios.rutas.dto.EstadoPasajeroDto;
import co.com.orbitta.cibercolegios.rutas.service.api.EstadoPasajeroCrudService;
import co.com.orbitta.core.web.api.controllers.CrudRestController;
import lombok.val;

@RestController
@RequestMapping(value = RutasRestConstants.estadoPasajero, produces = MediaType.APPLICATION_JSON_VALUE)
public class EstadoPasajeroRestCrudController extends CrudRestController<EstadoPasajeroDto, Integer> {

	@Autowired
	private EstadoPasajeroCrudService service;

	@Override
	protected EstadoPasajeroCrudService getService() {
		return service;
	}
	
	@GetMapping(PATH_LIST)
	public ResponseEntity<List<EstadoPasajeroDto>> list() {
		val result = getService().findAll();
		return ResponseEntity.ok(result);
	}
}
