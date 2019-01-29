package co.com.orbitta.cibercolegios.rutas.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import co.com.orbitta.cibercolegios.rutas.enums.TipoEstadoPasajeroEnum;
import co.com.orbitta.core.dto.AuditableEntityDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class EstadoPasajeroDto extends AuditableEntityDto<Integer> {

	@NotNull
	@Size(max = 50)
	private String descripcion;

	@NotNull
	private TipoEstadoPasajeroEnum tipo;

	private boolean aplicaSentidoIda;

	private boolean aplicaSentidoRetorno;

	public boolean isFinalizado() {
		return TipoEstadoPasajeroEnum.FIN.equals(tipo);
	}
}