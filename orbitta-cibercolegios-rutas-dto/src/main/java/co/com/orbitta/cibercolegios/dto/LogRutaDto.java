package co.com.orbitta.cibercolegios.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import co.com.orbitta.cibercolegios.core.dto.EntityDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogRutaDto implements EntityDto<Integer> {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private int rutaId;

	private int sentido;

	private LocalDateTime fechaHora;

	private int estadoId;

	@NotNull
	private BigDecimal x;

	@NotNull
	private BigDecimal y;
}
