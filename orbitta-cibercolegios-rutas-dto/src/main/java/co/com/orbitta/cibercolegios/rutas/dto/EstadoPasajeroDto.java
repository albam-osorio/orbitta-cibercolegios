package co.com.orbitta.cibercolegios.rutas.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import co.com.orbitta.cibercolegios.rutas.enums.TipoEstadoPasajeroEnum;
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
public class EstadoPasajeroDto extends EntityDto<Integer> {

	@NotNull
	private TipoEstadoPasajeroEnum tipo;

	@NotNull
	@Size(max = 50)
	private String descripcion;

	private boolean aplicaSentidoIda;

	private boolean aplicaSentidoRetorno;
	
	public boolean isFinalizado() {
		return TipoEstadoPasajeroEnum.FIN.equals(tipo);
	}

	@Builder
	public EstadoPasajeroDto(Integer id, @NotNull TipoEstadoPasajeroEnum tipo,
			@NotNull @Size(max = 50) String descripcion, boolean aplicaSentidoIda, boolean aplicaSentidoRetorno) {
		super(id);
		this.tipo = tipo;
		this.descripcion = descripcion;
		this.aplicaSentidoIda = aplicaSentidoIda;
		this.aplicaSentidoRetorno = aplicaSentidoRetorno;
	}
}