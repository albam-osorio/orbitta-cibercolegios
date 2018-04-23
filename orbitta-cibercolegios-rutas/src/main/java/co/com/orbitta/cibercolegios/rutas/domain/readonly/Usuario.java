package co.com.orbitta.cibercolegios.rutas.domain.readonly;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "USUARIOS")
@AttributeOverride(name = "id", column = @Column(name = "ID_USUARIO"))
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Usuario extends ReadOnlyEntity<Integer> {

	private static final long serialVersionUID = 1L;

	@Column(name = "NOMBRE", length = 50, nullable = false)
	@NotNull
	@Size(max = 50)
	private String nombre;
	
	@Column(name = "APELLIDO", length = 50, nullable = false)
	@NotNull
	@Size(max = 50)
	private String apellido;
	
	@Column(name = "GENERO", length = 1, nullable = false)
	@NotNull
	@Size(max = 1)
	private String genero;

	@Column(name = "EMAIL", length = 50, nullable = false)
	@NotNull
	@Size(max = 100)
	private String email;
}
