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
import co.com.orbitta.cibercolegios.dto.tracking.acudiente.DatosEstadoParadaDto;
import co.com.orbitta.cibercolegios.dto.tracking.acudiente.DatosParadaDto;
import co.com.orbitta.cibercolegios.dto.tracking.monitor.DatosRutaDto;
import co.com.orbitta.cibercolegios.rutas.service.api.tracking.AcudienteTrackingService;
import co.com.orbitta.cibercolegios.rutas.service.api.tracking.ChatTrackingService;
import lombok.val;

@RestController
@RequestMapping(value = RutasRestConstants.chat, produces = MediaType.APPLICATION_JSON_VALUE)
public class ChatRestController {

	@Autowired
	private ChatTrackingService chatTrackingService;

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

	@GetMapping("/estudiante/{estudianteId}/parada")
	public ResponseEntity<DatosParadaDto> getParadaByEstudianteId(@PathVariable Integer estudianteId) {

		val result = acudienteTrackingService.findParadaByEstudianteId(estudianteId);

		return ResponseEntity.ok(result);
	}

	@GetMapping("/estudiante/{estudianteId}/parada/status")
	public ResponseEntity<DatosEstadoParadaDto> getEstadoParadaByEstudianteId(@PathVariable Integer estudianteId) {

		val result = acudienteTrackingService.findEstadoParadaByEstudianteId(estudianteId);

		return ResponseEntity.ok(result);
	}
	
	protected UriComponents showURI(DatosRutaDto model) {
		// @formatter:off
		val result = MvcUriComponentsBuilder
				.fromMethodCall(MvcUriComponentsBuilder.on(ChatRestController.class).getRutasByMonitorId(null))
				.buildAndExpand(model.getLogRuta().getId())
				.encode();
		// @formatter:on

		return result;
	}
}
