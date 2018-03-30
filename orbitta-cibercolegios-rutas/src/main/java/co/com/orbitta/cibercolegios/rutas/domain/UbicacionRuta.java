package co.com.orbitta.cibercolegios.rutas.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import co.com.orbitta.core.data.jpa.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "ubicacionRuta")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UbicacionRuta extends BaseEntity<Integer> {

	private static final long serialVersionUID = 1L;

	@Column(name = "fecha")
	private LocalDateTime fecha;
	
	@Column(name = "ubicacionLat", nullable = false)
	@NotNull
	private BigDecimal ubicacionLat;
	
	@Column(name = "ubicacionLon", nullable = false)
	@NotNull
	private BigDecimal ubicacionLon;
	
	@Column(name = "ubicacionGeo")
	private BigDecimal ubicacionGeo;
	
	@Column(name = "sentido", nullable = false)
	private int sentido;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "rutaId")
	private Ruta ruta;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "estadoId")
	private Estado estado;
}
