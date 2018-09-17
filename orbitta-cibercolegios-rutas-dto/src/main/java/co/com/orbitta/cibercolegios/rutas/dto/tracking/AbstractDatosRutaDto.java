package co.com.orbitta.cibercolegios.rutas.dto.tracking;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractDatosRutaDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private int rutaId;

	private String codigo;

	private String descripcion;

	private String marca;

	private String placa;

	private String movil;

	private int capacidad;

	private int institucionId;

	private String institucionNombre;

	private BigDecimal institucionX;

	private BigDecimal institucionY;

	private int monitorId;

	private String monitorNombres;

	private String monitorApellidos;

	private int conductorId;

	private String conductorNombres;

	private String conductorApellidos;

	private boolean activa;

}
