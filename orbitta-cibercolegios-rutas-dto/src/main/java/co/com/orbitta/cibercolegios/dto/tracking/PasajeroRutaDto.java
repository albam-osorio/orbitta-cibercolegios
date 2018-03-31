package co.com.orbitta.cibercolegios.dto.tracking;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PasajeroRutaDto {
	private Integer usuarioRutaId;
	
	private Integer usuarioId;

	private String nombre1;

	private String nombre2;

	private String apellido1;

	private String apellido2;
	
	private int secuencia;
	
	private String direccion;

	private BigDecimal ubicacionLat;

	private BigDecimal ubicacionLon;

	private BigDecimal ubicacionGeo;
}
