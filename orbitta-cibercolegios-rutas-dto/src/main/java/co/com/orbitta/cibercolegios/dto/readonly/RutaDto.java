package co.com.orbitta.cibercolegios.dto.readonly;

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
public class RutaDto implements EntityDto<Integer> {

	private static final long serialVersionUID = 1L;

	private Integer id;

	@NotNull
	@Size(max = 2)
	private String codigo;

	@NotNull
	@Size(max = 100)
	private String descripcion;

	@NotNull
	@Size(max = 100)
	private String marca;

	@NotNull
	@Size(max = 100)
	private String placa;

	@NotNull
	@Size(max = 100)
	private String movil;

	private int institucionId;

	@NotNull
	private BigDecimal x;

	@NotNull
	private BigDecimal y;

	private int monitorId;

	private int conductorId;

	private int capacidad;
}
