package com.tbf.cibercolegios.api.routes.model.graph;

import com.tbf.cibercolegios.api.core.model.graph.AuditableEntityDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class PasajeroDireccionDto extends AuditableEntityDto<Integer> {

	private int pasajeroId;

	private int correlacion;

	private int sentido;

	private int direccionId;

	private boolean activo;

	private Integer rutaId;

	private Integer secuencia;
}
