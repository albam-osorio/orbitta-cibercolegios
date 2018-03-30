package co.com.orbitta.cibercolegios.dto;

import java.time.LocalDateTime;

import co.com.orbitta.cibercolegios.core.dto.EntityDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioRutaDto implements EntityDto<Integer> {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private LocalDateTime fecha;
	
	private int usuarioId;
	
	private int rutaId;
	
	private int direccionUsuarioId;
}
