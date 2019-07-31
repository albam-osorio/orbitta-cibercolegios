package com.tbf.cibercolegios.api.model.routes;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.tbf.cibercolegios.api.core.data.jpa.entities.AuditableEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "PASAJEROS_ACUDIENTES")
@DynamicUpdate
@Getter
@Setter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "ID_PASAJERO_ACUDIENTE"))
public class PasajeroAcudiente extends AuditableEntity<Integer> {

	@Column(name = "ID_PASAJERO", nullable = false)
	private int pasajeroId;

	@Column(name = "ID_ACUDIENTE", nullable = false)
	private int acudienteId;
}
