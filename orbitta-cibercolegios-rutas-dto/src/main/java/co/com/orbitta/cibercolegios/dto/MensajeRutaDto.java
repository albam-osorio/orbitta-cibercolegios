package co.com.orbitta.cibercolegios.dto;

import java.time.LocalDateTime;

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
public class MensajeRutaDto implements EntityDto<Integer> {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private LocalDateTime fecha;

	@NotNull
	@Size(max = 200)
	private String mensaje;
	
	private int origenMensaje;
	
	private int padreId;
	
	private int monitorId;
	
	private int rutaId;
	
	private int estadoId;
}
