package co.com.orbitta.cibercolegios.rutas.dto.tracking.monitor;

import java.math.BigDecimal;
import java.util.List;

import co.com.orbitta.cibercolegios.rutas.dto.LogRutaDto;
import co.com.orbitta.cibercolegios.rutas.dto.tracking.AbstractDatosRutaDto;
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
public class DatosRutaDto extends AbstractDatosRutaDto {

	public Integer getUltimoSentido() {
		val result = (this.getLogRuta() == null) ? null : this.getLogRuta().getSentido();
		return result;
	}

	public Integer getEstadoId() {
		val result = (this.getLogRuta() == null) ? null : this.getLogRuta().getEstadoId();
		return result;
	}

	public String getEstadoNombre() {
		val result = (this.getLogRuta() == null) ? null : this.getLogRuta().getEstadoNombre();
		return result;
	}

	public TipoEstadoRutaEnum getTipoEstado() {
		val result = (this.getLogRuta() == null) ? null : this.getLogRuta().getTipoEstado();
		return result;
	}
	
	public BigDecimal getX() {
		val result = (this.getLogRuta() == null) ? null : this.getLogRuta().getX();
		return result;
	}

	public BigDecimal getY() {
		val result = (this.getLogRuta() == null) ? null : this.getLogRuta().getY();
		return result;
	}

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
