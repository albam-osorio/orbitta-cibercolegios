package co.com.orbitta.cibercolegios.ciber.domain;

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
@Table(name = "INSTITUCIONES")
@AttributeOverride(name = "id", column = @Column(name = "ID_INSTITUCION"))
@Getter
@ToString(callSuper = true)
@NoArgsConstructor
public class Institucion extends ReadOnlyEntity<Integer> {

	@Column(name = "	NOMBRE", length = 100, nullable = false)
	@NotNull
	@Size(max = 100)
	private String nombre;

	@Builder
	public Institucion(Integer id, @NotNull @Size(max = 100) String nombre) {
		super(id);
		this.nombre = nombre;
	}
}