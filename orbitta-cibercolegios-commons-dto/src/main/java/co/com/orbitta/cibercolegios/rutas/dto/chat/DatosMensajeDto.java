package co.com.orbitta.cibercolegios.rutas.dto.chat;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import co.com.orbitta.cibercolegios.rutas.enums.EmisorMensajeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DatosMensajeDto {

	private int mensajeId;

	private int conversacionId;

	@NotNull
	private EmisorMensajeEnum origen;

	@NotNull
	@Size(max = 200)
	private String mensaje;

	@NotNull
	private LocalDateTime fechaHora;
}
