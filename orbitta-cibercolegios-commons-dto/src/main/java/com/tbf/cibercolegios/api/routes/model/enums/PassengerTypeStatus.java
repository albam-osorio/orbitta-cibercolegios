package com.tbf.cibercolegios.api.routes.model.enums;

public enum PassengerTypeStatus {
	INACTIVO, INICIO, FIN;
	
	public boolean isIniciado() {
		return this.equals(INICIO);
	}

	public boolean isFinalizado() {
		return this.equals(FIN);
	}

}
