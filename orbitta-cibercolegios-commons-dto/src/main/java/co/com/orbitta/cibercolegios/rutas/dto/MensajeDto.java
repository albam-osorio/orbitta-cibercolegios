package co.com.orbitta.cibercolegios.rutas.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import co.com.orbitta.cibercolegios.rutas.enums.EmisorMensajeEnum;
import co.com.orbitta.core.dto.SimpleAuditableEntityDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class MensajeDto extends SimpleAuditableEntityDto<Integer> {

	private int conversacionId;

	private int monitorId;

	@NotNull
	private EmisorMensajeEnum origen;

	@NotNull
	@Size(max = 200)
	private String mensaje;

}
