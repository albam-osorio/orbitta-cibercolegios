package com.tbf.cibercolegios.api.model.routes;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import com.tbf.cibercolegios.api.core.data.jpa.entities.AuditableEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "RUTAS")
@DynamicUpdate
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "ID_RUTA"))
public class Ruta extends AuditableEntity<Integer> {

	@Column(name = "CODIGO", length = 3, nullable = false)
	@NotNull
	@Size(max = 3)
	private String codigo;

	@Column(name = "DESCRIPCION", length = 100, nullable = false)
	@NotNull
	@Size(max = 100)
	private String descripcion;

	@Column(name = "MARCA", length = 100, nullable = false)
	@NotNull
	@Size(max = 100)
	private String marca;

	@Column(name = "PLACA", length = 20, nullable = false)
	@NotNull
	@Size(max = 20)
	private String placa;

	@Column(name = "CAPACIDAD_MAXIMA", nullable = false)
	private int capacidadMaxima;

	@Column(name = "ID_INSTITUCION", nullable = false)
	private int institucionId;

	@Column(name = "ID_DIRECCION_SEDE", nullable = false)
	private int direccionSedeId;

	@Column(name = "CONDUCTOR_NOMBRES", length = 100, nullable = false)
	@NotNull
	@Size(max = 100)
	private String conductorNombres;

	@Column(name = "ID_MONITOR", nullable = false)
	private int monitorId;
	
	@Column(name = "MOVIL", length = 100, nullable = false)
	@NotNull
	@Size(max = 100)
	private String movil;

	@Column(name = "TOKEN", length = 1024, nullable = true)
	@Size(max = 1024)
	private String token;

	@Column(name = "FECHA_ULTIMO_EVENTO", nullable = true)
	@DateTimeFormat(style = "M-")
	private LocalDateTime fechaUltimoEvento;
	
	@Column(name = "SENTIDO", nullable = true)
	private Integer sentido;

	@Column(name = "ID_ESTADO_RUTA", nullable = true)
	private Integer estadoId;

	@Column(name = "X", nullable = true, precision = 9, scale = 6)
	private BigDecimal x;

	@Column(name = "Y", nullable = true, precision = 9, scale = 6)
	private BigDecimal y;
}
