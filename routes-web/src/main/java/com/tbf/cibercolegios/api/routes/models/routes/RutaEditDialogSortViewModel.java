package com.tbf.cibercolegios.api.routes.models.routes;

import java.io.Serializable;

import com.tbf.cibercolegios.api.ciber.model.graph.UsuarioPasajeroDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class RutaEditDialogSortViewModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private UsuarioPasajeroDto pasajero;

	private int correlacion;

	private RutaEditDireccionViewModel direccion;

	private Integer secuencia;

	public boolean isModificado() {
		if (getSecuencia().equals(getDireccion().getSecuencia())) {
			return false;
		} else {
			return true;
		}
	}

	public String getOrden() {
		String result;
		if (getDireccion().getSecuencia() != null) {
			result = String.valueOf(getDireccion().getSecuencia());
		} else {
			result = getPasajero().getNombreCompleto();
		}
		return result;
	}
}
