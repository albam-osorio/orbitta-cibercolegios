package co.com.orbitta.cibercolegios.rutas.enums;

public enum TipoEstadoPasajeroEnum {
	INACTIVO, INICIO, FIN;
	
	public boolean isIniciado() {
		return this.equals(INICIO);
	}

	public boolean isFinalizado() {
		return this.equals(FIN);
	}

}
