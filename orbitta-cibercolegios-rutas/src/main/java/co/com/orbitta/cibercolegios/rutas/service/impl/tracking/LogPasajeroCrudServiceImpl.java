package co.com.orbitta.cibercolegios.rutas.service.impl.tracking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.rutas.domain.tracking.LogPasajero;
import co.com.orbitta.cibercolegios.rutas.dto.tracking.LogPasajeroDto;
import co.com.orbitta.cibercolegios.rutas.repository.EstadoPasajeroRepository;
import co.com.orbitta.cibercolegios.rutas.repository.RutaRepository;
import co.com.orbitta.cibercolegios.rutas.repository.tracking.LogPasajeroRepository;
import co.com.orbitta.cibercolegios.rutas.service.api.tracking.LogPasajeroService;
import co.com.orbitta.core.services.crud.impl.CrudServiceImpl;
import lombok.val;

@Service
public class LogPasajeroCrudServiceImpl extends CrudServiceImpl<LogPasajero, LogPasajeroDto, Integer>
		implements LogPasajeroService {

	@Autowired
	private LogPasajeroRepository repository;

	@Autowired
	private RutaRepository rutaRepository;

	@Autowired
	private EstadoPasajeroRepository estadoPasajeroRepository;

	@Override
	protected LogPasajeroRepository getRepository() {
		return repository;
	}

	@Override
	public LogPasajeroDto asModel(LogPasajero entity) {

		val result = new LogPasajeroDto();

		result.setId(entity.getId());
		result.setRutaId(entity.getRuta().getId());
		result.setUsuarioId(entity.getUsuarioId());
		
		result.setSentido(entity.getSentido());
		result.setEstadoId(entity.getEstado().getId());
		result.setEstadoDescripcion(entity.getEstado().getDescripcion());
		result.setTipoEstado(entity.getEstado().getTipo());

		result.setX(entity.getX());
		result.setY(entity.getY());
		
		result.setVersion(entity.getVersion());
		result.setFechaCreacion(entity.getFechaCreacion());
		result.setFechaModificacion(entity.getFechaModificacion());

		return result;
	}

	@Override
	protected LogPasajero mergeEntity(LogPasajeroDto model, LogPasajero entity) {
		val ruta = rutaRepository.findById(model.getRutaId());
		val estado = estadoPasajeroRepository.findById(model.getEstadoId());

		entity.setRuta(ruta.get());
		entity.setUsuarioId(model.getUsuarioId());
		entity.setSentido(entity.getSentido());
		entity.setEstado(estado.get());
		entity.setX(model.getX());
		entity.setY(model.getY());

		entity.setVersion(model.getVersion());

		return entity;
	}

	@Override
	protected LogPasajero newEntity() {
		return new LogPasajero();
	}
}