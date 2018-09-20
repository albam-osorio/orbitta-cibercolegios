package co.com.orbitta.cibercolegios.rutas.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import co.com.orbitta.cibercolegios.rutas.enums.TipoEstadoRutaEnum;
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
public class EstadoRutaDto extends EntityDto<Integer> {

	@NotNull
	private TipoEstadoRutaEnum tipo;

	@NotNull
	@Size(max = 50)
	private String descripcion;

	private boolean predeterminado;

	@Builder
	public EstadoRutaDto(Integer id, @NotNull TipoEstadoRutaEnum tipo, @NotNull @Size(max = 50) String descripcion,
			boolean predeterminado) {
		super(id);
		this.tipo = tipo;
		this.descripcion = descripcion;
		this.predeterminado = predeterminado;
	}
}