package com.tbf.cibercolegios.api.routes.model.graph;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import com.tbf.cibercolegios.api.core.model.graph.SimpleAuditableEntityDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class LogRutaDto extends SimpleAuditableEntityDto<Integer> {

	private int rutaId;

	private int monitorId;

	private int sentido;

	private int estadoId;

	@NotNull
	private BigDecimal x;

	@NotNull
	private BigDecimal y;
}
