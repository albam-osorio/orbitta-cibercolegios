package co.com.orbitta.cibercolegios.web.controller.rutas.models;

import java.io.Serializable;

import co.com.orbitta.cibercolegios.rutas.dto.RutaDto;
import lombok.Data;

@Data
public class ItemRutaViewModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private RutaDto ruta = new RutaDto();

	private String nombreMonitor;

	long inscritos = 0;
}
