package com.tbf.cibercolegios.api.routes.services.api;

import java.time.LocalDate;
import java.util.Optional;

import com.tbf.cibercolegios.api.core.services.api.CrudService;
import com.tbf.cibercolegios.api.routes.model.graph.LogRutaDto;

public interface LogRutaService extends CrudService<LogRutaDto, Integer> {

	Optional<LogRutaDto> findUltimoLogRutaByRutaIdAndFechaUltimoRecorrido(int rutaId, LocalDate fechaUltimoRecorrido);
}
