package co.com.orbitta.cibercolegios.rutas.dto.chat;

import co.com.orbitta.cibercolegios.rutas.enums.EmisorMensajeEnum;
import lombok.Data;

@Data
public class ChatSendRequest {
	private int conversacionId;

	private EmisorMensajeEnum origen;

	private String mensaje;
}
