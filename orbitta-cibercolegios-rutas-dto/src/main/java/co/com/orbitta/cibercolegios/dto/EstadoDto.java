package co.com.orbitta.cibercolegios.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import co.com.orbitta.cibercolegios.core.dto.EntityDto;
import co.com.orbitta.cibercolegios.enums.TipoEventoEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstadoDto implements EntityDto<Integer> {

	private static final long serialVersionUID = 1L;

	public static final String INICIANDO_RUTA_HACIA_EL_COLEGIO = "Iniciando ruta hacia el colegio";

	public static final String FINALIZANDO_RUTA_HACIA_EL_COLEGIO = "Finalizando ruta hacia el colegio";

	public static final String INICIANDO_RUTA_DESDE_EL_COLEGIO = "Iniciando ruta desde el colegio";

	public static final String FINALIZANDO_RUTA_DESDE_EL_COLEGIO = "Finalizando ruta desde el colegio";

	private Integer id;

	
	@NotNull
	private TipoEventoEnum tipoEvento;
    
	@NotNull
	@Size(max = 100)
	private String descripcion;
}
