package co.com.orbitta.cibercolegios.rutas.domain;

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

import co.com.orbitta.commons.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "LOGS_X_PASAJERO")
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class LogPasajero extends BaseEntity<Integer> {

	@Id
	@GeneratedValue(generator = "logs_x_pasajero_gen", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "logs_x_pasajero_gen", sequenceName = "LOGS_X_PASAJERO_SEQ", allocationSize = 1)
	@Column(name = "ID_LOG_PASAJERO")
	@Setter(value = AccessLevel.PROTECTED)
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "ID_USUARIO_RUTA", nullable = false)
	@NotNull
	private Pasajero pasajero;

	@Column(name = "FECHA_HORA", nullable = false)
	@NotNull
	private LocalDateTime fechaHora;

	@Column(name = "SENTIDO", nullable = false)
	@NotNull
	private int sentido;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "ID_ESTADO_PASAJERO", nullable = false)
	@NotNull
	private EstadoPasajero estado;

	@Builder
	public LogPasajero(Integer id, @NotNull Pasajero pasajero, @NotNull LocalDateTime fechaHora, @NotNull int sentido,
			@NotNull EstadoPasajero estado) {
		super();
		this.id = id;
		this.pasajero = pasajero;
		this.fechaHora = fechaHora;
		this.sentido = sentido;
		this.estado = estado;
	}
}
