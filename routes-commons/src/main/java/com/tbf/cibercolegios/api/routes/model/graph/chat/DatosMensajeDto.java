package com.tbf.cibercolegios.api.routes.model.graph.chat;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.tbf.cibercolegios.api.model.routes.enums.IssuingMessage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DatosMensajeDto {

	private int mensajeId;

	private int conversacionId;

	@NotNull
	private IssuingMessage origen;

	@NotNull
	@Size(max = 200)
	private String mensaje;

	@NotNull
	private LocalDateTime fechaHora;
}
