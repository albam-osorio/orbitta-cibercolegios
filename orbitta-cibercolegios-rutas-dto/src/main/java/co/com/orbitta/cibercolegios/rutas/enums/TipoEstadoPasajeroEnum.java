package co.com.orbitta.cibercolegios.rutas.enums;

public enum TipoEstadoPasajeroEnum {
	INICIO, FIN;
	
	public boolean isFinalizado() {
		return this.equals(FIN);
	}
}
