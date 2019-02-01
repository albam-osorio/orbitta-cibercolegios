package com.tbf.cibercolegios.api.model.routes;

import java.math.BigDecimal;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicUpdate;

import com.tbf.cibercolegios.api.core.data.jpa.entities.AuditableEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "DIRECCIONES")
@DynamicUpdate
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "ID_DIRECCION"))
public class Direccion extends AuditableEntity<Integer> {

	@Column(name = "ID_INSTITUCION", nullable = false)
	@NotNull
	private int institucionId;

	@Column(name = "ID_ESTADO_DIRECCION", nullable = false)
	@NotNull
	private Integer estadoId;

	@Column(name = "ID_PAISES", nullable = false)
	private int paisId;
	
	@Column(name = "ID_ESTADO", nullable = false)
	private int departamentoId;
	
	@Column(name = "ID_CIUDAD", nullable = false)
	@NotNull
	private int ciudadId;

	@Column(name = "DIRECCION", length = 100, nullable = false)
	@NotNull
	@Size(max = 100)
	private String direccion;

	@Column(name = "X", nullable = true, precision = 9, scale = 6)
	private BigDecimal x;

	@Column(name = "Y", nullable = true, precision = 9, scale = 6)
	private BigDecimal y;
}