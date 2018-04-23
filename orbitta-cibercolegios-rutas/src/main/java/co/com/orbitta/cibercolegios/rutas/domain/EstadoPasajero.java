package co.com.orbitta.cibercolegios.rutas.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import co.com.orbitta.cibercolegios.enums.TipoEstadoPasajeroEnum;
import co.com.orbitta.core.data.jpa.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "ESTADO_PASAJERO")
@SequenceGenerator(name = "estado_pasajero_sequence", allocationSize = 1, sequenceName = "SEQ_ESTADO_PASAJERO")
@AttributeOverride(name = "id", column = @Column(name = "ID_ESTADO_PASAJERO"))
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EstadoPasajero extends BaseEntity<Integer> {

	private static final long serialVersionUID = 1L;

	@Enumerated(EnumType.STRING)
	@Column(name = "TIPO", length = 50, nullable = false)
	@NotNull
	private TipoEstadoPasajeroEnum tipo;

	@Column(name = "DESCRIPCION", length = 50, nullable = false)
	@NotNull
	@Size(max = 50)
	private String descripcion;

	@Column(name = "APLICA_SENTIDO_IDA", nullable = false)
	private boolean aplicaSentidoIda;

	@Column(name = "APLICA_SENTIDO_RETORNO", nullable = false)
	private boolean aplicaSentidoRetorno;
}