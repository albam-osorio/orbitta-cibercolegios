package co.com.orbitta.cibercolegios.rutas.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import co.com.orbitta.cibercolegios.rutas.enums.TipoEstadoPasajeroEnum;
import co.com.orbitta.commons.dto.EntityDto;
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
	
	@NotNull
	@Size(max = 50)
	private String estadoNombre;
	
	@NotNull
	private TipoEstadoPasajeroEnum tipoEstado;
}
