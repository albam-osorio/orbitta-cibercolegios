package co.com.orbitta.cibercolegios.rutas.dto.chat;

import lombok.Data;

@Data
public class ChatStartRequest {
	private int rutaId;
	private int acudienteId;
	private int estudianteId;
}
