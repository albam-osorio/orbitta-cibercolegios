package co.com.orbitta.cibercolegios.rutas.service.api.chat;

import org.springframework.transaction.annotation.Transactional;

import co.com.orbitta.cibercolegios.rutas.enums.EmisorMensajeEnum;

public interface ChatService {

	@Transactional(readOnly = false)
	void registrarAcudiente(int usuarioId, String token);

	@Transactional(readOnly = false)
	int iniciarConversacion(int rutaId, int acudienteId, int pasajeroId);

	@Transactional(readOnly = false)
	int enviarMensaje(int conversacionId, EmisorMensajeEnum origen, String mensaje);
}
