package co.com.orbitta.cibercolegios.rutas.service.impl.readonly;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.rutas.domain.readonly.Curso;
import co.com.orbitta.cibercolegios.rutas.dto.readonly.CursoDto;
import co.com.orbitta.cibercolegios.rutas.repository.readonly.CursoRepository;
import co.com.orbitta.cibercolegios.rutas.service.api.readonly.CursoService;
import co.com.orbitta.core.services.crud.impl.QueryServiceImpl;
import lombok.val;

@Service
public class CursoQueryServiceImpl extends QueryServiceImpl<Curso, CursoDto, Integer> implements CursoService {

	@Autowired
	private CursoRepository repository;

	@Override
	protected CursoRepository getRepository() {
		return repository;
	}

	@Override
	protected CursoDto asModel(Curso entity) {
		val result = new CursoDto();

		result.setId(entity.getId());
		result.setGradoAcademicoId(entity.getGradoAcademicoId());
		result.setNombre(entity.getNombre());
		
		return result;
	}

}