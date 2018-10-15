package co.com.orbitta.cibercolegios.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import co.com.orbitta.core.data.jpa.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "RUTAS")
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class Ruta extends BaseEntity<Integer> {
	
	@Id
	@GeneratedValue(generator = "default_gen", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "default_gen", sequenceName = "DEFAULT_SEQ", allocationSize = 1)
	@Column(name = "ID_RUTA")
	@Setter(value = AccessLevel.PROTECTED)
	private Integer id;

	@Column(name = "ID_INSTITUCION", nullable = false)
	@NotNull
	private int institucionId;

	@Column(name = "CODIGO", length = 10, nullable = false)
	@NotNull
	@Size(max = 10)
	private String codigo;

	@Column(name = "DESCRIPCION", length = 100, nullable = false)
	@NotNull
	@Size(max = 100)
	private String descripcion;

	@Column(name = "MARCA", length = 100, nullable = false)
	@NotNull
	@Size(max = 100)
	private String marca;

	@Column(name = "PLACA", length = 100, nullable = false)
	@NotNull
	@Size(max = 100)
	private String placa;

	@Column(name = "MOVIL", length = 100, nullable = false)
	@NotNull
	@Size(max = 100)
	private String movil;

	@Column(name = "CONDUCTOR_NOMBRES", length = 100, nullable = false)
	@NotNull
	@Size(max = 100)
	private String conductorNombres;

	@Column(name = "ID_MONITOR", nullable = false)
	@NotNull
	private int monitorId;

	@Column(name = "CAPACIDAD_MAXIMA", nullable = false)
	private int capacidadMaxima;
}
