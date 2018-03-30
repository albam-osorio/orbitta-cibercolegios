package co.com.orbitta.cibercolegios.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import co.com.orbitta.cibercolegios.core.dto.EntityDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UbicacionRutaDto implements EntityDto<Integer> {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private LocalDateTime fecha;

	@NotNull
	private BigDecimal ubicacionLat;
	
	@NotNull
	private BigDecimal ubicacionLon;
	
	private BigDecimal ubicacionGeo;
	
	private int sentido;
	
	private int rutaId;
	
	private int estadoId;
}
