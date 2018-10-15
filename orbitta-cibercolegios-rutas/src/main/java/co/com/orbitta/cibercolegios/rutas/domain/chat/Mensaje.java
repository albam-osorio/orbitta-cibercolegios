package co.com.orbitta.cibercolegios.rutas.domain.chat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicUpdate;

import co.com.orbitta.cibercolegios.rutas.enums.EmisorMensajeEnum;
import co.com.orbitta.commons.domain.SimpleAuditableEntity;
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

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "ID_CONVERSACION", nullable = false)
	@NotNull
	private Conversacion conversacion;

	@Column(name = "ID_MONITOR", nullable = false)
	@NotNull
	private int monitorId;

	@Enumerated(EnumType.STRING)
	@Column(name = "ORIGEN", length = 50, nullable = false)
	@NotNull
	private EmisorMensajeEnum origen;

	@Column(name = "MENSAJE", length = 200, nullable = false)
	@NotNull
	@Size(max = 200)
	private String mensaje;
}
