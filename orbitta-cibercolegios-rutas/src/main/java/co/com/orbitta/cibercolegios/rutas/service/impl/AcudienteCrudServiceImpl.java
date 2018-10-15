package co.com.orbitta.cibercolegios.rutas.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.rutas.domain.Acudiente;
import co.com.orbitta.cibercolegios.rutas.dto.AcudienteDto;
import co.com.orbitta.cibercolegios.rutas.repository.AcudienteRepository;
import co.com.orbitta.cibercolegios.rutas.service.api.AcudienteService;
import co.com.orbitta.core.services.crud.impl.CrudServiceImpl;
import lombok.val;

@Service
public class AcudienteCrudServiceImpl extends CrudServiceImpl<Acudiente, AcudienteDto, Integer>
		implements AcudienteService {

	@Autowired
	private AcudienteRepository repository;

	@Override
	protected AcudienteRepository getRepository() {
		return repository;
	}

	@Override
	public AcudienteDto asModel(Acudiente entity) {

		val result = new AcudienteDto();

		result.setId(entity.getId());
		result.setUsuarioId(entity.getUsuarioId());
		result.setToken(entity.getToken());

		result.setVersion(entity.getVersion());
		result.setFechaCreacion(entity.getFechaCreacion());
		result.setCreadoPor(entity.getCreadoPor());
		result.setFechaModificacion(entity.getFechaModificacion());
		result.setModificadoPor(entity.getModificadoPor());

		return result;
	}

	@Override
	protected Acudiente mergeEntity(AcudienteDto model, Acudiente entity) {
		entity.setUsuarioId(model.getUsuarioId());
		entity.setToken(model.getToken());

		entity.setVersion(model.getVersion());
		return entity;
	}

	@Override
	protected Acudiente newEntity() {
		return new Acudiente();
	}
}