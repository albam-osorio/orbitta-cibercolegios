package co.com.orbitta.cibercolegios.rutas.dto;

import java.util.List;

import javax.validation.constraints.Size;

import co.com.orbitta.cibercolegios.rutas.enums.TipoEstadoPasajeroEnum;
import co.com.orbitta.core.dto.AuditableEntityDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class PasajeroDto extends AuditableEntityDto<Integer> {

	public static final int ESTADO_INACTIVO = 0;
	
	private Integer rutaId;

	private int usuarioId;

	private int secuenciaIda;

	private Integer direccionIdaId;

	private int secuenciaRetorno;

	private Integer direccionRetornoId;

	private int estadoId;

	private TipoEstadoPasajeroEnum tipoEstado;

	@Size(max = 50)
	private String estadoDescripcion;

	private List<Integer> acudientes;
}
