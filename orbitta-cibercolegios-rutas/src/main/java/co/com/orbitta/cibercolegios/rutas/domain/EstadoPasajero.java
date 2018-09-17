package co.com.orbitta.cibercolegios.rutas.domain;

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

import co.com.orbitta.cibercolegios.rutas.enums.TipoEstadoPasajeroEnum;
import co.com.orbitta.commons.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "ESTADOS_PASAJERO")
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class EstadoPasajero extends BaseEntity<Integer> {

	@Id
	@GeneratedValue(generator = "estado_pasajero_gen", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "estado_pasajero_gen", sequenceName = "ESTADOS_PASAJERO_SEQ", allocationSize = 1)
	@Column(name = "ID_ESTADO_PASAJERO")
	@Setter(value = AccessLevel.PROTECTED)
	private Integer id;

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

	@Builder
	public EstadoPasajero(Integer id, @NotNull TipoEstadoPasajeroEnum tipo, @NotNull @Size(max = 50) String descripcion,
			boolean aplicaSentidoIda, boolean aplicaSentidoRetorno) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.descripcion = descripcion;
		this.aplicaSentidoIda = aplicaSentidoIda;
		this.aplicaSentidoRetorno = aplicaSentidoRetorno;
	}
}