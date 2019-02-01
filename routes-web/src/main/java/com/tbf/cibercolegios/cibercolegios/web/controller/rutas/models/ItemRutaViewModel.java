package com.tbf.cibercolegios.cibercolegios.web.controller.rutas.models;

import java.io.Serializable;

import com.tbf.cibercolegios.api.routes.model.graph.RutaDto;

import lombok.Data;

@Data
public class ItemRutaViewModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private RutaDto ruta = new RutaDto();

	private String nombreMonitor;

	long inscritos = 0;
}
