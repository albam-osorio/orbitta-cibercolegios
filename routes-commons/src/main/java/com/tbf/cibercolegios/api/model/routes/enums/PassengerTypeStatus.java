package com.tbf.cibercolegios.api.model.routes.enums;

public enum PassengerTypeStatus {
	INACTIVO, INICIO, FIN;

	public boolean isInactivo() {
		return this.equals(INACTIVO);
	}

	public boolean isIniciado() {
		return this.equals(INICIO);
	}

	public boolean isFinalizado() {
		return this.equals(FIN);
	}

}
