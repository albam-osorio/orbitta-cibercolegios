package com.tbf.cibercolegios.api.routes.model.graph.web;

import java.io.Serializable;

import lombok.Data;

@Data
public class RutaConCapacidadDto implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private boolean seleccionada;

	private boolean habilitada;
	
	private Integer id;

	private String descripcion;

	private int ocupacionAm;

	private int ocupacionPm;

	private int capacidadMaxima;

	public int getDisponibilidadAm() {
		return this.getCapacidadMaxima() - this.getOcupacionAm();
	}

	public int getDisponibilidadPm() {
		return this.getCapacidadMaxima() - this.getOcupacionPm();
	}
}
