package com.tbf.cibercolegios.api.routes.services.api;

import java.util.Optional;

import com.tbf.cibercolegios.api.core.services.api.CrudService;
import com.tbf.cibercolegios.api.routes.model.graph.AcudienteDto;

public interface AcudienteService extends CrudService<AcudienteDto, Integer>{

	Optional<AcudienteDto> findByUsuarioId(int usuarioId);
}
