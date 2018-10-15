package co.com.orbitta.cibercolegios.rutas.service.impl.readonly;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.rutas.domain.readonly.ProgramaAcademico;
import co.com.orbitta.cibercolegios.rutas.dto.readonly.ProgramaAcademicoDto;
import co.com.orbitta.cibercolegios.rutas.repository.readonly.ProgramaAcademicoRepository;
import co.com.orbitta.cibercolegios.rutas.service.api.readonly.ProgramaAcademicoService;
import co.com.orbitta.core.services.crud.impl.QueryServiceImpl;
import lombok.val;

@Service
public class ProgramaAcademicoQueryServiceImpl extends QueryServiceImpl<ProgramaAcademico, ProgramaAcademicoDto, Integer> implements ProgramaAcademicoService {

	@Autowired
	private ProgramaAcademicoRepository repository;

	@Override
	protected ProgramaAcademicoRepository getRepository() {
		return repository;
	}

	@Override
	protected ProgramaAcademicoDto asModel(ProgramaAcademico entity) {
		val result = new ProgramaAcademicoDto();

		result.setId(entity.getId());
		result.setInstitucionId(entity.getInstitucionId());
		result.setNombre(entity.getNombre());

		return result;
	}

}