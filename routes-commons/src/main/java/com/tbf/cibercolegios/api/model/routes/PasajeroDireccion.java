package com.tbf.cibercolegios.api.model.routes;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.tbf.cibercolegios.api.core.data.jpa.entities.AuditableEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "PASAJEROS_DIRECCIONES")
@DynamicUpdate
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "ID_PASAJERO_DIRECCION"))
public class PasajeroDireccion extends AuditableEntity<Integer> {

	@Column(name = "ID_PASAJERO", nullable = false)
	private int pasajeroId;

	@Column(name = "CORRELACION", nullable = false)
	private int correlacion;

	@Column(name = "SENTIDO", nullable = false)
	private int sentido;

	@Column(name = "ID_DIRECCION", nullable = false)
	private int direccionId;

	@Column(name = "ACTIVO", nullable = false)
	private boolean activo;
	
	@Column(name = "ID_RUTA", nullable = true)
	private Integer rutaId;

	@Column(name = "SECUENCIA", nullable = true)
	private Integer secuencia;
}
