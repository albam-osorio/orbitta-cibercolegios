package co.com.orbitta.cibercolegios.rutas.dto.tracking.acudiente;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DatosEstadoParadaDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private int estadoRutaId;

	private String estadoRutaNombre;

	private int estadoPasajeroId;

	private String estadoPasajeroNombre;

	private BigDecimal x;

	private BigDecimal y;
}
