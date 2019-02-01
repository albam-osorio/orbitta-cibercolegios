package com.tbf.cibercolegios.api.model.routes.enums;

public enum RouteTypeStatus {
	INACTIVO, INICIO, RECORRIDO, FIN;

	public boolean isActiva() {
		return this.equals(INICIO) || this.equals(RECORRIDO);
	}

	public boolean isFinalizado() {
		return this.equals(FIN);
	}
}
