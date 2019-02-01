package com.tbf.cibercolegios.api.routes.services.api;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.tbf.cibercolegios.api.core.services.api.CrudService;
import com.tbf.cibercolegios.api.routes.model.graph.DireccionDto;
import com.tbf.cibercolegios.api.routes.model.graph.PasajeroDto;

public interface PasajeroService extends CrudService<PasajeroDto, Integer> {

	@Transactional(readOnly = true)
	Optional<PasajeroDto> findByUsuarioId(int usuarioId);

	@Transactional(readOnly = true)
	List<PasajeroDto> findAllByRutaId(int rutaId);

	long countByRutaId(int rutaId);

	@Transactional(readOnly = false)
	PasajeroDto create(PasajeroDto model, DireccionDto direccionIda, DireccionDto direccionRetorno,
			List<Integer> usuariosAcudientesId);

	@Transactional(readOnly = false)
	PasajeroDto update(PasajeroDto model, DireccionDto direccionIda, DireccionDto direccionRetorno,
			List<Integer> usuariosAcudientesId);
}