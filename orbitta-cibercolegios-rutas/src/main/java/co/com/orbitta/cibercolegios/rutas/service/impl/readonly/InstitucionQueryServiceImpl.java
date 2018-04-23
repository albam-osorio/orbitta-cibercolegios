package co.com.orbitta.cibercolegios.rutas.service.impl.readonly;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.dto.readonly.InstitucionDto;
import co.com.orbitta.cibercolegios.rutas.domain.readonly.Institucion;
import co.com.orbitta.cibercolegios.rutas.repository.readonly.InstitucionRepository;
import co.com.orbitta.cibercolegios.rutas.service.api.readonly.InstitucionQueryService;
import co.com.orbitta.core.services.crud.impl.QueryServiceImpl;
import lombok.val;

@Service
public class InstitucionQueryServiceImpl extends QueryServiceImpl<Institucion, InstitucionDto, Integer>
		implements InstitucionQueryService {

	@Autowired
	private InstitucionRepository repository;

	@Override
	protected InstitucionRepository getRepository() {
		return repository;
	}

	@Override
	public InstitucionDto asModel(Institucion entity) {
		// @formatter:off
		val result = InstitucionDto
				.builder()
				.id(entity.getId())
				.descripcion(entity.getDescripcion())

				.build();
		// @formatter:on
		return result;
	}
}