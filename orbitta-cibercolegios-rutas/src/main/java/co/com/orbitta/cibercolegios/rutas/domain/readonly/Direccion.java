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
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "USUARIO_DIRECCION")
@AttributeOverride(name = "id", column = @Column(name = "ID_USUARIO_DIRECCION"))
@Getter
@Setter(value = AccessLevel.PROTECTED)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Direccion extends ReadOnlyEntity<Integer> {

	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "ID_USUARIO", nullable = false)
	@NotNull
	private Usuario usuario;

	@Column(name = "DESCRIPCION", length = 100, nullable = false)
	@NotNull
	@Size(max = 100)
	private String descripcion;

	@Column(name = "X", nullable = false, precision = 9, scale = 6)
	@NotNull
	private BigDecimal x;

	@Column(name = "Y", nullable = false, precision = 9, scale = 6)
	@NotNull
	private BigDecimal y;
}
