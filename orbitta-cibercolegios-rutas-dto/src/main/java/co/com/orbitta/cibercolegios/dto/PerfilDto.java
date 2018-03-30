package co.com.orbitta.cibercolegios.dto;

import co.com.orbitta.cibercolegios.core.dto.EntityDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PerfilDto implements EntityDto<Integer> {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private int tipoPerfilId;

	private int usuarioId;
	
	private int perfilPadreId;
	
	private int perfilMadreId;
}
