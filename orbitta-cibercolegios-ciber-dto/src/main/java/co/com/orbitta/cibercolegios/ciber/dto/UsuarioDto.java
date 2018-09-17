package co.com.orbitta.cibercolegios.ciber.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import co.com.orbitta.commons.dto.EntityDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@NoArgsConstructor
public class UsuarioDto extends EntityDto<Integer> {

	@NotNull
	@Size(max = 50)
	private String nombre;

	@NotNull
	@Size(max = 50)
	private String apellido;

	@Size(max = 100)
	private String email;

	@NotNull
	@Size(max = 1)
	private String genero;


	@Builder
	public UsuarioDto(Integer id, @NotNull @Size(max = 50) String nombre, @NotNull @Size(max = 50) String apellido,
			@NotNull @Size(max = 1) String genero, @NotNull @Size(max = 100) String email) {
		super(id);
		this.nombre = nombre;
		this.apellido = apellido;
		this.genero = genero;
		this.email = email;
	}
}
