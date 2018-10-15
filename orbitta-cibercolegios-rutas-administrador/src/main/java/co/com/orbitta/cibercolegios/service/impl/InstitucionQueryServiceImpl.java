package co.com.orbitta.cibercolegios.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.ciber.dto.InstitucionDto;
import co.com.orbitta.cibercolegios.domain.Institucion;
import co.com.orbitta.cibercolegios.repository.InstitucionRepository;
import co.com.orbitta.cibercolegios.service.api.InstitucionQueryService;
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
				.nombre(entity.getNombre())
				.build();
		// @formatter:on

		return result;
	}

	@Override
	public List<InstitucionDto> findAll() {
		val entities = getRepository().findAll();
		val result = asModels(entities);
		return result;
	}
}