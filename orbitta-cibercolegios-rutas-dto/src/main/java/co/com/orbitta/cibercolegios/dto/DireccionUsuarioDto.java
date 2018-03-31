package co.com.orbitta.cibercolegios.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import co.com.orbitta.cibercolegios.core.dto.EntityDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DireccionUsuarioDto implements EntityDto<Integer> {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private int usuarioId;

	@NotNull
	@Size(max = 100)
	private String descripcion;

	@NotNull
	private BigDecimal ubicacionLon;

	@NotNull
	private BigDecimal ubicacionLat;

	private BigDecimal ubicacionGeo;
}
