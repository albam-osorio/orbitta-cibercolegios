package co.com.orbitta.cibercolegios.rutas.domain;

import java.math.BigDecimal;

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
@Table(name = "direccionUsuario")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DireccionUsuario extends BaseEntity<Integer> {

	private static final long serialVersionUID = 1L;

	@Column(name = "descripcion", length = 100, nullable = false)
	@NotNull
	@Size(max = 100)
	private String descripcion;

	@Column(name = "ubicacionLat", nullable = false)
	@NotNull
	private BigDecimal ubicacionLat;

	@Column(name = "ubicacionLon", nullable = false)
	@NotNull
	private BigDecimal ubicacionLon;

	@Column(name = "ubicacionGeo")
	private BigDecimal ubicacionGeo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuarioId")
	private Usuario usuario;
}
