package co.com.orbitta.cibercolegios.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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

import co.com.orbitta.core.data.jpa.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "LOGS_X_RUTA")
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class LogRuta extends BaseEntity<Integer> {

	@Id
	@GeneratedValue(generator = "default_gen", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "default_gen", sequenceName = "DEFAULT_SEQ", allocationSize = 1)
	@Column(name = "ID_LOG_RUTA")
	@Setter(value = AccessLevel.PROTECTED)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "ID_RUTA", nullable = false)
	@NotNull
	private Ruta ruta;

	@Column(name = "FECHA_HORA", nullable = false)
	@NotNull
	private LocalDateTime fechaHora;

	@Column(name = "SENTIDO", nullable = false)
	@NotNull
	private int sentido;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "ID_ESTADO_RUTA", nullable = false)
	@NotNull
	private EstadoRuta estado;

	@Column(name = "x", nullable = false, precision = 9, scale = 6)
	@NotNull
	private BigDecimal x;

	@Column(name = "y", nullable = false, precision = 9, scale = 6)
	@NotNull
	private BigDecimal y;

	@Builder
	public LogRuta(Integer id, @NotNull Ruta ruta, @NotNull LocalDateTime fechaHora, @NotNull int sentido,
			@NotNull EstadoRuta estado, @NotNull BigDecimal x, @NotNull BigDecimal y) {
		super();
		this.id = id;
		this.ruta = ruta;
		this.fechaHora = fechaHora;
		this.sentido = sentido;
		this.estado = estado;
		this.x = x;
		this.y = y;
	}
}