package com.tbf.cibercolegios.api.routes.model.graph.tracking;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.tbf.cibercolegios.api.model.routes.enums.CourseType;
import com.tbf.cibercolegios.api.model.routes.enums.RouteTypeStatus;

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

	private RouteTypeStatus tipoEstado;

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

	public long getPasajerosAbordo() {
		long result = 0;
		if (this.pasajeros != null) {
			// @formatter:off
			result = this.pasajeros
					.stream()
					.filter(a -> !a.getTipoEstado().isIniciado())
					.count();
			// @formatter:on
		}
		return result;
	}

	public String getDestino() {
		val sentido = CourseType.asEnum(this.getUltimoSentido());
		if (sentido != null) {
			switch (sentido) {
			case SENTIDO_IDA:
				return "CAMINO AL COLEGIO";
			case SENTIDO_RETORNO:
				return "CAMINO A CASA";
			default:
				return "DESCONOCIDO";
			}
		} else {
			return "";
		}
	}

	// private LogRutaDto logRuta;

	private List<DatosPasajeroDto> pasajeros;
}
