package co.com.orbitta.cibercolegios.rutas.dto.tracking.monitor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import co.com.orbitta.cibercolegios.rutas.dto.tracking.AbstractDatosRutaDto;
import co.com.orbitta.cibercolegios.rutas.dto.tracking.LogRutaDto;
import co.com.orbitta.cibercolegios.rutas.enums.TipoEstadoRutaEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.val;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MonitorDatosRutaDto extends AbstractDatosRutaDto {

	private LocalDate fechaUltimoRecorrido;

	private Integer ultimoSentido;

	private Integer estadoId;

	private TipoEstadoRutaEnum tipoEstado;

	private String estadoNombre;

	private BigDecimal x;

	private BigDecimal y;

	public int getTotalParadas() {
		int result = 0;
		if (this.pasajeros != null) {
			result = this.pasajeros.size();
		}
		return result;
	}

	public int getParadaActual() {
		int result = 0;
		if (this.pasajeros != null) {
			// @formatter:off
			val optional = this.pasajeros
					.stream()
					.sorted((a, b) -> Integer.compare(a.getSecuencia(), b.getSecuencia()))
					.filter(a -> !a.isFinalizado())
					.findFirst();
			// @formatter:on

			if ((optional.isPresent())) {
				result = optional.get().getSecuencia();
			} else {
				result = this.pasajeros.size();
			}
		}
		return result;
	}

	private LogRutaDto logRuta;

	private List<DatosPasajeroDto> pasajeros;
}
