package co.com.orbitta.cibercolegios.rutas.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.rutas.domain.Direccion;
import co.com.orbitta.cibercolegios.rutas.dto.DireccionDto;
import co.com.orbitta.cibercolegios.rutas.repository.DireccionRepository;
import co.com.orbitta.cibercolegios.rutas.repository.EstadoDireccionRepository;
import co.com.orbitta.cibercolegios.rutas.service.api.DireccionService;
import co.com.orbitta.core.services.crud.impl.CrudServiceImpl;
import lombok.val;

@Service
public class DireccionCrudServiceImpl extends CrudServiceImpl<Direccion, DireccionDto, Integer>
		implements DireccionService {

	@Autowired
	private DireccionRepository repository;

	@Autowired
	private EstadoDireccionRepository estadoDireccionRepository;

	@Override
	protected DireccionRepository getRepository() {
		return repository;
	}

	@Override
	public DireccionDto asModel(Direccion entity) {
		val result = new DireccionDto();

		result.setId(entity.getId());

		result.setInstitucionId(entity.getInstitucionId());
		result.setEstadoId(entity.getEstado().getId());
		result.setCiudadId(entity.getCiudadId());
		result.setDireccion(entity.getDireccion());
		result.setX(entity.getX());
		result.setY(entity.getY());

		result.setVersion(entity.getVersion());
		result.setFechaCreacion(entity.getFechaCreacion());
		result.setCreadoPor(entity.getCreadoPor());
		result.setFechaModificacion(entity.getFechaModificacion());
		result.setModificadoPor(entity.getModificadoPor());

		return result;
	}

	@Override
	protected Direccion mergeEntity(DireccionDto model, Direccion entity) {
		val estado = estadoDireccionRepository.findById(model.getEstadoId());

		entity.setInstitucionId(model.getInstitucionId());
		entity.setEstado(estado.get());
		entity.setCiudadId(model.getCiudadId());
		entity.setDireccion(model.getDireccion());
		entity.setX(model.getX());
		entity.setY(model.getY());
		
		entity.setVersion(model.getVersion());
		return entity;
	}

	@Override
	protected Direccion newEntity() {
		return new Direccion();
	}
}