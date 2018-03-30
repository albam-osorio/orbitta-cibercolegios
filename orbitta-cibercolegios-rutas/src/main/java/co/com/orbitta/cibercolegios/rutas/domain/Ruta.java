package co.com.orbitta.cibercolegios.rutas.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import co.com.orbitta.core.data.jpa.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "ruta")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Ruta extends BaseEntity<Integer> {

	private static final long serialVersionUID = 1L;

	@Column(name = "codRuta", length = 2, nullable = false)
	@NotNull
	@Size(max = 2)
	private String codRuta;

	@Column(name = "descripcion", length = 100, nullable = false)
	@NotNull
	@Size(max = 100)
	private String descripcion;
	
	@Column(name = "marca", length = 100, nullable = false)
	@NotNull
	@Size(max = 100)
	private String marca;
	
	@Column(name = "placa", length = 100, nullable = false)
	@NotNull
	@Size(max = 100)
	private String placa;
	
	@Column(name = "movil", length = 100, nullable = false)
	@NotNull
	@Size(max = 100)
	private String movil;
	
	@Column(name = "capacidad", nullable = false)
	private int capacidad;
	
	@Column(name = "estudiante", nullable = false)
	private int estudiante;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "institucionId")
	private Institucion institucion;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "monitorId")
	private Usuario monitor;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "conductorId")
	private Usuario conductor;
}
