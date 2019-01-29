package co.com.orbitta.cibercolegios.rutas.enums;

public enum TipoEstadoRutaEnum {
	INACTIVO, INICIO, RECORRIDO, FIN;

	public boolean isActiva() {
		return this.equals(INICIO) || this.equals(RECORRIDO);
	}

	public boolean isFinalizado() {
		return this.equals(FIN);
	}
}
