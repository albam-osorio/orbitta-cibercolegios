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
public class LogUsuarioDto implements EntityDto<Integer> {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private int usuarioRutaId;

	private int sentido;

	private LocalDateTime fechaHora;

	private int estadoId;

	private int logId;
}
