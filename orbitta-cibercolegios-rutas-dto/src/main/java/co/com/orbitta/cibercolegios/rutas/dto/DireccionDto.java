package co.com.orbitta.cibercolegios.rutas.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import co.com.orbitta.commons.dto.AuditableEntityDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class DireccionDto extends AuditableEntityDto<Integer> {

	private int institucionId;

	private int estadoId;
	
	private int paisId;
	
	private int departamentoId;

	private int ciudadId;

	@NotNull
	@Size(max = 100)
	private String direccion;

	private BigDecimal x;

	private BigDecimal y;
}
