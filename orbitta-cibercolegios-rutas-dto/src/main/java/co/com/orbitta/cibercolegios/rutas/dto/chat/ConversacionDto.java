package co.com.orbitta.cibercolegios.rutas.dto.chat;

import co.com.orbitta.commons.dto.SimpleAuditableEntityDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class ConversacionDto extends SimpleAuditableEntityDto<Integer> {

	private int rutaId;

	private int usuarioMonitorId;
	
	private int usuarioAcudienteId;

	private int usuarioPasajeroId;
}
