package co.com.orbitta.cibercolegios.rutas.web.api.tracking;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.orbitta.cibercolegios.rutas.constants.RutasRestConstants;



@RestController
@RequestMapping(value = RutasRestConstants.chat, produces = MediaType.APPLICATION_JSON_VALUE)
public class ChatRestController {

//	@Autowired
//	private ChatService chatTrackingService;
//
//	@GetMapping("/monitor/{monitorId}/rutas")
//	public ResponseEntity<List<DatosRutaDto>> getRutasByMonitorId(@PathVariable Integer monitorId) {
//
//		val result = chatTrackingService.findRutasByMonitorId(monitorId);
//
//		return ResponseEntity.ok(result);
//	}
//
//	@PostMapping(path = "/monitor/{monitorId}/rutas/{rutaId}/track/start", params = { "x", "y", "sentido" })
//	public ResponseEntity<?> iniciarRecorrido(@PathVariable int monitorId, @PathVariable int rutaId,
//			@RequestParam BigDecimal x, @RequestParam BigDecimal y, @RequestParam int sentido) {
//
//		val result = chatTrackingService.iniciarRecorrido(monitorId, rutaId, x, y, sentido);
//
//		return ResponseEntity.created(showURI(result).toUri()).body(result);
//	}
//
//	@PostMapping(path = "/track/{logRutaId}", params = { "type=pos", "x", "y" })
//	public ResponseEntity<?> registrarPosicion(@PathVariable int logRutaId, @RequestParam BigDecimal x,
//			@RequestParam BigDecimal y) {
//
//		val result = chatTrackingService.registrarPosicion(logRutaId, x, y);
//
//		return ResponseEntity.ok(result);
//	}
//
//	@PostMapping(path = "/track/{logRutaId}", params = { "type=event", "x", "y", "estado" })
//	public ResponseEntity<?> registrarEventoEnRecorrido(@PathVariable int logRutaId, @RequestParam BigDecimal x,
//			@RequestParam BigDecimal y, @RequestParam int estado) {
//
//		val result = chatTrackingService.registrarEvento(logRutaId, x, y, estado);
//
//		return ResponseEntity.ok(result);
//	}
//
//	@PostMapping(path = "/track/{logRutaId}", params = { "type=stop", "x", "y", "usuario-ruta", "estado" })
//	public ResponseEntity<?> registrarParadaPasajero(@PathVariable int logRutaId, @RequestParam BigDecimal x,
//			@RequestParam BigDecimal y, @RequestParam("usuario-ruta") int usuarioRutaId, @RequestParam int estado) {
//
//		val result = chatTrackingService.registrarParadaPasajero(logRutaId, x, y, usuarioRutaId, estado);
//
//		return ResponseEntity.ok(result);
//	}
//
//	@PostMapping(path = "/track/{logRutaId}", params = { "type=end", "x", "y" })
//	public ResponseEntity<?> finalizarRecorrido(@PathVariable int logRutaId, @RequestParam BigDecimal x,
//			@RequestParam BigDecimal y) {
//
//		val result = chatTrackingService.finalizarRecorrido(logRutaId, x, y);
//
//		return ResponseEntity.ok(result);
//	}
//
//	
//	protected UriComponents showURI(DatosRutaDto model) {
//		// @formatter:off
//		val result = MvcUriComponentsBuilder
//				.fromMethodCall(MvcUriComponentsBuilder.on(ChatRestController.class).getRutasByMonitorId(null))
//				.buildAndExpand(model.getLogRuta().getId())
//				.encode();
//		// @formatter:on
//
//		return result;
//	}
}
