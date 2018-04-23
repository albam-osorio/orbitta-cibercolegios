package co.com.orbitta.cibercolegios.rutas.domain;

import java.time.LocalDateTime;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import co.com.orbitta.cibercolegios.rutas.domain.readonly.Pasajero;
import co.com.orbitta.core.data.jpa.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "RUTA_LOG_PASAJERO")
@SequenceGenerator(name = "ruta_log_pasajero_sequence", allocationSize = 1, sequenceName = "SEQ_RUTA_LOG_PASAJERO")
@AttributeOverride(name = "id", column = @Column(name = "ID_RUTA_LOG_PASAJERO"))
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LogPasajero extends BaseEntity<Integer> {

	private static final long serialVersionUID = 1L;

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
}
