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

import co.com.orbitta.cibercolegios.rutas.constants.RutasRestConstants;
import co.com.orbitta.cibercolegios.rutas.dto.tracking.acudiente.DatosEstadoParadaDto;
import co.com.orbitta.cibercolegios.rutas.dto.tracking.acudiente.DatosParadaDto;
import co.com.orbitta.cibercolegios.rutas.dto.tracking.monitor.MonitorDatosRutaDto;
import co.com.orbitta.cibercolegios.rutas.service.api.tracking.acudiente.AcudienteTrackingService;
import co.com.orbitta.cibercolegios.rutas.service.api.tracking.monitor.MonitorTrackingService;
import lombok.val;

@RestController
@RequestMapping(value = RutasRestConstants.tracking, produces = MediaType.APPLICATION_JSON_VALUE)
public class TrackingRestController {

	@Autowired
	private MonitorTrackingService monitorTrackingService;

	@Autowired
	private AcudienteTrackingService acudienteTrackingService;

	@GetMapping("/monitor/{monitorId}/rutas")
	public ResponseEntity<List<MonitorDatosRutaDto>> getRutasByMonitorId(@PathVariable Integer monitorId) {

		val result = monitorTrackingService.findRutasByMonitorId(monitorId);

		return ResponseEntity.ok(result);
	}

	@PostMapping(path = "/monitor/{monitorId}/rutas/{rutaId}/track/start", params = { "x", "y", "sentido", "token" })
	public ResponseEntity<?> iniciarRecorrido(@PathVariable int monitorId, @PathVariable int rutaId,
			@RequestParam(required = true) BigDecimal x, @RequestParam(required = true) BigDecimal y,
			@RequestParam(required = true) Integer sentido, @RequestParam(required = true) String token) {

		val result = monitorTrackingService.iniciarRecorrido(monitorId, rutaId, x, y, sentido, token);

		return ResponseEntity.ok(result);
	}

	@GetMapping("/ruta/{rutaId}/status")
	public ResponseEntity<MonitorDatosRutaDto> getEstadoByRutaId(@PathVariable Integer rutaId) {
		val optional = monitorTrackingService.findRutaByRutaId(rutaId);

		if (optional.isPresent()) {
			return ResponseEntity.ok(optional.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping(path = "/ruta/{rutaId}/track", params = { "type=pos", "x", "y" })
	public ResponseEntity<?> registrarPosicion(@PathVariable int rutaId, @RequestParam(required = true) BigDecimal x,
			@RequestParam(required = true) BigDecimal y) {

		val result = monitorTrackingService.registrarPosicion(rutaId, x, y);

		return ResponseEntity.ok(result);
	}

	@PostMapping(path = "/ruta/{rutaId}/track", params = { "type=event", "x", "y", "estado" })
	public ResponseEntity<?> registrarEventoEnRecorrido(@PathVariable int rutaId,
			@RequestParam(required = true) BigDecimal x, @RequestParam(required = true) BigDecimal y,
			@RequestParam(required = true) Integer estado) {

		val result = monitorTrackingService.registrarEvento(rutaId, x, y, estado);

		return ResponseEntity.ok(result);
	}

	@PostMapping(path = "/ruta/{rutaId}/track", params = { "type=stop", "x", "y", "usuarioId", "estado" })
	public ResponseEntity<?> registrarParadaPasajero(@PathVariable int rutaId,
			@RequestParam(required = true) BigDecimal x, @RequestParam(required = true) BigDecimal y,
			@RequestParam(required = true) Integer usuarioId, @RequestParam(required = true) Integer estado) {

		val result = monitorTrackingService.registrarParadaPasajero(rutaId, x, y, usuarioId, estado);

		return ResponseEntity.ok(result);
	}

	@PostMapping(path = "/ruta/{rutaId}/track", params = { "type=end", "x", "y" })
	public ResponseEntity<?> finalizarRecorrido(@PathVariable int rutaId, @RequestParam(required = true) BigDecimal x,
			@RequestParam(required = true) BigDecimal y) {

		val result = monitorTrackingService.finalizarRecorrido(rutaId, x, y);

		return ResponseEntity.ok(result);
	}

	// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//
	// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	@GetMapping("/estudiante/{usuarioId}/parada")
	public ResponseEntity<DatosParadaDto> getParadaByUsuarioId(@PathVariable Integer usuarioId) {
		val optional = acudienteTrackingService.findParadaByUsuarioId(usuarioId);

		if (optional.isPresent()) {
			return ResponseEntity.ok(optional.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/estudiante/{usuarioId}/parada/status")
	public ResponseEntity<DatosEstadoParadaDto> getEstadoParadaByUsuarioId(@PathVariable Integer usuarioId) {
		val optional = acudienteTrackingService.findEstadoParadaByUsuarioId(usuarioId);

		if (optional.isPresent()) {
			return ResponseEntity.ok(optional.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//
	// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	@PostMapping(path = "/track/{logRutaId}", params = { "type=pos", "x", "y" })
	public ResponseEntity<?> registrarPosicionByLogRuta(@PathVariable int logRutaId, @RequestParam BigDecimal x,
			@RequestParam BigDecimal y) {

		val result = monitorTrackingService.registrarPosicionByLogRuta(logRutaId, x, y);

		return ResponseEntity.ok(result);
	}

	@PostMapping(path = "/track/{logRutaId}", params = { "type=event", "x", "y", "estado" })
	public ResponseEntity<?> registrarEventoEnRecorridoByLogRuta(@PathVariable int logRutaId,
			@RequestParam BigDecimal x, @RequestParam BigDecimal y, @RequestParam int estado) {

		val result = monitorTrackingService.registrarEventoByLogRuta(logRutaId, x, y, estado);

		return ResponseEntity.ok(result);
	}

	@PostMapping(path = "/track/{logRutaId}", params = { "type=stop", "x", "y", "usuario-ruta", "estado" })
	public ResponseEntity<?> registrarParadaPasajeroByLogRuta(@PathVariable int logRutaId, @RequestParam BigDecimal x,
			@RequestParam BigDecimal y, @RequestParam("usuario-ruta") int usuarioRutaId, @RequestParam int estado) {

		val result = monitorTrackingService.registrarParadaPasajeroByLogRuta(logRutaId, x, y, usuarioRutaId, estado);

		return ResponseEntity.ok(result);
	}

	@PostMapping(path = "/track/{logRutaId}", params = { "type=end", "x", "y" })
	public ResponseEntity<?> finalizarRecorridoByLogRuta(@PathVariable int logRutaId, @RequestParam BigDecimal x,
			@RequestParam BigDecimal y) {

		val result = monitorTrackingService.finalizarRecorridoByLogRuta(logRutaId, x, y);

		return ResponseEntity.ok(result);
	}

}
