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
@Table(name = "USUARIOS")
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

	@Column(name = "EMAIL", length = 50, nullable = true)
	@Size(max = 50)
	private String email;

	@Column(name = "LOGIN", length = 16, nullable = true)
	@Size(max = 16)
	private String login;

	@Column(name = "GENERO", length = 1, nullable = true)
	@Size(max = 1)
	private String genero;

	@Builder
	public Usuario(Integer id, @NotNull @Size(max = 50) String nombre, @NotNull @Size(max = 50) String apellido,
			@Size(max = 50) String email, @Size(max = 16) String login, @Size(max = 1) String genero) {
		super(id);
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.login = login;
		this.genero = genero;
	}
}
