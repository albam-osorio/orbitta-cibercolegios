package com.tbf.cibercolegios.api.routes.services.api.chat;

import org.springframework.transaction.annotation.Transactional;

import com.tbf.cibercolegios.api.routes.model.enums.IssuingMessage;

public interface ChatService {

	@Transactional(readOnly = false)
	void registrarAcudiente(int usuarioId, String token);

	@Transactional(readOnly = false)
	int iniciarConversacion(int rutaId, int acudienteId, int pasajeroId);

	@Transactional(readOnly = false)
	int enviarMensaje(int conversacionId, IssuingMessage origen, String mensaje);
}
