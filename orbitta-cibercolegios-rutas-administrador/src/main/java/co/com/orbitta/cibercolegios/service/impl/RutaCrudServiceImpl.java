package co.com.orbitta.cibercolegios.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.domain.Ruta;
import co.com.orbitta.cibercolegios.repository.RutaRepository;
import co.com.orbitta.cibercolegios.rutas.dto.RutaDto;
import co.com.orbitta.cibercolegios.service.api.RutaCrudService;
import co.com.orbitta.core.services.crud.impl.CrudServiceImpl;
import lombok.val;

@Service
public class RutaCrudServiceImpl extends CrudServiceImpl<Ruta, RutaDto, Integer> implements RutaCrudService {

	@Autowired
	private RutaRepository repository;

	@Override
	protected RutaRepository getRepository() {
		return repository;
	}

	@Override
	public RutaDto asModel(Ruta entity) {
		val result = new RutaDto();

		result.setId(entity.getId());

		result.setInstitucionId(entity.getInstitucionId());
		result.setCodigo(entity.getCodigo());
		result.setDescripcion(entity.getDescripcion());
		result.setMarca(entity.getMarca());
		result.setPlaca(entity.getPlaca());
		result.setMovil(entity.getMovil());
		result.setConductorNombres(entity.getConductorNombres());
		result.setMonitorId(entity.getMonitorId());
		result.setCapacidadMaxima(entity.getCapacidadMaxima());

		return result;
	}

	@Override
	protected Ruta mergeEntity(RutaDto model, Ruta entity) {

		entity.setInstitucionId(model.getInstitucionId());
		entity.setCodigo(model.getCodigo());
		entity.setDescripcion(model.getDescripcion());
		entity.setMarca(model.getMarca());
		entity.setPlaca(model.getPlaca());
		entity.setMovil(model.getMovil());
		entity.setConductorNombres(model.getConductorNombres());
		entity.setMonitorId(model.getMonitorId());
		entity.setCapacidadMaxima(model.getCapacidadMaxima());

		return entity;
	}

	@Override
	protected Ruta newEntity() {
		return new Ruta();
	}

	@Override
	public List<RutaDto> findAllByMonitorId(int monitorId) {
		val optional = getRepository().findAllByMonitorId(monitorId);

		val result = asModels(optional);
		return result;
	}

	@Override
	public List<RutaDto> findAllByInstitucionId(int institucionId) {
		val optional = getRepository().findAllByInstitucionId(institucionId);

		val result = asModels(optional);
		return result;
	}
}