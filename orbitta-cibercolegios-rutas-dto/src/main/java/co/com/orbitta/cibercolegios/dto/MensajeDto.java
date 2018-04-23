package co.com.orbitta.cibercolegios.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import co.com.orbitta.cibercolegios.core.dto.EntityDto;
import co.com.orbitta.cibercolegios.enums.EmisorMensajeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MensajeDto implements EntityDto<Integer> {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private int conversacionId;

	private int rutaId;

	private int monitorId;

	@NotNull
	private EmisorMensajeEnum origen;

	@NotNull
	private LocalDateTime fechaHora;

	@NotNull
	@Size(max = 200)
	private String mensaje;
}
