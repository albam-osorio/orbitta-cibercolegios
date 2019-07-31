package com.tbf.cibercolegios.api.model.routes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicUpdate;

import com.tbf.cibercolegios.api.core.data.jpa.entities.AuditableEntity;
import com.tbf.cibercolegios.api.model.routes.enums.RouteTypeStatus;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "ESTADOS_RUTA")
@DynamicUpdate
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class EstadoRuta extends AuditableEntity<Integer> {

	@Id
	@GeneratedValue(generator = "default_gen", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "default_gen", sequenceName = "DEFAULT_SEQ", allocationSize = 1)
	@Column(name = "ID_ESTADO_RUTA")
	@Setter(value = AccessLevel.PROTECTED)
	private Integer id;

	@Column(name = "DESCRIPCION", length = 50, nullable = false)
	@NotNull
	@Size(max = 50)
	private String descripcion;

	@Enumerated(EnumType.STRING)
	@Column(name = "TIPO", length = 50, nullable = false)
	@NotNull
	private RouteTypeStatus tipo;

	@Column(name = "PREDETERMINADO", nullable = false)
	private boolean predeterminado;

}
