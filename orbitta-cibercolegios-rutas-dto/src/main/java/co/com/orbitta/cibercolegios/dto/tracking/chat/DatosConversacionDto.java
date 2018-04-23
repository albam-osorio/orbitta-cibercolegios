package co.com.orbitta.cibercolegios.dto.tracking.chat;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DatosConversacionDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private int conversacionId;

	private int acudienteId;

	private String acudienteNombres;

	private String acudienteApellidos;

	private int estudianteId;

	private String estudianteNombres;

	private String estudianteApellidos;
}
