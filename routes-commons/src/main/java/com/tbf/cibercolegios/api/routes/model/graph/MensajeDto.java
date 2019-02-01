package com.tbf.cibercolegios.api.routes.model.graph;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.tbf.cibercolegios.api.core.model.graph.SimpleAuditableEntityDto;
import com.tbf.cibercolegios.api.model.routes.enums.IssuingMessage;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class MensajeDto extends SimpleAuditableEntityDto<Integer> {

	private int conversacionId;

	private int monitorId;

	@NotNull
	private IssuingMessage origen;

	@NotNull
	@Size(max = 200)
	private String mensaje;

}
