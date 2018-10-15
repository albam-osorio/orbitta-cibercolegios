package co.com.orbitta.cibercolegios.web.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class RutaViewModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private int rutaId;

	private String codigo;

	private String descripcion;

	private String marca;

	private String placa;

	private String movil;

	private int institucionId;

	private String institucionNombre;

	private int monitorId;

	private String monitorNombres;

	private String monitorApellidos;

	private int conductorId;

	private String conductorNombres;

	private String conductorApellidos;

	private int capacidadMaxima;

	private int usuariosInscritos;

	public String monitorNombre() {
		return getMonitorNombres() + ", " + getMonitorApellidos();
	}
}
