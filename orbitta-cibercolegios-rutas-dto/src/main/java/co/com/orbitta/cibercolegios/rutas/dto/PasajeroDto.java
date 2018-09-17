package co.com.orbitta.cibercolegios.rutas.dto;

import co.com.orbitta.commons.dto.EntityDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@NoArgsConstructor
public class PasajeroDto extends EntityDto<Integer> {

	private int rutaId;

	private int secuencia;

	private int usuarioId;

	private int direccionId;

	@Builder
	public PasajeroDto(Integer id, int rutaId, int secuencia, int usuarioId, int direccionId) {
		super(id);
		this.rutaId = rutaId;
		this.secuencia = secuencia;
		this.usuarioId = usuarioId;
		this.direccionId = direccionId;
	}
}
