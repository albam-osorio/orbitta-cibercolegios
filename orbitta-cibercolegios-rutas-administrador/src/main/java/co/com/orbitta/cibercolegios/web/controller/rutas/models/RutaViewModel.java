package co.com.orbitta.cibercolegios.web.controller.rutas.models;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import co.com.orbitta.cibercolegios.rutas.dto.RutaDto;
import co.com.orbitta.cibercolegios.rutas.dto.readonly.UsuarioDto;
import lombok.Data;

@Data
public class RutaViewModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private RutaDto ruta = new RutaDto();

	private UsuarioDto monitor = new UsuarioDto();

	private Integer tipoIdentificacionId;

	@Size(max = 30)
	private String numeroIdentificacion;

	private String nombreMonitor;
	
	private boolean usarDireccionExistente = true;

	private Integer departamentoId;

	private Integer ciudadId;

	@NotNull
	@Size(max = 100)
	private String direccionNueva;
	
	private String direccionExistente;

	public void resetDireccion() {
		departamentoId = null;
		ciudadId = null;
		direccionNueva = "";
		ruta.setDireccionSedeId(null);
		direccionExistente = "";
	}

}
