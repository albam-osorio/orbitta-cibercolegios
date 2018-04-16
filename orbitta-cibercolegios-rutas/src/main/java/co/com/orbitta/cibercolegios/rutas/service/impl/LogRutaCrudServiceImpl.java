package co.com.orbitta.cibercolegios.rutas.service.impl;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.dto.LogRutaDto;
import co.com.orbitta.cibercolegios.rutas.domain.LogRuta;
import co.com.orbitta.cibercolegios.rutas.repository.EstadoRutaRepository;
import co.com.orbitta.cibercolegios.rutas.repository.RutaRepository;
import co.com.orbitta.cibercolegios.rutas.repository.LogRutaRepository;
import co.com.orbitta.cibercolegios.rutas.service.api.LogRutaCrudService;
import co.com.orbitta.core.services.crud.impl.CrudServiceImpl;
import lombok.val;

@Service
public class LogRutaCrudServiceImpl extends CrudServiceImpl<LogRuta, LogRutaDto, Integer>
		implements LogRutaCrudService {

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

		// @formatter:off
		val result = LogRutaDto
				.builder()
				.id(entity.getId())
				.rutaId(entity.getRuta().getId())
				.sentido(entity.getSentido())
				.fechaHora(entity.getFechaHora())
				.estadoId(entity.getEstadoRuta().getId())
				.x(entity.getX())
				.y(entity.getY())

				.build();
		// @formatter:on
		return result;
	}

	@Override
	protected LogRuta asEntity(LogRutaDto model, LogRuta entity) {
		val ruta = rutaRepository.findById(model.getRutaId());
		val estadoRuta = estadoRepository.findById(model.getEstadoId());

		entity.setRuta(ruta.get());
		entity.setSentido(model.getSentido());
		entity.setFechaHora(model.getFechaHora());
		entity.setEstadoRuta(estadoRuta.get());
		entity.setX(model.getX());
		entity.setY(model.getY());

		return entity;
	}

	@Override
	protected LogRuta newEntity() {
		return new LogRuta();
	}

	@Override
	public Optional<LogRutaDto> findSiguienteUbicacion(int rutaId, int id) {
		val optional = getRepository().findFirstByRutaIdAndIdGreaterThanOrderById(rutaId, id);

		val result = asModel(optional);
		return result;
	}

	@Override
	public Optional<Integer> findIdUltimoLogRutaDelDiaByRutaId(int rutaId) {
//		val fechaHora = fecha.atStartOfDay();
//		val optional = getRepository().findFirstByRutaIdAndFechaHoraGreaterThanOrderByIdDesc(rutaId, fechaHora);
//
//		val result = asModel(optional);
//		return result;
		return null;
	}
}