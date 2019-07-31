package com.tbf.cibercolegios.api.model.routes;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicUpdate;

import com.tbf.cibercolegios.api.core.data.jpa.entities.SimpleAuditableEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "LOGS_PASAJERO")
@DynamicUpdate
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class LogPasajero extends SimpleAuditableEntity<Integer> {

	@Id
	@GeneratedValue(generator = "default_gen", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "default_gen", sequenceName = "DEFAULT_SEQ", allocationSize = 1)
	@Column(name = "ID_LOG_PASAJERO")
	@Setter(value = AccessLevel.PROTECTED)
	private Integer id;

	@Column(name = "ID_USUARIO", nullable = false)
	private int usuarioId;

	@Column(name = "SENTIDO", nullable = false)
	private int sentido;

	@Column(name = "ID_DIRECCION", nullable = false)
	private int direccionId;

	@Column(name = "ID_RUTA", nullable = false)
	private int rutaId;

	@Column(name = "SECUENCIA", nullable = false)
	private int secuencia;

	@Column(name = "ID_ESTADO_PASAJERO", nullable = false)
	private int estadoId;

	@Column(name = "x", nullable = false, precision = 9, scale = 6)
	@NotNull
	private BigDecimal x;

	@Column(name = "y", nullable = false, precision = 9, scale = 6)
	@NotNull
	private BigDecimal y;
}
