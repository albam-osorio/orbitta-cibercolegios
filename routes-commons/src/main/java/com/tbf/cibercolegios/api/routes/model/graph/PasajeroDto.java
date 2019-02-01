package com.tbf.cibercolegios.api.routes.model.graph;

import java.util.List;

import javax.validation.constraints.Size;

import com.tbf.cibercolegios.api.core.model.graph.AuditableEntityDto;
import com.tbf.cibercolegios.api.model.routes.enums.PassengerTypeStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class PasajeroDto extends AuditableEntityDto<Integer> {

	public static final int ESTADO_INACTIVO = 0;
	
	private Integer rutaId;

	private int usuarioId;

	private int secuenciaIda;

	private Integer direccionIdaId;

	private int secuenciaRetorno;

	private Integer direccionRetornoId;

	private int estadoId;

	private PassengerTypeStatus tipoEstado;

	@Size(max = 50)
	private String estadoDescripcion;

	private List<Integer> acudientes;
}
