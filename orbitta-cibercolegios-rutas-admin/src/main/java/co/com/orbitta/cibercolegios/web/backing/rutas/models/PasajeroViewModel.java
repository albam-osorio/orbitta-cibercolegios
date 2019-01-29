package co.com.orbitta.cibercolegios.web.backing.rutas.models;

import java.io.Serializable;

import lombok.Data;

@Data
public class PasajeroViewModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private int usuarioId;

	private String nombreEstudiante;

	private int secuenciaIda = 0;

	private int secuenciaRetorno = 0;

	private boolean miembro;
	
	private boolean adicionado;
	
	private boolean removido;
	
	private Integer nivelId;

	private Integer jornadaId;

	private Integer programaId;

	private Integer gradoId;

}
