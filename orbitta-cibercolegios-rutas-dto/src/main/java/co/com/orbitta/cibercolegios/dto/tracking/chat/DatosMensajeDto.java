package co.com.orbitta.cibercolegios.dto.tracking.chat;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import co.com.orbitta.cibercolegios.enums.EmisorMensajeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DatosMensajeDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private int mensajeId;

	private int conversacionId;

	private int rutaId;

	private String rutaCodigo;
	
	private int monitorId;

	private String monitorNombres;

	private String monitorApellidos;

	@NotNull
	private EmisorMensajeEnum origen;

	@NotNull
	private LocalDateTime fechaHora;

	@NotNull
	@Size(max = 200)
	private String mensaje;
}
