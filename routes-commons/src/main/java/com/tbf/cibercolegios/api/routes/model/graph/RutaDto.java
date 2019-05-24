package com.tbf.cibercolegios.api.routes.model.graph;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.tbf.cibercolegios.api.core.model.graph.AuditableEntityDto;
import com.tbf.cibercolegios.api.model.routes.enums.RouteTypeStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class RutaDto extends AuditableEntityDto<Integer> {

	public static final int SENTIDO_IDA = 1;

	public static final int SENTIDO_RETORNO = 2;

	public static final int ESTADO_INACTIVA = 0;

	private int institucionId;

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
	@Size(max = 100)
	private String placa;

	private Integer capacidadMaxima;

	@NotNull
	@Size(max = 100)
	private String movil;

	@NotNull
	@Size(max = 200)
	private String token;

	@NotNull
	@Size(max = 100)
	private String conductorNombres;

	@NotNull
	private Integer monitorId;

	@NotNull
	private Integer direccionSedeId;

	@DateTimeFormat(style = "M-")
	private LocalDate fechaUltimoRecorrido;

	private int sentido;	

	private int estadoId;

	@NotNull
	private RouteTypeStatus tipoEstado;

	@NotNull
	@Size(max = 50)
	private String estadoDescripcion;

	private BigDecimal x;

	private BigDecimal y;

}
