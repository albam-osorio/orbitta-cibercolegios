package com.tbf.cibercolegios.api.ciber.model.graph;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.tbf.cibercolegios.api.core.model.graph.EntityDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class TipoDocumentoDto extends EntityDto<Integer> {

	@NotNull
	@Size(max = 4)
	private String abreviatura;

	@NotNull
	@Size(max = 50)
	private String descripcion;
}