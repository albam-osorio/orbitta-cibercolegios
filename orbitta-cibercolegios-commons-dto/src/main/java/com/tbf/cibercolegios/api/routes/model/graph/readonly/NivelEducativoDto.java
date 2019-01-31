package com.tbf.cibercolegios.api.routes.model.graph.readonly;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class NivelEducativoDto {

	private int jornadaId;

	private Integer id;

	@NotNull
	@Size(max = 50)
	private String descripcion;
}