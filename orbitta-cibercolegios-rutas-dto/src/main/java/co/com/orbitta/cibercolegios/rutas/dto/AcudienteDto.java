package co.com.orbitta.cibercolegios.rutas.dto;

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
public class AcudienteDto extends AuditableEntityDto<Integer> {

	private int usuarioId;

	@Size(max = 200)
	private String token;
}
