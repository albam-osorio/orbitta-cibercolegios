package com.tbf.cibercolegios.api.routes.model.graph;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import co.com.orbitta.core.dto.AuditableEntityDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class EstadoDireccionDto extends AuditableEntityDto<Integer> {

	@NotNull
	@Size(max = 50)
	private String descripcion;
}