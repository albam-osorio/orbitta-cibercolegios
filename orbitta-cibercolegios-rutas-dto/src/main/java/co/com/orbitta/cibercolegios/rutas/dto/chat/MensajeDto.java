package co.com.orbitta.cibercolegios.rutas.dto.chat;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import co.com.orbitta.cibercolegios.rutas.enums.EmisorMensajeEnum;
import co.com.orbitta.commons.dto.EntityDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class MensajeDto extends EntityDto<Integer> {

	private int conversacionId;

	private int rutaId;

	private int monitorId;

	@NotNull
	private EmisorMensajeEnum origen;

	@NotNull
	private LocalDateTime fechaHora;

	@NotNull
	@Size(max = 200)
	private String mensaje;

	@Builder
	public MensajeDto(Integer id, int conversacionId, int rutaId, int monitorId, @NotNull EmisorMensajeEnum origen,
			@NotNull LocalDateTime fechaHora, @NotNull @Size(max = 200) String mensaje) {
		super(id);
		this.conversacionId = conversacionId;
		this.rutaId = rutaId;
		this.monitorId = monitorId;
		this.origen = origen;
		this.fechaHora = fechaHora;
		this.mensaje = mensaje;
	}
}
