package co.com.orbitta.cibercolegios.rutas.dto.tracking;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import co.com.orbitta.cibercolegios.rutas.enums.TipoEstadoRutaEnum;
import co.com.orbitta.commons.dto.SimpleAuditableEntityDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class LogRutaDto extends SimpleAuditableEntityDto<Integer> {

	private int rutaId;

	private int sentido;

	private int estadoId;

	@NotNull
	private TipoEstadoRutaEnum tipoEstado;

	@NotNull
	@Size(max = 50)
	private String estadoDescripcion;

	@NotNull
	private BigDecimal x;

	@NotNull
	private BigDecimal y;

}