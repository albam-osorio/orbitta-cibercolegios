package co.com.orbitta.cibercolegios.rutas.dto.readonly;

import co.com.orbitta.core.dto.EntityDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class UsuarioPerfilDto extends EntityDto<Integer> {

	private byte[] foto;
	
	private String tipoDocumento;

	private String telefonoDomicilio;

	private String direccionDomicilio;

	private String email;

}
