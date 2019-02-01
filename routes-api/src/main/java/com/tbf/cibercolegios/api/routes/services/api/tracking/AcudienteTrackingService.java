package com.tbf.cibercolegios.api.routes.services.api.tracking;

import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.tbf.cibercolegios.api.routes.model.graph.tracking.DatosEstadoParadaDto;
import com.tbf.cibercolegios.api.routes.model.graph.tracking.DatosParadaDto;

@Transactional(readOnly = true)
public interface AcudienteTrackingService {

	Optional<DatosParadaDto> findParadaByUsuarioId(int usuarioId);

	Optional<DatosEstadoParadaDto> findEstadoParadaByUsuarioId(int usuarioId);
}
