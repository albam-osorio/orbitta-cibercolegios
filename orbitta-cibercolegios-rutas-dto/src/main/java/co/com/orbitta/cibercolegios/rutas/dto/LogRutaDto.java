package co.com.orbitta.cibercolegios.rutas.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import co.com.orbitta.cibercolegios.rutas.enums.TipoEstadoRutaEnum;
import co.com.orbitta.commons.dto.EntityDto;
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
	@Size(max = 50)
	private String estadoNombre;
	
	@NotNull
	private TipoEstadoRutaEnum tipoEstado;

	@NotNull
	private BigDecimal x;

	@NotNull
	private BigDecimal y;

}
