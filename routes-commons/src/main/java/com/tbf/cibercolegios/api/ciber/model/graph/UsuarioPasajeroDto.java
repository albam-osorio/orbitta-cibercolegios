package com.tbf.cibercolegios.api.ciber.model.graph;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class UsuarioPasajeroDto extends UsuarioDto {

	private String telefonoDomicilio;

	private String direccionDomicilio;

	private String email;
	
	private Integer pasajeroId;
}
