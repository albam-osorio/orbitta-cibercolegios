package com.tbf.cibercolegios.api.routes.model.graph;

import com.tbf.cibercolegios.api.core.model.graph.SimpleAuditableEntityDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class ConversacionDto extends SimpleAuditableEntityDto<Integer> {

	private int rutaId;

	private int usuarioAcudienteId;

	private int usuarioPasajeroId;
}
