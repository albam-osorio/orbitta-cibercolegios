package com.tbf.cibercolegios.api.routes.model.graph;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.tbf.cibercolegios.api.core.model.graph.AuditableEntityDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class RutaDto extends AuditableEntityDto<Integer> {

	@NotNull
	@Size(max = 3)
	private String codigo;

	@NotNull
	@Size(max = 100)
	private String descripcion;

	@NotNull
	@Size(max = 100)
	private String marca;

	@NotNull
	@Size(max = 20)
	private String placa;

	private Integer capacidadMaxima;

	private Integer institucionId;

	private Integer direccionSedeId;

	@NotNull
	@Size(max = 100)
	private String conductorNombres;

	@NotNull
	private Integer monitorId;

	@NotNull
	@Size(max = 100)
	private String movil;

	@NotNull
	@Size(max = 1024)
	private String token;

	@DateTimeFormat(style = "M-")
	private LocalDateTime fechaUltimoEvento;

	private Integer sentido;

	private Integer estadoId;

	private BigDecimal x;

	private BigDecimal y;

}
