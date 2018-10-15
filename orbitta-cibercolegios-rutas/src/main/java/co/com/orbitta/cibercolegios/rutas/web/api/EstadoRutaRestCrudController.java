package co.com.orbitta.cibercolegios.rutas.web.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.orbitta.cibercolegios.rutas.constants.RutasRestConstants;
import co.com.orbitta.cibercolegios.rutas.dto.EstadoRutaDto;
import co.com.orbitta.cibercolegios.rutas.service.api.EstadoRutaService;
import co.com.orbitta.core.web.api.controllers.CrudRestController;
import lombok.val;

@RestController
@RequestMapping(value = RutasRestConstants.estadoRuta, produces = MediaType.APPLICATION_JSON_VALUE)
public class EstadoRutaRestCrudController extends CrudRestController<EstadoRutaDto, Integer> {

	@Autowired
	private EstadoRutaService service;

	@Override
	protected EstadoRutaService getService() {
		return service;
	}
	
	@GetMapping(PATH_LIST)
	public ResponseEntity<List<EstadoRutaDto>> list() {
		val result = getService().findAll();
		return ResponseEntity.ok(result);
	}
}
