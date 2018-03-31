package co.com.orbitta.cibercolegios.dto.tracking;

import java.util.List;

import co.com.orbitta.cibercolegios.dto.InstitucionDto;
import co.com.orbitta.cibercolegios.dto.RutaDto;
import co.com.orbitta.cibercolegios.dto.UsuarioDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DatosRutaDto {
	private int ubicacionRutaId;
	
	private RutaDto ruta;

	private InstitucionDto institucion;

	private UsuarioDto monitor;

	private UsuarioDto conductor;
	
	private List<PasajeroRutaDto> pasajeros;
}
