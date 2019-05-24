package com.tbf.cibercolegios.deprecated.api.routes.controllers.tracking;

import java.io.Serializable;

import com.tbf.cibercolegios.api.routes.model.graph.RutaDto;

import lombok.Data;

@Data
public class TrackViewModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private RutaDto ruta = new RutaDto();

	private String nombreMonitor;

	private long paradaActual = 0;
	
	private long paradasTotales = 0;
	
	private long pasajerosAbordo = 0;
}
