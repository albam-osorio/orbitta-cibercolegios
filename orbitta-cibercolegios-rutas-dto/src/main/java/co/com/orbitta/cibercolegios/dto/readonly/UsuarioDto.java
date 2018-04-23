package co.com.orbitta.cibercolegios.dto.readonly;

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
	@Size(max = 50)
	private String nombre;

	@NotNull
	@Size(max = 50)
	private String apellido;
	
	@NotNull
	@Size(max = 1)
	private String genero;

	@NotNull
	@Size(max = 100)
	private String email;
}
