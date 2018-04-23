package co.com.orbitta.cibercolegios.dto.tracking.monitor;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DatosPasajeroDto {
	private Integer usuarioRutaId;

	private Integer usuarioId;

	private String nombres;

	private String apellidos;

	private int secuencia;

	private String direccion;

	private BigDecimal x;

	private BigDecimal y;
	
	private int estadoId;
	
	private String estadoDescripcion;
}
