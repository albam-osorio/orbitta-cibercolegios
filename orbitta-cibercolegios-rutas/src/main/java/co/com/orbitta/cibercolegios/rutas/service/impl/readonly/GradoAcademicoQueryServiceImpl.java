package co.com.orbitta.cibercolegios.rutas.service.impl.readonly;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.rutas.domain.readonly.GradoAcademico;
import co.com.orbitta.cibercolegios.rutas.dto.readonly.GradoAcademicoDto;
import co.com.orbitta.cibercolegios.rutas.repository.readonly.GradoAcademicoRepository;
import co.com.orbitta.cibercolegios.rutas.service.api.readonly.GradoAcademicoService;
import co.com.orbitta.core.services.crud.impl.QueryServiceImpl;
import lombok.val;

@Service
public class GradoAcademicoQueryServiceImpl extends QueryServiceImpl<GradoAcademico, GradoAcademicoDto, Integer> implements GradoAcademicoService {

	@Autowired
	private GradoAcademicoRepository repository;

	@Override
	protected GradoAcademicoRepository getRepository() {
		return repository;
	}

	@Override
	protected GradoAcademicoDto asModel(GradoAcademico entity) {
		val result = new GradoAcademicoDto();

		result.setId(entity.getId());
		result.setProgramaAcademicoId(entity.getProgramaAcademicoId());
		result.setNombre(entity.getNombre());
		
		return result;
	}

}