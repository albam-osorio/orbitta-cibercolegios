package co.com.orbitta.cibercolegios.rutas.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.rutas.domain.Direccion;
import co.com.orbitta.cibercolegios.rutas.dto.DireccionDto;
import co.com.orbitta.cibercolegios.rutas.repository.DireccionRepository;
import co.com.orbitta.cibercolegios.rutas.service.api.DireccionQueryService;
import co.com.orbitta.core.services.crud.impl.QueryServiceImpl;
import lombok.val;

@Service
public class DireccionQueryServiceImpl extends QueryServiceImpl<Direccion, DireccionDto, Integer> implements DireccionQueryService {

	@Autowired
	private DireccionRepository repository;

	@Override
	protected DireccionRepository getRepository() {
		return repository;
	}

	@Override
	public DireccionDto asModel(Direccion entity) {

		// @formatter:off
		val result = DireccionDto
				.builder()
				.id(entity.getId())
				.usuarioId(entity.getUsuarioId())
				.descripcion(entity.getDescripcion())
				.x(entity.getX())
				.y(entity.getY())

				.build();
		// @formatter:on
		return result;
	}
}