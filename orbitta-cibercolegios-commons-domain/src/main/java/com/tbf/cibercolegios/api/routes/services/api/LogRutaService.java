package com.tbf.cibercolegios.api.routes.services.api;

import java.time.LocalDate;
import java.util.Optional;

import com.tbf.cibercolegios.api.routes.model.graph.LogRutaDto;

import co.com.orbitta.core.services.crud.api.CrudService;

public interface LogRutaService extends CrudService<LogRutaDto, Integer> {

	Optional<LogRutaDto> findUltimoLogRutaByRutaIdAndFechaUltimoRecorrido(int rutaId, LocalDate fechaUltimoRecorrido);
}
