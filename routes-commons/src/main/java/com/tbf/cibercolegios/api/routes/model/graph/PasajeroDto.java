package com.tbf.cibercolegios.api.routes.model.graph;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.tbf.cibercolegios.api.core.model.graph.AuditableEntityDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class PasajeroDto extends AuditableEntityDto<Integer> {

	private int institucionId;

	private int usuarioId;

	private Integer estadoId;

	private LocalDateTime fechaUltimoEvento;

	private Integer rutaId;

	private Integer secuencia;

	private Integer sentido;

	private Integer direccionId;

	private BigDecimal x;

	private BigDecimal y;

}
