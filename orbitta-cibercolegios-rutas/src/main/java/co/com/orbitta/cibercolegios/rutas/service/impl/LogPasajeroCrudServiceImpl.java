package co.com.orbitta.cibercolegios.rutas.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.rutas.domain.LogPasajero;
import co.com.orbitta.cibercolegios.rutas.dto.LogPasajeroDto;
import co.com.orbitta.cibercolegios.rutas.repository.EstadoPasajeroRepository;
import co.com.orbitta.cibercolegios.rutas.repository.LogPasajeroRepository;
import co.com.orbitta.cibercolegios.rutas.repository.PasajeroRepository;
import co.com.orbitta.cibercolegios.rutas.service.api.LogPasajeroCrudService;
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

		// @formatter:off
		val result = LogPasajeroDto
				.builder()
				.id(entity.getId())
				.pasajeroId(entity.getPasajero().getId())
				.fechaHora(entity.getFechaHora())
				.sentido(entity.getSentido())
				.estadoId(entity.getEstado().getId())

				.build();
		// @formatter:on
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