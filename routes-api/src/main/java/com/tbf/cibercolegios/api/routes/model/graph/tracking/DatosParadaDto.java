package com.tbf.cibercolegios.api.routes.model.graph.tracking;

import java.math.BigDecimal;

import com.tbf.cibercolegios.api.model.routes.enums.PassengerTypeStatus;
import com.tbf.cibercolegios.api.model.routes.enums.RouteTypeStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DatosParadaDto extends AbstractDatosRutaDto {

	private Integer estadoRutaId;

	private String estadoRutaNombre;

	private RouteTypeStatus tipoEstadoRuta;

	private BigDecimal rutaX;

	private BigDecimal rutaY;

	private int totalParadas;

	private int paradaActual;
	
	private int secuencia;

	private Integer estudianteId;

	private String estudianteNombres;

	private String estudianteApellidos;

	private int estadoEstudianteId;

	private String estadoEstudianteNombre;

	private PassengerTypeStatus tipoEstadoEstudiante;

	public Boolean isFinalizado() {
		return (tipoEstadoEstudiante == null) ? false : tipoEstadoEstudiante.isFinalizado();
	}

	private String ciudadNombre;
	
	private String direccion;

	private BigDecimal paradaX;

	private BigDecimal paradaY;
}
