package co.com.orbitta.cibercolegios.rutas.service.impl.tracking;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.rutas.domain.tracking.LogRuta;
import co.com.orbitta.cibercolegios.rutas.dto.tracking.LogRutaDto;
import co.com.orbitta.cibercolegios.rutas.repository.EstadoRutaRepository;
import co.com.orbitta.cibercolegios.rutas.repository.RutaRepository;
import co.com.orbitta.cibercolegios.rutas.repository.tracking.LogRutaRepository;
import co.com.orbitta.cibercolegios.rutas.service.api.tracking.LogRutaService;
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
		val result = new LogRutaDto();

		result.setId(entity.getId());
		result.setRutaId(entity.getRuta().getId());
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