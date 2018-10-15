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

	private Integer estadoRutaId;

	private String estadoRutaNombre;

	private TipoEstadoRutaEnum tipoEstadoRuta;

	private BigDecimal rutaX;

	private BigDecimal rutaY;

	private int totalParadas;

	private int paradaActual;
	
	private int secuencia;

	private Integer estudianteId;

	private String estudianteNombres;

	private String estudianteApellidos;

	private int estadoEstudianteId;

	private String estadoEstudianteNombre;

	private TipoEstadoPasajeroEnum tipoEstadoEstudiante;

	public Boolean isFinalizado() {
		return (tipoEstadoEstudiante == null) ? false : tipoEstadoEstudiante.isFinalizado();
	}

	private String ciudadNombre;
	
	private String direccion;

	private BigDecimal paradaX;

	private BigDecimal paradaY;
}
