package co.com.orbitta.cibercolegios.rutas.dto;

import java.time.LocalDateTime;

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
public class LogPasajeroDto extends EntityDto<Integer> {

	private int pasajeroId;

	private LocalDateTime fechaHora;

	private int sentido;

	private int estadoId;

	@Builder
	public LogPasajeroDto(Integer id, int pasajeroId, LocalDateTime fechaHora, int sentido, int estadoId) {
		super(id);
		this.pasajeroId = pasajeroId;
		this.fechaHora = fechaHora;
		this.sentido = sentido;
		this.estadoId = estadoId;
	}
}
