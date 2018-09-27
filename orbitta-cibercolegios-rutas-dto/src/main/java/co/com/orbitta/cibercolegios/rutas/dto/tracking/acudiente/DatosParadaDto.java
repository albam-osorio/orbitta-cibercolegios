package co.com.orbitta.cibercolegios.rutas.dto.tracking.acudiente;

import java.math.BigDecimal;

import co.com.orbitta.cibercolegios.rutas.dto.tracking.AbstractDatosRutaDto;
import co.com.orbitta.cibercolegios.rutas.enums.TipoEstadoPasajeroEnum;
import co.com.orbitta.cibercolegios.rutas.enums.TipoEstadoRutaEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DatosParadaDto extends AbstractDatosRutaDto {
	
	

	private Integer estudianteId;

	private int secuencia;

	private String estudianteNombres;

	private String estudianteApellidos;

	
	private Integer estadoRutaId;

	private String estadoRutaNombre;

	private TipoEstadoRutaEnum tipoEstadoRuta;

	private BigDecimal rutaX;

	private BigDecimal rutaY;

	
	private int estadoEstudianteId;

	private String estadoEstudianteNombre;

	private TipoEstadoPasajeroEnum tipoEstadoEstudiante;

	public boolean isFinalizado() {
		return (tipoEstadoEstudiante == null) ? null : tipoEstadoEstudiante.isFinalizado();
	}

	private String direccion;

	private BigDecimal paradaX;

	private BigDecimal paradaY;
	
	
	private int totalParadas;

	private int paradaActual;

}
