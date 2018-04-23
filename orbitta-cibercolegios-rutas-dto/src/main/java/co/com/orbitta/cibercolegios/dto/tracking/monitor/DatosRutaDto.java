package co.com.orbitta.cibercolegios.dto.tracking.monitor;

import java.math.BigDecimal;
import java.util.List;

import co.com.orbitta.cibercolegios.dto.LogRutaDto;
import co.com.orbitta.cibercolegios.dto.tracking.AbstractDatosRutaDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
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

	private static final long serialVersionUID = 1L;

	private LogRutaDto logRuta;

	private List<DatosPasajeroDto> pasajeros;

	public Integer getUltimoSentido() {
		val result = (this.getLogRuta() == null) ? null : this.getLogRuta().getSentido();
		return result;
	}

	public int getNumeroPasajeros() {
		return this.pasajeros.size();
	}

	@Builder
	public DatosRutaDto(int rutaId, String codigo, String descripcion, String marca, String placa, String movil,
			int capacidad, int institucionId, String institucionNombre, BigDecimal institucionX,
			BigDecimal institucionY, int monitorId, String monitorNombres, String monitorApellidos, int conductorId,
			String conductorNombres, String conductorApellidos, boolean activa, LogRutaDto logRuta,
			List<DatosPasajeroDto> pasajeros) {
		super(rutaId, codigo, descripcion, marca, placa, movil, capacidad, institucionId, institucionNombre,
				institucionX, institucionY, monitorId, monitorNombres, monitorApellidos, conductorId, conductorNombres,
				conductorApellidos, activa);
		this.logRuta = logRuta;
		this.pasajeros = pasajeros;
	}
}
