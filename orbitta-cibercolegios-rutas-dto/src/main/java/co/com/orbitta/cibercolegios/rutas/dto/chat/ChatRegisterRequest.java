package co.com.orbitta.cibercolegios.rutas.dto.chat;

import lombok.Data;

@Data
public class ChatRegisterRequest {
	private int acudienteId;
	private String token;
}
