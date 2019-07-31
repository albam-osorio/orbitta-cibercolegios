package com.tbf.cibercolegios.api.routes.model.graph;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.tbf.cibercolegios.api.core.model.graph.SimpleAuditableEntityDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class EstadoDireccionDto extends SimpleAuditableEntityDto<Integer> {

	public static final int ESTADO_NO_GEOREFERENCIADA = 1;
	
	public static final int GEOREFERENCIADA = 1;

	@NotNull
	@Size(max = 50)
	private String descripcion;
}