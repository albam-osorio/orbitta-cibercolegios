package co.com.orbitta.cibercolegios.rutas.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import co.com.orbitta.cibercolegios.rutas.enums.TipoEstadoRutaEnum;
import co.com.orbitta.commons.dto.AuditableEntityDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class RutaDto extends AuditableEntityDto<Integer> {

	public static final int SENTIDO_IDA = 1;

	public static final int SENTIDO_RETORNO = 2;

	private int institucionId;

	@NotNull
	@Size(max = 10)
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

	private int capacidadMaxima;

	@NotNull
	@Size(max = 100)
	private String movil;

	@NotNull
	@Size(max = 200)
	private String token;

	@NotNull
	@Size(max = 100)
	private String conductorNombres;

	private int monitorId;

	private int direccionSedeId;

	@DateTimeFormat(style = "M-")
	private LocalDate fechaUltimoRecorrido;

	private int sentido;

	private int estadoId;

	@NotNull
	private TipoEstadoRutaEnum tipoEstado;

	@NotNull
	@Size(max = 50)
	private String estadoDescripcion;

	private BigDecimal x;

	private BigDecimal y;
	
}
