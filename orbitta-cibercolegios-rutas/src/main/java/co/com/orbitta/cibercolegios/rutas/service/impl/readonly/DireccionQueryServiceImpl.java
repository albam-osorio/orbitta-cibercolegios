package co.com.orbitta.cibercolegios.rutas.service.impl.readonly;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.dto.readonly.DireccionDto;
import co.com.orbitta.cibercolegios.rutas.domain.readonly.Direccion;
import co.com.orbitta.cibercolegios.rutas.repository.readonly.DireccionRepository;
import co.com.orbitta.cibercolegios.rutas.service.api.readonly.DireccionQueryService;
import co.com.orbitta.core.services.crud.impl.QueryServiceImpl;
import lombok.val;

@Service
public class DireccionQueryServiceImpl extends QueryServiceImpl<Direccion, DireccionDto, Integer>
		implements DireccionQueryService {

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
				.usuarioId(entity.getUsuario().getId())
				.descripcion(entity.getDescripcion())
				.x(entity.getX())
				.y(entity.getY())

				.build();
		// @formatter:on
		return result;
	}
}