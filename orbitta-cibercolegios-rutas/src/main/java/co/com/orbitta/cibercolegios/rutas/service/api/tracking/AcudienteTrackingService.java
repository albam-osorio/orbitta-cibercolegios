package co.com.orbitta.cibercolegios.rutas.service.api.tracking;

import org.springframework.transaction.annotation.Transactional;

import co.com.orbitta.cibercolegios.rutas.dto.tracking.acudiente.DatosEstadoParadaDto;
import co.com.orbitta.cibercolegios.rutas.dto.tracking.acudiente.DatosParadaDto;

@Transactional(readOnly = true)
public interface AcudienteTrackingService {

	DatosParadaDto findParadaByEstudianteId(int estudianteId);

	DatosEstadoParadaDto findEstadoParadaByEstudianteId(int estudianteId);
}
