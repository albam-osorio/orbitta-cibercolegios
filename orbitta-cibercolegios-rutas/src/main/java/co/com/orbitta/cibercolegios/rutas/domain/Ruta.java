package co.com.orbitta.cibercolegios.rutas.domain;

import java.math.BigDecimal;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import co.com.orbitta.core.data.jpa.domain.ReadOnlyEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "RUTA")
@AttributeOverride(name = "id", column = @Column(name = "ID_RUTA"))
@Getter
@ToString(callSuper = true)
@NoArgsConstructor
public class Ruta extends ReadOnlyEntity<Integer> {

	@Column(name = "CODRUTA", length = 2, nullable = false)
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

	@Column(name = "ID_INSTITUCION", nullable = false)
	@NotNull
	private int institucionId;

	@Column(name = "UBICACIONLON", nullable = false, precision = 9, scale = 6)
	@NotNull
	private BigDecimal x;

	@Column(name = "UBICACIONLAT", nullable = false, precision = 9, scale = 6)
	@NotNull
	private BigDecimal y;

	@Column(name = "ID_MONITOR", nullable = false)
	@NotNull
	private int monitorId;

	@Column(name = "ID_CONDUCTOR", nullable = false)
	@NotNull
	private int conductorId;

	@Column(name = "CAPACIDAD", nullable = false)
	private int capacidad;

	@Builder
	public Ruta(Integer id, @NotNull @Size(max = 2) String codigo, @NotNull @Size(max = 100) String descripcion,
			@NotNull @Size(max = 100) String marca, @NotNull @Size(max = 100) String placa,
			@NotNull @Size(max = 100) String movil, @NotNull int institucionId, @NotNull BigDecimal x,
			@NotNull BigDecimal y, @NotNull int monitorId, @NotNull int conductorId, int capacidad) {
		super(id);
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.marca = marca;
		this.placa = placa;
		this.movil = movil;
		this.institucionId = institucionId;
		this.x = x;
		this.y = y;
		this.monitorId = monitorId;
		this.conductorId = conductorId;
		this.capacidad = capacidad;
	}

}
