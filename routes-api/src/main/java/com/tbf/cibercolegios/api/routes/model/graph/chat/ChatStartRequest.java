package com.tbf.cibercolegios.api.routes.model.graph.chat;

import lombok.Data;

@Data
public class ChatStartRequest {
	private int rutaId;
	private int acudienteId;
	private int estudianteId;
}
