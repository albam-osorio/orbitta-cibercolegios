package co.com.orbitta.cibercolegios.rutas.dto.tracking;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractDatosRutaDto {

	private int rutaId;

	private int institucionId;

	private String institucionNombre;

	private BigDecimal institucionX;

	private BigDecimal institucionY;

	private String codigo;

	private String descripcion;

	private String marca;

	private String placa;

	private int capacidad;

	private String movil;

	private String token;

	private int conductorId;

	private String conductorNombres;

	private String conductorApellidos;

	private int monitorId;

	private String monitorNombres;

	private String monitorApellidos;

	private boolean activa;
	
}
