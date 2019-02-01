package com.tbf.cibercolegios.api.routes.model.graph;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.tbf.cibercolegios.api.core.model.graph.AuditableEntityDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class DireccionDto extends AuditableEntityDto<Integer> {

	public static final int ESTADO_NO_PROCESADA = 1;
	
	private int institucionId;

	private int estadoId;
	
	private int paisId;
	
	private int departamentoId;

	private int ciudadId;

	@NotNull
	@Size(max = 100)
	private String direccion;

	private BigDecimal x;

	private BigDecimal y;
}
