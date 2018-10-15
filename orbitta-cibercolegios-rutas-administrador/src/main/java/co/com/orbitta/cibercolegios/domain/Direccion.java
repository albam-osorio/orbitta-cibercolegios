package co.com.orbitta.cibercolegios.domain;

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
@Table(name = "DIRECCIONES_X_USUARIO")
@AttributeOverride(name = "id", column = @Column(name = "ID_DIRECCIONES_X_USUARIO"))
@Getter
@ToString(callSuper = true)
@NoArgsConstructor
public class Direccion extends ReadOnlyEntity<Integer> {

	@Column(name = "ID_USUARIO", nullable = false)
	@NotNull
	private int usuarioId;

	@Column(name = "DESCRIPCION", length = 100, nullable = false)
	@NotNull
	@Size(max = 100)
	private String descripcion;

	@Column(name = "UBICACIONLON", nullable = false, precision = 9, scale = 6)
	@NotNull
	private BigDecimal x;

	@Column(name = "UBICACIONLAT", nullable = false, precision = 9, scale = 6)
	@NotNull
	private BigDecimal y;

	@Builder
	public Direccion(Integer id, @NotNull int usuarioId, @NotNull @Size(max = 100) String descripcion,
			@NotNull BigDecimal x, @NotNull BigDecimal y) {
		super(id);
		this.usuarioId = usuarioId;
		this.descripcion = descripcion;
		this.x = x;
		this.y = y;
	}
}