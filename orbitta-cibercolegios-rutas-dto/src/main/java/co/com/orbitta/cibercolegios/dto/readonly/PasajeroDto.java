package co.com.orbitta.cibercolegios.dto.readonly;

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
public class PasajeroDto implements EntityDto<Integer> {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private int rutaId;

	private int usuarioId;

	private int direccionId;

	private LocalDateTime fechaHora;
	
	private int secuencia;
}
