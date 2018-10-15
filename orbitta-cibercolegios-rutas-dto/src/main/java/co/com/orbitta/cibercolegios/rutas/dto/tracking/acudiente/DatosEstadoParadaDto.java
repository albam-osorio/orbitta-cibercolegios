package co.com.orbitta.cibercolegios.rutas.dto.tracking.acudiente;

import java.math.BigDecimal;

import co.com.orbitta.cibercolegios.rutas.enums.TipoEstadoPasajeroEnum;
import co.com.orbitta.cibercolegios.rutas.enums.TipoEstadoRutaEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DatosEstadoParadaDto {

	private Integer estadoRutaId;

	private String estadoRutaNombre;

	private TipoEstadoRutaEnum tipoEstadoRuta;

	private BigDecimal rutaX;

	private BigDecimal rutaY;

	
	private Integer estadoEstudianteId;

	private String estadoEstudianteNombre;

	private TipoEstadoPasajeroEnum tipoEstadoEstudiante;

	private BigDecimal paradaX;

	private BigDecimal paradaY;
	
	
	private BigDecimal institucionX;

	private BigDecimal institucionY;
}
