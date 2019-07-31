package com.tbf.cibercolegios.api.routes.services.api;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.tbf.cibercolegios.api.core.services.api.CrudService;
import com.tbf.cibercolegios.api.model.routes.Acudiente;
import com.tbf.cibercolegios.api.model.routes.PasajeroDireccion;
import com.tbf.cibercolegios.api.routes.model.graph.DireccionDto;
import com.tbf.cibercolegios.api.routes.model.graph.PasajeroDto;

public interface PasajeroService extends CrudService<PasajeroDto, Integer> {

	@Transactional(readOnly = true)
	Optional<PasajeroDto> findByInstitucionIdAndUsuarioId(int institucionId, int usuarioId);

	@Transactional(readOnly = true)
	List<PasajeroDto> findAllByRutaId(int rutaId);
	
	@Transactional(readOnly = true)
	List<PasajeroDireccion> findAllPasajeroDireccionByRutaId(int rutaId);

	@Transactional(readOnly = true)
	List<PasajeroDireccion> findAllPasajeroDireccionByPasajeroId(int pasajeroId);

	@Transactional(readOnly = true)
	List<PasajeroDireccion> findAllPasajeroDireccionByPasajeroIdIn(List<Integer> pasajerosId);
	
	@Transactional(readOnly = true)
	Map<Integer, List<Acudiente>> findAllAcudientesIdByPasajeroIdIn(List<Integer> pasajerosId);

	// -----------------------------------------------------------------------------------
	// --
	// -----------------------------------------------------------------------------------
	@Transactional(readOnly = false)
	void create(PasajeroDto model, Optional<DireccionDto> direccionIda, Optional<DireccionDto> direccionRetorno,
			List<Integer> usuariosAcudientesId, boolean activo);

	@Transactional(readOnly = false)
	void update(PasajeroDto model, Optional<DireccionDto> direccionIda, Optional<DireccionDto> direccionRetorno,
			List<Integer> usuariosAcudientesId, boolean activo);
}
