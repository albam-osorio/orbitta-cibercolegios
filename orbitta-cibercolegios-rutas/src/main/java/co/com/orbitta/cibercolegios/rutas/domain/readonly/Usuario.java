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
@Table(name = "USUARIOS")
@Immutable
@AttributeOverride(name = "id", column = @Column(name = "ID_USUARIO"))
@Getter
@ToString(callSuper = true)
@NoArgsConstructor
public class Usuario extends ReadOnlyEntity<Integer> {

	@Column(name = "NOMBRE", length = 50, nullable = false)
	@NotNull
	@Size(max = 50)
	private String nombre;

	@Column(name = "APELLIDO", length = 50, nullable = false)
	@NotNull
	@Size(max = 50)
	private String apellido;

}
