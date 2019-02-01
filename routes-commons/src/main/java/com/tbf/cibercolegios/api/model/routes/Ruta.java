package com.tbf.cibercolegios.api.model.routes;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

	@Column(name = "ID_INSTITUCION", nullable = false)
	@NotNull
	private int institucionId;

	@Column(name = "CODIGO", length = 10, nullable = false)
	@NotNull
	@Size(max = 10)
	private String codigo;

	@Column(name = "DESCRIPCION", length = 100, nullable = false)
	@NotNull
	@Size(max = 100)
	private String descripcion;

	@Column(name = "MARCA", length = 100, nullable = false)
	@NotNull
	@Size(max = 100)
	private String marca;

	@Column(name = "PLACA", length = 100, nullable = false)
	@NotNull
	@Size(max = 100)
	private String placa;

	@Column(name = "CAPACIDAD_MAXIMA", nullable = false)
	private int capacidadMaxima;

	@Column(name = "MOVIL", length = 100, nullable = false)
	@NotNull
	@Size(max = 100)
	private String movil;

	@Column(name = "TOKEN", length = 200, nullable = true)
	@Size(max = 200)
	private String token;

	@Column(name = "CONDUCTOR_NOMBRES", length = 100, nullable = false)
	@NotNull
	@Size(max = 100)
	private String conductorNombres;

	@Column(name = "ID_MONITOR", nullable = false)
	private int monitorId;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "ID_DIRECCION_SEDE", nullable = false)
	@NotNull
	private Direccion direccionSede;

	@Column(name = "FECHA_ULTIMO_RECORRIDO")
	@DateTimeFormat(style = "M-")
	private LocalDate fechaUltimoRecorrido;
	
	@Column(name = "SENTIDO", nullable = false)
	private int sentido;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "ID_ESTADO_RUTA", nullable = false)
	@NotNull
	private EstadoRuta estado;

	@Column(name = "X", nullable = true, precision = 9, scale = 6)
	private BigDecimal x;

	@Column(name = "Y", nullable = true, precision = 9, scale = 6)
	private BigDecimal y;
}