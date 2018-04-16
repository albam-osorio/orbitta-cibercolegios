package co.com.orbitta.cibercolegios.rutas.web.api.tracking;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import co.com.orbitta.cibercolegios.constants.RutasRestConstants;
import co.com.orbitta.cibercolegios.dto.tracking.DatosRutaDto;
import co.com.orbitta.cibercolegios.rutas.service.api.tracking.TrackingService;
import lombok.val;

@RestController
@RequestMapping(value = RutasRestConstants.tracking, produces = MediaType.APPLICATION_JSON_VALUE)
public class TrackingRestController {

	@Autowired
	private TrackingService service;

	private TrackingService getService() {
		return service;
	}

	@GetMapping("/rutas/{monitorId}")
	public ResponseEntity<List<DatosRutaDto>> getRutasByMonitorId(@PathVariable Integer monitorId) {

		val result = getService().getRutasByMonitorId(monitorId);

		return ResponseEntity.ok(result);
	}

	@PostMapping(path = "/track/{monitor}", params = { "type=start", "sentido", "cx", "cy" })
	public ResponseEntity<?> iniciarRecorrido(@PathVariable int monitor, @RequestParam int sentido,
			@RequestParam BigDecimal cx, @RequestParam BigDecimal cy) {

		val result = getService().iniciarRecorrido(monitor, sentido, cx, cy);

		return ResponseEntity.created(showURI(result).toUri()).body(result);
	}

	@PostMapping(path = "/track/{ubicacion}", params = { "type=pos", "cx", "cy" })
	public ResponseEntity<?> registrarPosicion(@PathVariable int ubicacion, @RequestParam BigDecimal cx,
			@RequestParam BigDecimal cy) {

		val result = getService().registrarPosicion(ubicacion, cx, cy);

		return ResponseEntity.ok(result);
	}

	@PostMapping(path = "/track/{ubicacion}", params = { "type=event", "cx", "cy", "estado" })
	public ResponseEntity<?> registrarEventoEnRecorrido(@PathVariable int ubicacion, @RequestParam BigDecimal cx,
			@RequestParam BigDecimal cy, @RequestParam int estado) {

		val result = getService().registrarEvento(ubicacion, cx, cy, estado);

		return ResponseEntity.ok(result);
	}

	@PostMapping(path = "/track/{ubicacion}", params = { "type=stop", "cx", "cy", "estado", "usuario-ruta", "log" })
	public ResponseEntity<?> registrarParadaPasajero(@PathVariable int ubicacion, @RequestParam BigDecimal cx,
			@RequestParam BigDecimal cy, @RequestParam int estado, @RequestParam("usuario-ruta") int usuarioRutaId) {
		val result = getService().registrarParadaPasajero(ubicacion, cx, cy, usuarioRutaId, estado);

		return ResponseEntity.ok(result);
	}

	@PostMapping(path = "/track/{ubicacion}", params = { "type=end", "cx", "cy", "estado" })
	public ResponseEntity<?> finalizarRecorrido(@PathVariable int ubicacion, @RequestParam BigDecimal cx,
			@RequestParam BigDecimal cy, @RequestParam int estado) {
		val result = getService().finalizarRecorrido(ubicacion, cx, cy, estado);

		return ResponseEntity.ok(result);
	}

	protected UriComponents showURI(DatosRutaDto model) {
		// @formatter:off
		val result = MvcUriComponentsBuilder
				.fromMethodCall(MvcUriComponentsBuilder.on(TrackingRestController.class).getRutasByMonitorId(null))
				.buildAndExpand(model.getLogRuta().getId())
				.encode();
		// @formatter:on

		return result;
	}
}
