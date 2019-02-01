package com.tbf.cibercolegios.api.routes.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tbf.cibercolegios.api.routes.model.graph.chat.AcudienteRegisterDto;
import com.tbf.cibercolegios.api.routes.model.graph.chat.ChatSendRequest;
import com.tbf.cibercolegios.api.routes.model.graph.chat.ChatStartRequest;
import com.tbf.cibercolegios.api.routes.model.graph.chat.DatosConversacionDto;
import com.tbf.cibercolegios.api.routes.model.graph.chat.DatosMensajeDto;
import com.tbf.cibercolegios.api.routes.model.graph.constants.RutasRestConstants;
import com.tbf.cibercolegios.api.routes.services.api.ConversacionService;
import com.tbf.cibercolegios.api.routes.services.api.MensajeService;
import com.tbf.cibercolegios.api.routes.services.api.chat.ChatService;

import lombok.val;

@RestController
@RequestMapping(value = RutasRestConstants.conversaciones, produces = MediaType.APPLICATION_JSON_VALUE)
public class ChatRestController {

	@Autowired
	private ChatService chatService;

	@Autowired
	private ConversacionService conversacionService;

	@Autowired
	private MensajeService mensajeService;

	@GetMapping(path = "/ruta/{rutaId}", params = {})
	public ResponseEntity<List<DatosConversacionDto>> findByRutaId(@PathVariable Integer rutaId) {
		val result = conversacionService.findByRutaId(rutaId);
		return ResponseEntity.ok(result);
	}

	@GetMapping(path = "/ruta/{rutaId}", params = { "acudienteId", "estudianteId" })
	public ResponseEntity<DatosConversacionDto> findConversacion(@PathVariable Integer rutaId,
			@RequestParam(required = true) Integer acudienteId, @RequestParam(required = true) Integer estudianteId) {

		val optional = conversacionService.findByRutaIdAndUsuarioAcudienteIdAndUsuarioPasajeroId(rutaId, acudienteId,
				estudianteId);

		if (optional.isPresent()) {
			return ResponseEntity.ok(optional.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping(path = RutasRestConstants.mensajes)
	public ResponseEntity<List<DatosMensajeDto>> findMensajesByConversacionId(@PathVariable Integer conversacionId) {

		val result = mensajeService.findAllByConversacionId(conversacionId);

		return ResponseEntity.ok(result);
	}

	@PostMapping(path = "/register")
	public ResponseEntity<?> registrar(@RequestBody AcudienteRegisterDto request) {

		chatService.registrarAcudiente(request.getAcudienteId(), request.getToken());
		return ResponseEntity.ok("");
	}

	@PostMapping(path = "/start")
	public ResponseEntity<?> iniciarRecorrido(@RequestBody ChatStartRequest request) {

		val result = chatService.iniciarConversacion(request.getRutaId(), request.getAcudienteId(),
				request.getEstudianteId());
		return ResponseEntity.ok(result);
	}

	@PostMapping(path = "/send")
	public ResponseEntity<?> enviarMensaje(@RequestBody ChatSendRequest request) {

		val result = chatService.enviarMensaje(request.getConversacionId(), request.getOrigen(), request.getMensaje());
		return ResponseEntity.ok(result);
	}
}
