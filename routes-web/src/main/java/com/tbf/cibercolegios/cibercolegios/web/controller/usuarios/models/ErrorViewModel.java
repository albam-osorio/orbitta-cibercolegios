package com.tbf.cibercolegios.cibercolegios.web.controller.usuarios.models;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorViewModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private int index;

	private String numeroIdentificacion;

	private String error;
}