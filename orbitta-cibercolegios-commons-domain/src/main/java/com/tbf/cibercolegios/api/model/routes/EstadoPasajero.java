package com.tbf.cibercolegios.api.model.routes;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicUpdate;

import com.tbf.cibercolegios.api.routes.model.enums.PassengerTypeStatus;

import co.com.orbitta.core.data.jpa.domain.AuditableEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "ESTADOS_PASAJERO")
@DynamicUpdate
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "ID_ESTADO_PASAJERO"))
public class EstadoPasajero extends AuditableEntity<Integer> {

	@Column(name = "DESCRIPCION", length = 50, nullable = false)
	@NotNull
	@Size(max = 50)
	private String descripcion;

	@Enumerated(EnumType.STRING)
	@Column(name = "TIPO", length = 50, nullable = false)
	@NotNull
	private PassengerTypeStatus tipo;

	@Column(name = "APLICA_SENTIDO_IDA", nullable = false)
	private boolean aplicaSentidoIda;

	@Column(name = "APLICA_SENTIDO_RETORNO", nullable = false)
	private boolean aplicaSentidoRetorno;

}