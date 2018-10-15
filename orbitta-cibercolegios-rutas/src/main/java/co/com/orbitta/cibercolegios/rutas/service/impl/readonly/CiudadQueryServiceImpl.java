package co.com.orbitta.cibercolegios.rutas.service.impl.readonly;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.rutas.domain.readonly.Ciudad;
import co.com.orbitta.cibercolegios.rutas.dto.readonly.CiudadDto;
import co.com.orbitta.cibercolegios.rutas.repository.readonly.CiudadRepository;
import co.com.orbitta.cibercolegios.rutas.service.api.readonly.CiudadService;
import co.com.orbitta.core.services.crud.impl.QueryServiceImpl;
import lombok.val;

@Service
public class CiudadQueryServiceImpl extends QueryServiceImpl<Ciudad, CiudadDto, Integer> implements CiudadService {

	@Autowired
	private CiudadRepository repository;

	@Override
	protected CiudadRepository getRepository() {
		return repository;
	}

	@Override
	protected CiudadDto asModel(Ciudad entity) {
		val result = new CiudadDto();

		result.setId(entity.getId());
		result.setCodigo(entity.getCodigo());
		result.setNombre(entity.getNombre());

		return result;
	}
}