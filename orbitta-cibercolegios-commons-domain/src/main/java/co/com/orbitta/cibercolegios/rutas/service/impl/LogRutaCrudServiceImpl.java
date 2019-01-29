package co.com.orbitta.cibercolegios.rutas.service.impl;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.rutas.domain.tracking.LogRuta;
import co.com.orbitta.cibercolegios.rutas.dto.LogRutaDto;
import co.com.orbitta.cibercolegios.rutas.repository.EstadoRutaRepository;
import co.com.orbitta.cibercolegios.rutas.repository.RutaRepository;
import co.com.orbitta.cibercolegios.rutas.repository.tracking.LogRutaRepository;
import co.com.orbitta.cibercolegios.rutas.service.api.LogRutaService;
import co.com.orbitta.core.services.crud.impl.CrudServiceImpl;
import lombok.val;

@Service
public class LogRutaCrudServiceImpl extends CrudServiceImpl<LogRuta, LogRutaDto, Integer> implements LogRutaService {

	@Autowired
	private LogRutaRepository repository;

	@Autowired
	private RutaRepository rutaRepository;

	@Autowired
	private EstadoRutaRepository estadoRepository;

	@Override
	protected LogRutaRepository getRepository() {
		return repository;
	}

	@Override
	public LogRutaDto asModel(LogRuta entity) {
		val model = newModel();
		mapModel(entity, model);
		
		model.setRutaId(entity.getRuta().getId());
		model.setSentido(entity.getSentido());

		model.setEstadoId(entity.getEstado().getId());
		model.setEstadoDescripcion(entity.getEstado().getDescripcion());
		model.setTipoEstado(entity.getEstado().getTipo());

		model.setX(entity.getX());
		model.setY(entity.getY());

		return model;
	}

	@Override
	protected LogRuta mergeEntity(LogRutaDto model, LogRuta entity) {
		val ruta = rutaRepository.findById(model.getRutaId());
		val estado = estadoRepository.findById(model.getEstadoId());

		entity.setRuta(ruta.get());
		entity.setSentido(model.getSentido());
		entity.setEstado(estado.get());
		entity.setX(model.getX());
		entity.setY(model.getY());

		entity.setVersion(model.getVersion());

		return entity;
	}

	@Override
	protected LogRutaDto newModel() {
		return new LogRutaDto();
	}
	
	@Override
	protected LogRuta newEntity() {
		return new LogRuta();
	}

	@Override
	public Optional<LogRutaDto> findUltimoLogRutaByRutaIdAndFechaUltimoRecorrido(int rutaId, LocalDate fechaUltimoRecorrido) {
		val fechaCreacion = fechaUltimoRecorrido.atStartOfDay();
		val optional = getRepository().findFirstByRutaIdAndFechaCreacionGreaterThanOrderByIdDesc(rutaId, fechaCreacion);

		val result = asModel(optional);
		return result;
	}


}