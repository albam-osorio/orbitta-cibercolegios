package com.tbf.cibercolegios.api.routes.models.users;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.tbf.cibercolegios.api.model.routes.enums.CourseType;

import lombok.Data;

@Data
public class DireccionViewModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private int version;

	private CourseType sentido;

	private Integer direccionId;

	private Integer departamentoId;

	private String departamentoNombre;

	private Integer ciudadId;

	private String ciudadNombre;

	@Size(max = 100)
	@NotNull
	private String direccion;

	private int direccionVersion;

	private Integer secuencia;

	private boolean geoCodificada;

	private boolean modificado;

	public String getDireccionCompleta() {
		return getDireccion() + ", " + getCiudadNombre();
	}
}
