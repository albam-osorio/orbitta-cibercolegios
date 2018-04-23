package co.com.orbitta.cibercolegios.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import co.com.orbitta.cibercolegios.core.dto.EntityDto;
import co.com.orbitta.cibercolegios.enums.TipoEstadoPasajeroEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstadoPasajeroDto implements EntityDto<Integer> {

	private static final long serialVersionUID = 1L;

	private Integer id;

	@NotNull
	private TipoEstadoPasajeroEnum tipo;

	@NotNull
	@Size(max = 50)
	private String descripcion;
	
	private boolean aplicaIda;
	
	private boolean aplicaRetorno;
}


