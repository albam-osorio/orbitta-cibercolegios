package com.tbf.cibercolegios.api.ciber.model.graph;

import com.tbf.cibercolegios.api.core.model.graph.EntityDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class UsuarioPerfilDto extends EntityDto<Integer> {

	private byte[] foto;
	
	private String tipoDocumento;

	private String telefonoDomicilio;

	private String direccionDomicilio;

	private String email;

}
