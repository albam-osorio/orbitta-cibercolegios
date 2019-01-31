package com.tbf.cibercolegios.cibercolegios.web.controller.usuarios.models;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.tbf.cibercolegios.api.routes.model.graph.readonly.UsuarioDto;

import lombok.Data;

@Data
public class DireccionPasajeroViewModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private int index;

	private String codigoTipoIdentificacionId;

	@Size(max = 30)
	private String numeroIdentificacion;

	@NotNull
	@Size(max = 100)
	private String nombres;

	@NotNull
	@Size(max = 50)
	private String apellidos;

	@NotNull
	@Size(max = 50)
	private String departamentoAm;

	@NotNull
	@Size(max = 100)
	private String ciudadAm;

	@NotNull
	@Size(max = 100)
	private String direccionAm;

	@NotNull
	@Size(max = 50)
	private String departamentoPm;

	@NotNull
	@Size(max = 100)
	private String ciudadPm;

	@NotNull
	@Size(max = 100)
	private String direccionPm;

	private Integer tipoIdentificacionId;

	private UsuarioDto usuario;

	private Integer institucionId;

	private Integer departamentoAmId;

	private Integer ciudadAmId;

	private Integer departamentoPmId;

	private Integer ciudadPmId;

	private List<String> errores;
}
