package co.com.orbitta.cibercolegios.rutas.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DatosConversacionDto {

	private int conversacionId;

	private int rutaId;

	private String rutaCodigo;

	private int monitorId;

	private String monitorNombres;

	private String monitorApellidos;

	private int acudienteId;

	private String acudienteNombres;

	private String acudienteApellidos;

	private int estudianteId;

	private String estudianteNombres;

	private String estudianteApellidos;
}
