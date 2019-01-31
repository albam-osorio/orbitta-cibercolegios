package com.tbf.cibercolegios.api.routes.model.graph.readonly;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import co.com.orbitta.core.dto.EntityDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class GradoAcademicoDto extends EntityDto<Integer> {

	private int programaAcademicoId;

	@NotNull
	@Size(max = 100)
	private String nombre;
}