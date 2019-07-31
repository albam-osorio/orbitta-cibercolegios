package com.tbf.cibercolegios.api.model.routes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.tbf.cibercolegios.api.core.data.jpa.entities.SimpleAuditableEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "CONVERSACIONES")
@DynamicUpdate
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class Conversacion extends SimpleAuditableEntity<Integer> {

	@Id
	@GeneratedValue(generator = "default_gen", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "default_gen", sequenceName = "DEFAULT_SEQ", allocationSize = 1)
	@Column(name = "ID_CONVERSACION")
	@Setter(value = AccessLevel.PROTECTED)
	private Integer id;

	@Column(name = "ID_RUTA", nullable = false)
	private int rutaId;

	@Column(name = "ID_USUARIO_ACUDIENTE", nullable = false)
	private int usuarioAcudienteId;

	@Column(name = "ID_USUARIO_PASAJERO", nullable = false)
	private int usuarioPasajeroId;
}