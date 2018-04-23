package co.com.orbitta.cibercolegios.rutas.domain.readonly;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "INSTITUCION")
@AttributeOverride(name = "id", column = @Column(name = "ID_INSTITUCION"))
@Getter
@Setter(value = AccessLevel.PROTECTED)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Institucion extends ReadOnlyEntity<Integer> {

	private static final long serialVersionUID = 1L;

	@Column(name = "DESCRIPCION", length = 100, nullable = false)
	@NotNull
	@Size(max = 100)
	private String descripcion;
}
