package com.tbf.cibercolegios.api.model.routes;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import com.tbf.cibercolegios.api.core.data.jpa.entities.AuditableEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "PASAJEROS")
@DynamicUpdate
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "ID_PASAJERO"))
public class Pasajero extends AuditableEntity<Integer> {

	@Column(name = "ID_INSTITUCION", nullable = false)
	private int institucionId;

	@Column(name = "ID_USUARIO", nullable = false)
	private int usuarioId;

	@Column(name = "ID_ESTADO_PASAJERO", nullable = false)
	private int estadoId;

	@Column(name = "FECHA_ULTIMO_EVENTO")
	@DateTimeFormat(style = "M-")
	private LocalDateTime fechaUltimoEvento;

	@Column(name = "ID_RUTA", nullable = true)
	private Integer rutaId;

	@Column(name = "SECUENCIA", nullable = true)
	private Integer secuencia;

	@Column(name = "SENTIDO", nullable = true)
	private Integer sentido;

	@Column(name = "ID_DIRECCION", nullable = true)
	private Integer direccionId;

	@Column(name = "x", nullable = true, precision = 9, scale = 6)
	private BigDecimal x;

	@Column(name = "y", nullable = true, precision = 9, scale = 6)
	private BigDecimal y;
}
