package co.com.orbitta.cibercolegios.rutas.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import co.com.orbitta.commons.dto.EntityDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class LogRutaDto extends EntityDto<Integer> {

	private int rutaId;

	private LocalDateTime fechaHora;

	private int sentido;

	private int estadoId;

	@NotNull
	private BigDecimal x;

	@NotNull
	private BigDecimal y;

	@Builder
	public LogRutaDto(Integer id, int rutaId, LocalDateTime fechaHora, int sentido, int estadoId, @NotNull BigDecimal x,
			@NotNull BigDecimal y) {
		super(id);
		this.rutaId = rutaId;
		this.fechaHora = fechaHora;
		this.sentido = sentido;
		this.estadoId = estadoId;
		this.x = x;
		this.y = y;
	}
}
