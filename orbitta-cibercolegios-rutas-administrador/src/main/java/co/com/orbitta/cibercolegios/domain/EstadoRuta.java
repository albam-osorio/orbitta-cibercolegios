package co.com.orbitta.cibercolegios.domain;

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

import co.com.orbitta.cibercolegios.rutas.enums.TipoEstadoRutaEnum;
import co.com.orbitta.core.data.jpa.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "ESTADOS_RUTA")
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class EstadoRuta extends BaseEntity<Integer> {

	@Id
	@GeneratedValue(generator = "default_gen", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "default_gen", sequenceName = "DEFAULT_SEQ", allocationSize = 1)
	@Column(name = "ID_ESTADO_RUTA")
	@Setter(value = AccessLevel.PROTECTED)
	private Integer id;

	@Enumerated(EnumType.STRING)
	@Column(name = "TIPO", length = 50, nullable = false)
	@NotNull
	private TipoEstadoRutaEnum tipo;

	@Column(name = "DESCRIPCION", length = 50, nullable = false)
	@NotNull
	@Size(max = 50)
	private String descripcion;

	@Column(name = "PREDETERMINADO", nullable = false)
	private boolean predeterminado;

	@Builder
	public EstadoRuta(Integer id, @NotNull TipoEstadoRutaEnum tipo, @NotNull @Size(max = 50) String descripcion,
			boolean predeterminado) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.descripcion = descripcion;
		this.predeterminado = predeterminado;
	}
}
