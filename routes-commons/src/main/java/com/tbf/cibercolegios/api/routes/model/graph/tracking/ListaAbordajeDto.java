package com.tbf.cibercolegios.api.routes.model.graph.tracking;

import lombok.Data;

@Data
public class ListaAbordajeDto {
	private int rutaId;
	private PasajeroEstadoInicialDto[] pasajeros;
}
