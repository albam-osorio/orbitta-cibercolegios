package co.com.orbitta.cibercolegios.rutas.service.impl;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.rutas.domain.LogRuta;
import co.com.orbitta.cibercolegios.rutas.dto.LogRutaDto;
import co.com.orbitta.cibercolegios.rutas.repository.EstadoRutaRepository;
import co.com.orbitta.cibercolegios.rutas.repository.LogRutaRepository;
import co.com.orbitta.cibercolegios.rutas.repository.RutaRepository;
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
				.fechaHora(entity.getFechaHora())
				.sentido(entity.getSentido())
				.estadoId(entity.getEstado().getId())
				.x(entity.getX())
				.y(entity.getY())

				.build();
		// @formatter:on
		return result;
	}

	@Override
	protected LogRuta mergeEntity(LogRutaDto model, LogRuta entity) {
		val ruta = rutaRepository.findById(model.getRutaId());
		val estadoRuta = estadoRepository.findById(model.getEstadoId());

		entity.setRuta(ruta.get());
		entity.setFechaHora(model.getFechaHora());
		entity.setSentido(model.getSentido());
		entity.setEstado(estadoRuta.get());
		entity.setX(model.getX());
		entity.setY(model.getY());

		return entity;
	}

	@Override
	protected LogRuta newEntity() {
		return new LogRuta();
	}

	@Override
	public Optional<LogRutaDto> findUltimoLogRutaByRutaId(int rutaId) {
		val fecha = LocalDate.now().atStartOfDay();
		val optional = getRepository().findFirstByRutaIdAndFechaHoraGreaterThanOrderByIdDesc(rutaId, fecha);

		val result = asModel(optional);
		return result;
	}
}