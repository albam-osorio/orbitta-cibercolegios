package co.com.orbitta.cibercolegios.rutas.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import co.com.orbitta.core.data.jpa.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Usuario extends BaseEntity<Integer> {

	private static final long serialVersionUID = 1L;

	@Column(name = "nombre1", length = 100, nullable = false)
	@NotNull
	@Size(max = 100)
	private String nombre1;
	
	@Column(name = "nombre2", length = 100, nullable = false)
	@NotNull
	@Size(max = 100)
	private String nombre2;
	
	@Column(name = "apellido1", length = 100, nullable = false)
	@NotNull
	@Size(max = 100)
	private String apellido1;
	
	@Column(name = "apellido2", length = 100, nullable = false)
	@NotNull
	@Size(max = 100)
	private String apellido2;
	
	@Column(name = "correo", length = 100, nullable = false)
	@NotNull
	@Size(max = 100)
	private String correo;

	@Column(name = "sexo", nullable = false)
	private int sexo;

}
