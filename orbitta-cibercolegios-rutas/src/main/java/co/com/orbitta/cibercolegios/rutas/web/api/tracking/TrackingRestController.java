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

import co.com.orbitta.cibercolegios.rutas.constants.RutasRestConstants;
import co.com.orbitta.cibercolegios.rutas.dto.tracking.acudiente.DatosEstadoParadaDto;
import co.com.orbitta.cibercolegios.rutas.dto.tracking.acudiente.DatosParadaDto;
import co.com.orbitta.cibercolegios.rutas.dto.tracking.monitor.DatosRutaDto;
import co.com.orbitta.cibercolegios.rutas.service.api.tracking.AcudienteTrackingService;
import co.com.orbitta.cibercolegios.rutas.service.api.tracking.MonitorTrackingService;
import lombok.val;

@RestController
@RequestMapping(value = RutasRestConstants.tracking, produces = MediaType.APPLICATION_JSON_VALUE)
public class TrackingRestController {

	@Autowired
	private MonitorTrackingService monitorTrackingService;

	@Autowired
	private AcudienteTrackingService acudienteTrackingService;

	@GetMapping("/monitor/{monitorId}/rutas")
	public ResponseEntity<List<DatosRutaDto>> getRutasByMonitorId(@PathVariable Integer monitorId) {

		val result = monitorTrackingService.findRutasByMonitorId(monitorId);

		return ResponseEntity.ok(result);
	}

	@PostMapping(path = "/monitor/{monitorId}/rutas/{rutaId}/track/start", params = { "x", "y", "sentido" })
	public ResponseEntity<?> iniciarRecorrido(@PathVariable int monitorId, @PathVariable int rutaId,
			@RequestParam BigDecimal x, @RequestParam BigDecimal y, @RequestParam int sentido) {

		val result = monitorTrackingService.iniciarRecorrido(monitorId, rutaId, x, y, sentido);

		return ResponseEntity.created(showURI(result).toUri()).body(result);
	}

	@PostMapping(path = "/track/{logRutaId}", params = { "type=pos", "x", "y" })
	public ResponseEntity<?> registrarPosicion(@PathVariable int logRutaId, @RequestParam BigDecimal x,
			@RequestParam BigDecimal y) {

		val result = monitorTrackingService.registrarPosicion(logRutaId, x, y);

		return ResponseEntity.ok(result);
	}

	@PostMapping(path = "/track/{logRutaId}", params = { "type=event", "x", "y", "estado" })
	public ResponseEntity<?> registrarEventoEnRecorrido(@PathVariable int logRutaId, @RequestParam BigDecimal x,
			@RequestParam BigDecimal y, @RequestParam int estado) {

		val result = monitorTrackingService.registrarEvento(logRutaId, x, y, estado);

		return ResponseEntity.ok(result);
	}

	@PostMapping(path = "/track/{logRutaId}", params = { "type=stop", "x", "y", "usuario-ruta", "estado" })
	public ResponseEntity<?> registrarParadaPasajero(@PathVariable int logRutaId, @RequestParam BigDecimal x,
			@RequestParam BigDecimal y, @RequestParam("usuario-ruta") int usuarioRutaId, @RequestParam int estado) {

		val result = monitorTrackingService.registrarParadaPasajero(logRutaId, x, y, usuarioRutaId, estado);

		return ResponseEntity.ok(result);
	}

	@PostMapping(path = "/track/{logRutaId}", params = { "type=end", "x", "y" })
	public ResponseEntity<?> finalizarRecorrido(@PathVariable int logRutaId, @RequestParam BigDecimal x,
			@RequestParam BigDecimal y) {

		val result = monitorTrackingService.finalizarRecorrido(logRutaId, x, y);

		return ResponseEntity.ok(result);
	}

	@GetMapping("/estudiante/{usuarioId}/parada")
	public ResponseEntity<DatosParadaDto> getParadaByEstudianteId(@PathVariable Integer usuarioId) {
		val optional = acudienteTrackingService.findParadaByUsuarioId(usuarioId);

		if (optional.isPresent()) {
			return ResponseEntity.ok(optional.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/estudiante/{usuarioId}/parada/status")
	public ResponseEntity<DatosEstadoParadaDto> getEstadoParadaByEstudianteId(@PathVariable Integer usuarioId) {
		val optional = acudienteTrackingService.findEstadoParadaByUsuarioId(usuarioId);

		if (optional.isPresent()) {
			return ResponseEntity.ok(optional.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/ruta/{rutaId}/status")
	public ResponseEntity<DatosRutaDto> getEstadoByRutaId(@PathVariable Integer rutaId) {
		val optional = monitorTrackingService.findEstadoRutaByRutaId(rutaId);

		if (optional.isPresent()) {
			return ResponseEntity.ok(optional.get());
		} else {
			return ResponseEntity.notFound().build();
		}
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
