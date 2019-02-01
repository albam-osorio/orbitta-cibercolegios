package com.tbf.cibercolegios.api.routes.model.graph;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.tbf.cibercolegios.api.core.model.graph.SimpleAuditableEntityDto;
import com.tbf.cibercolegios.api.model.routes.enums.PassengerTypeStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class LogPasajeroDto extends SimpleAuditableEntityDto<Integer> {

	private int rutaId;

	private int usuarioId;

	private int sentido;

	private int estadoId;

	@NotNull
	private PassengerTypeStatus tipoEstado;

	@NotNull
	@Size(max = 50)
	private String estadoDescripcion;

	@NotNull
	private BigDecimal x;

	@NotNull
	private BigDecimal y;

}
