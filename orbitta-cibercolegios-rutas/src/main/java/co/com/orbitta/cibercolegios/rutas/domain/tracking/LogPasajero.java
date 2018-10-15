package co.com.orbitta.cibercolegios.rutas.domain.tracking;

import java.math.BigDecimal;

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

import co.com.orbitta.cibercolegios.rutas.domain.EstadoPasajero;
import co.com.orbitta.cibercolegios.rutas.domain.Ruta;
import co.com.orbitta.commons.domain.SimpleAuditableEntity;
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

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "ID_RUTA", nullable = false)
	@NotNull
	private Ruta ruta;
	
	@Column(name = "ID_USUARIO", nullable = false, unique = true)
	@NotNull
	private int usuarioId;

	@Column(name = "SENTIDO", nullable = false)
	@NotNull
	private int sentido;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "ID_ESTADO_PASAJERO", nullable = false)
	@NotNull
	private EstadoPasajero estado;

	@Column(name = "x", nullable = false, precision = 9, scale = 6)
	@NotNull
	private BigDecimal x;

	@Column(name = "y", nullable = false, precision = 9, scale = 6)
	@NotNull
	private BigDecimal y;
}
