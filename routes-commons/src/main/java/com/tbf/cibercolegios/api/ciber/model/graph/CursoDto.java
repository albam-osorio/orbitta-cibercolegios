package com.tbf.cibercolegios.api.ciber.model.graph;

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
public class CursoDto {

	private int id;

	@NotNull
	@Size(max = 100)
	private String descripcion;
}