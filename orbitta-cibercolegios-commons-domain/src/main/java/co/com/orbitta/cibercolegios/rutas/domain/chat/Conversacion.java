package co.com.orbitta.cibercolegios.rutas.domain.chat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicUpdate;

import co.com.orbitta.cibercolegios.rutas.domain.Ruta;
import co.com.orbitta.core.data.jpa.domain.SimpleAuditableEntity;
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

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "ID_RUTA", nullable = false)
	@NotNull
	private Ruta ruta;

	@Column(name = "ID_USUARIO_ACUDIENTE", nullable = false)
	@NotNull
	private int usuarioAcudienteId;

	@Column(name = "ID_USUARIO_PASAJERO", nullable = false)
	@NotNull
	private int usuarioPasajeroId;
}