package co.com.orbitta.cibercolegios.service.api;

import java.util.List;

import co.com.orbitta.cibercolegios.ciber.dto.InstitucionDto;
import co.com.orbitta.core.services.crud.api.QueryService;

public interface InstitucionQueryService extends QueryService<InstitucionDto, Integer> {
	List<InstitucionDto> findAll();
}
