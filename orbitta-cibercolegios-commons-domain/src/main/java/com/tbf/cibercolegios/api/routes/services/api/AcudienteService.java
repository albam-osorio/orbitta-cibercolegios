package com.tbf.cibercolegios.api.routes.services.api;

import java.util.Optional;

import com.tbf.cibercolegios.api.routes.model.graph.AcudienteDto;

import co.com.orbitta.core.services.crud.api.CrudService;

public interface AcudienteService extends CrudService<AcudienteDto, Integer>{

	Optional<AcudienteDto> findByUsuarioId(int usuarioId);
}
