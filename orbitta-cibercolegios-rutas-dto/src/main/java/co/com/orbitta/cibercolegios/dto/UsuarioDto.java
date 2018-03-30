package co.com.orbitta.cibercolegios.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import co.com.orbitta.cibercolegios.core.dto.EntityDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioDto implements EntityDto<Integer> {

	private static final long serialVersionUID = 1L;

	private Integer id;

	@NotNull
	@Size(max = 100)
	private String nombre1;

	@NotNull
	@Size(max = 100)
	private String nombre2;

	@NotNull
	@Size(max = 100)
	private String apellido1;

	@NotNull
	@Size(max = 100)
	private String apellido2;

	@NotNull
	@Size(max = 100)
	private String correo;

	private int sexo;
}
