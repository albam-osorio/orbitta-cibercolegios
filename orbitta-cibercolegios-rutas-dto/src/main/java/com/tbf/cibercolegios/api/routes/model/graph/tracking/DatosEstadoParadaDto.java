package com.tbf.cibercolegios.api.routes.model.graph.tracking;

import java.math.BigDecimal;

import com.tbf.cibercolegios.api.routes.model.enums.PassengerTypeStatus;
import com.tbf.cibercolegios.api.routes.model.enums.RouteTypeStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DatosEstadoParadaDto {

	private Integer estadoRutaId;

	private String estadoRutaNombre;

	private RouteTypeStatus tipoEstadoRuta;

	private BigDecimal rutaX;

	private BigDecimal rutaY;

	
	private Integer estadoEstudianteId;

	private String estadoEstudianteNombre;

	private PassengerTypeStatus tipoEstadoEstudiante;

	private BigDecimal paradaX;

	private BigDecimal paradaY;
	
	
	private BigDecimal institucionX;

	private BigDecimal institucionY;
}
