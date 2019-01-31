package com.tbf.cibercolegios.api.routes.model.graph.tracking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DatosAcudienteDto {

	private Integer usuarioId;

	private String nombres;

	private String apellidos;

	private String token;
}
