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

import com.tbf.cibercolegios.api.core.data.jpa.entities.SimpleAuditableEntity;
import com.tbf.cibercolegios.api.model.routes.enums.IssuingMessage;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "MENSAJES")
@DynamicUpdate
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class Mensaje extends SimpleAuditableEntity<Integer> {

	@Id
	@GeneratedValue(generator = "default_gen", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "default_gen", sequenceName = "DEFAULT_SEQ", allocationSize = 1)
	@Column(name = "ID_MENSAJE")
	@Setter(value = AccessLevel.PROTECTED)
	private Integer id;
	
	@Column(name = "ID_CONVERSACION", nullable = false)
	private int conversacionId;

	@Column(name = "ID_MONITOR", nullable = false)
	private int monitorId;

	@Enumerated(EnumType.STRING)
	@Column(name = "ORIGEN", length = 50, nullable = false)
	@NotNull
	private IssuingMessage origen;

	@Column(name = "MENSAJE", length = 200, nullable = false)
	@NotNull
	@Size(max = 200)
	private String mensaje;
}
