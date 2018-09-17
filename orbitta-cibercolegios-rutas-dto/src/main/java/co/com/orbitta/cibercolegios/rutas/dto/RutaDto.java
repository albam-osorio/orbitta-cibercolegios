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
public class RutaDto extends EntityDto<Integer> {

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

	@Builder
	public RutaDto(Integer id, @NotNull @Size(max = 2) String codigo, @NotNull @Size(max = 100) String descripcion,
			@NotNull @Size(max = 100) String marca, @NotNull @Size(max = 100) String placa,
			@NotNull @Size(max = 100) String movil, int institucionId, @NotNull BigDecimal x, @NotNull BigDecimal y,
			int monitorId, int conductorId, int capacidad) {
		super(id);
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.marca = marca;
		this.placa = placa;
		this.movil = movil;
		this.institucionId = institucionId;
		this.x = x;
		this.y = y;
		this.monitorId = monitorId;
		this.conductorId = conductorId;
		this.capacidad = capacidad;
	}
}
