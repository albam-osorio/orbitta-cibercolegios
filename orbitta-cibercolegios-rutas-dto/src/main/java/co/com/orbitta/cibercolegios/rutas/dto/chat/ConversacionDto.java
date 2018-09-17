package co.com.orbitta.cibercolegios.rutas.dto.chat;


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
public class ConversacionDto extends EntityDto<Integer> {

	private int acudienteId;

	private int estudianteId;

	@Builder
	public ConversacionDto(Integer id, int acudienteId, int estudianteId) {
		super(id);
		this.acudienteId = acudienteId;
		this.estudianteId = estudianteId;
	}
}
