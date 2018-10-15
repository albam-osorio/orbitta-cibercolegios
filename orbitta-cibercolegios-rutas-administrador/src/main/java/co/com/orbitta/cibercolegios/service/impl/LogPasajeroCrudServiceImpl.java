package co.com.orbitta.cibercolegios.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.domain.LogPasajero;
import co.com.orbitta.cibercolegios.repository.EstadoPasajeroRepository;
import co.com.orbitta.cibercolegios.repository.LogPasajeroRepository;
import co.com.orbitta.cibercolegios.repository.PasajeroRepository;
import co.com.orbitta.cibercolegios.rutas.dto.tracking.LogPasajeroDto;
import co.com.orbitta.cibercolegios.service.api.LogPasajeroCrudService;
import co.com.orbitta.core.services.crud.impl.CrudServiceImpl;
import lombok.val;

@Service
public class LogPasajeroCrudServiceImpl extends CrudServiceImpl<LogPasajero, LogPasajeroDto, Integer>
		implements LogPasajeroCrudService {

	@Autowired
	private LogPasajeroRepository repository;

	@Autowired
	private PasajeroRepository pasajeroRepository;

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
		result.setPasajeroId(entity.getPasajero().getId());
		result.setFechaHora(entity.getFechaHora());
		result.setSentido(entity.getSentido());

		result.setEstadoId(entity.getEstado().getId());
		result.setEstadoNombre(entity.getEstado().getDescripcion());
		result.setTipoEstado(entity.getEstado().getTipo());

		return result;
	}

	@Override
	protected LogPasajero mergeEntity(LogPasajeroDto model, LogPasajero entity) {
		val pasajero = pasajeroRepository.findById(model.getPasajeroId());
		val estado = estadoPasajeroRepository.findById(model.getEstadoId());

		entity.setPasajero(pasajero.get());
		entity.setFechaHora(model.getFechaHora());
		entity.setSentido(entity.getSentido());
		entity.setEstado(estado.get());

		return entity;
	}

	@Override
	protected LogPasajero newEntity() {
		return new LogPasajero();
	}

	@Override
	public List<LogPasajeroDto> findUltimosLogPasajerosByRutaId(int rutaId) {
		val fecha = LocalDate.now().atStartOfDay();

		val ids = getRepository().findAllMaxIdByRutaIdAndFechaHoraGreaterThan(rutaId, fecha);
		val result = findAllById(ids.stream().map(a -> a.intValue()).collect(Collectors.toList()));
		return result;
	}

	@Override
	public Optional<LogPasajeroDto> findUltimoLogPasajerosByRutaIdAndPasajeroId(int rutaId, int pasajeroId) {
		val fecha = LocalDate.now().atStartOfDay();

		val optional = getRepository().findAllMaxIdByRutaIdAndFechaGreaterThanAndPasajeroId(rutaId, pasajeroId, fecha);
		if (optional.isPresent()) {
			val result = findById(optional.get().intValue());
			return result;
		} else {
			return Optional.empty();
		}
	}
}