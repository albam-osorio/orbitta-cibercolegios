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
public class UsuarioDto extends EntityDto<Integer> {

	@NotNull
	@Size(max = 16)
	private String login;

	private int tipoId;

	@NotNull
	@Size(max = 30)
	private String numeroId;

	@NotNull
	@Size(max = 50)
	private String nombre;

	@NotNull
	@Size(max = 50)
	private String apellido;

	public String getNombreCompleto() {
		return getApellido() + " " + getNombre();
	}
}
