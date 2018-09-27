package co.com.orbitta.cibercolegios.rutas.dto.tracking.monitor;

import java.math.BigDecimal;

import co.com.orbitta.cibercolegios.rutas.enums.TipoEstadoPasajeroEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DatosPasajeroDto {

	private Integer usuarioId;

	private int secuencia;

	private String nombres;

	private String apellidos;

	private int estadoId;

	private String estadoDescripcion;

	private TipoEstadoPasajeroEnum tipoEstado;

	public boolean isFinalizado() {
		return (tipoEstado == null) ? null : tipoEstado.isFinalizado();
	}

	private String direccion;

	private BigDecimal x;

	private BigDecimal y;
}
