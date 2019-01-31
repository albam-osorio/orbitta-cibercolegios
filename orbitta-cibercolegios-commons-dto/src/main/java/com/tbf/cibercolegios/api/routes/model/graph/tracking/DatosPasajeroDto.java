package com.tbf.cibercolegios.api.routes.model.graph.tracking;

import java.math.BigDecimal;
import java.util.List;

import com.tbf.cibercolegios.api.routes.model.enums.PassengerTypeStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DatosPasajeroDto {

	private Integer usuarioId;

	private String nombres;

	private String apellidos;

	private int secuencia;

	private int estadoId;

	private String estadoDescripcion;

	private PassengerTypeStatus tipoEstado;

	public Boolean isFinalizado() {
		return (tipoEstado == null) ? false : tipoEstado.isFinalizado();
	}

	private String ciudadNombre;
	
	private String direccion;

	private BigDecimal x;

	private BigDecimal y;
	
	private List<DatosAcudienteDto> acudientes;
}
