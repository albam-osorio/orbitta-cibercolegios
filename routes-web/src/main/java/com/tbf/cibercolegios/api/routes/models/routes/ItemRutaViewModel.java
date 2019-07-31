package com.tbf.cibercolegios.api.routes.models.routes;

import java.io.Serializable;

import com.tbf.cibercolegios.api.routes.model.graph.RutaDto;

import lombok.Data;

@Data
public class ItemRutaViewModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private RutaDto ruta = new RutaDto();

	private String nombreMonitor;

	long ocupacionAm = 0;
	long ocupacionPm = 0;
}
