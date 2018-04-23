package co.com.orbitta.cibercolegios.rutas.domain.readonly;

import java.math.BigDecimal;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import co.com.orbitta.core.data.jpa.domain.ReadOnlyEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "RUTA")
@AttributeOverride(name = "id", column = @Column(name = "ID_RUTA"))
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Ruta extends ReadOnlyEntity<Integer> {

	private static final long serialVersionUID = 1L;

	@Column(name = "CODIGO", length = 2, nullable = false)
	@NotNull
	@Size(max = 2)
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

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "ID_INSTITUCION", nullable = false)
	@NotNull
	private Institucion institucion;

	@Column(name = "X", nullable = false, precision = 9, scale = 6)
	@NotNull
	private BigDecimal x;

	@Column(name = "Y", nullable = false, precision = 9, scale = 6)
	@NotNull
	private BigDecimal y;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "ID_MONITOR", nullable = false)
	@NotNull
	private Usuario monitor;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "ID_CONDUCTOR", nullable = false)
	@NotNull
	private Usuario conductor;
	
	@Column(name = "CAPACIDAD", nullable = false)
	private int capacidad;
}
