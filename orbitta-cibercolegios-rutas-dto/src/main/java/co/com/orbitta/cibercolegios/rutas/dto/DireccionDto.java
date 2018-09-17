package co.com.orbitta.cibercolegios.rutas.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import co.com.orbitta.commons.dto.EntityDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@NoArgsConstructor
public class DireccionDto extends EntityDto<Integer> {

	private int usuarioId;

	@NotNull
	@Size(max = 100)
	private String descripcion;

	@NotNull
	private BigDecimal x;

	@NotNull
	private BigDecimal y;

	@Builder
	public DireccionDto(Integer id, int usuarioId, @NotNull @Size(max = 100) String descripcion, @NotNull BigDecimal x,
			@NotNull BigDecimal y) {
		super(id);
		this.usuarioId = usuarioId;
		this.descripcion = descripcion;
		this.x = x;
		this.y = y;
	}
}
