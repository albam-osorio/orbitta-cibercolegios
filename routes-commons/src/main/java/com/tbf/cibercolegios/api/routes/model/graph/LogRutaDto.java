package com.tbf.cibercolegios.api.routes.model.graph;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.tbf.cibercolegios.api.core.model.graph.SimpleAuditableEntityDto;
import com.tbf.cibercolegios.api.model.routes.enums.RouteTypeStatus;

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

	private int sentido;

	private int estadoId;

	@NotNull
	private RouteTypeStatus tipoEstado;

	@NotNull
	@Size(max = 50)
	private String estadoDescripcion;

	@NotNull
	private BigDecimal x;

	@NotNull
	private BigDecimal y;

}
