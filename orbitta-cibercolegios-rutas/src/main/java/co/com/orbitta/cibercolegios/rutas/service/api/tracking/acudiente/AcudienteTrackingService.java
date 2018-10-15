package co.com.orbitta.cibercolegios.rutas.service.api.tracking.acudiente;

import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import co.com.orbitta.cibercolegios.rutas.dto.tracking.acudiente.DatosEstadoParadaDto;
import co.com.orbitta.cibercolegios.rutas.dto.tracking.acudiente.DatosParadaDto;

@Transactional(readOnly = true)
public interface AcudienteTrackingService {

	Optional<DatosParadaDto> findParadaByUsuarioId(int usuarioId);

	Optional<DatosEstadoParadaDto> findEstadoParadaByUsuarioId(int usuarioId);
}
