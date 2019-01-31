package com.tbf.cibercolegios.api.routes.model.graph.chat;

import com.tbf.cibercolegios.api.routes.model.enums.IssuingMessage;

import lombok.Data;

@Data
public class ChatSendRequest {
	private int conversacionId;

	private IssuingMessage origen;

	private String mensaje;
}
