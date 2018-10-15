package co.com.orbitta.cibercolegios.rutas.domain.readonly;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Immutable;

import co.com.orbitta.core.data.jpa.domain.ReadOnlyEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "CURSOS")
@Immutable
@AttributeOverride(name = "id", column = @Column(name = "ID_CURSO"))
@Getter
@ToString(callSuper = true)
@NoArgsConstructor
public class Curso extends ReadOnlyEntity<Integer> {

	@Column(name = "ID_GRADO_ACADEMICO", nullable = false)
	@NotNull
	private int gradoAcademicoId;

	@Column(name = "NOMBRE", length = 100, nullable = false)
	@NotNull
	@Size(max = 100)
	private String nombre;
}