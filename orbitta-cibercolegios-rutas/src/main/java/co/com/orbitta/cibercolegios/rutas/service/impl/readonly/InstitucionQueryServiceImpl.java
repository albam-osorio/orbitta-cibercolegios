package co.com.orbitta.cibercolegios.rutas.service.impl.readonly;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.rutas.domain.readonly.Institucion;
import co.com.orbitta.cibercolegios.rutas.dto.readonly.InstitucionDto;
import co.com.orbitta.cibercolegios.rutas.repository.readonly.InstitucionRepository;
import co.com.orbitta.cibercolegios.rutas.service.api.readonly.InstitucionService;
import co.com.orbitta.core.services.crud.impl.QueryServiceImpl;
import lombok.val;

@Service
public class InstitucionQueryServiceImpl extends QueryServiceImpl<Institucion, InstitucionDto, Integer>
		implements InstitucionService {

	@Autowired
	private InstitucionRepository repository;

	@Override
	protected InstitucionRepository getRepository() {
		return repository;
	}

	@Override
	protected InstitucionDto asModel(Institucion entity) {
		val result = new InstitucionDto();

		result.setId(entity.getId());
		result.setNombre(entity.getNombre());

		return result;
	}

}