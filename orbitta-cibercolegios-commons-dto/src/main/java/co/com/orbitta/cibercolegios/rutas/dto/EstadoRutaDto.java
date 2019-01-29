package co.com.orbitta.cibercolegios.rutas.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import co.com.orbitta.cibercolegios.rutas.enums.TipoEstadoRutaEnum;
import co.com.orbitta.core.dto.AuditableEntityDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class EstadoRutaDto extends AuditableEntityDto<Integer> {

	@NotNull
	@Size(max = 50)
	private String descripcion;

	@NotNull
	private TipoEstadoRutaEnum tipo;

	private boolean predeterminado;

	public boolean isActivo() {
		switch (tipo) {
		case INICIO:
		case RECORRIDO:
			return true;
		default:
			return false;
		}
	}

	public boolean isFinalizado() {
		return TipoEstadoRutaEnum.FIN.equals(tipo);
	}
}