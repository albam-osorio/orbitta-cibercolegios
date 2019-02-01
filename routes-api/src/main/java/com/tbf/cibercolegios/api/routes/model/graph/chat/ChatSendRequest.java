package com.tbf.cibercolegios.api.routes.model.graph.chat;

import com.tbf.cibercolegios.api.model.routes.enums.IssuingMessage;

import lombok.Data;

@Data
public class ChatSendRequest {
	private int conversacionId;

	private IssuingMessage origen;

	private String mensaje;
}
