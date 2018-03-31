package co.com.orbitta.cibercolegios.rutas.service.impl;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.dto.UbicacionRutaDto;
import co.com.orbitta.cibercolegios.rutas.domain.UbicacionRuta;
import co.com.orbitta.cibercolegios.rutas.repository.EstadoRepository;
import co.com.orbitta.cibercolegios.rutas.repository.RutaRepository;
import co.com.orbitta.cibercolegios.rutas.repository.UbicacionRutaRepository;
import co.com.orbitta.cibercolegios.rutas.service.api.UbicacionRutaCrudService;
import co.com.orbitta.core.services.crud.impl.CrudServiceImpl;
import lombok.val;

@Service
public class UbicacionRutaCrudServiceImpl extends CrudServiceImpl<UbicacionRuta, UbicacionRutaDto, Integer>
		implements UbicacionRutaCrudService {

	@Autowired
	private UbicacionRutaRepository repository;

	@Autowired
	private RutaRepository rutaRepository;

	@Autowired
	private EstadoRepository estadoRepository;

	@Override
	protected UbicacionRutaRepository getRepository() {
		return repository;
	}

	@Override
	public UbicacionRutaDto asModel(UbicacionRuta entity) {

		// @formatter:off
		val result = UbicacionRutaDto
				.builder()
				.id(entity.getId())
				.rutaId(entity.getRuta().getId())
				.sentido(entity.getSentido())
				.fechaHora(entity.getFechaHora())
				.estadoId(entity.getEstado().getId())
				.ubicacionLon(entity.getUbicacionLon())
				.ubicacionLat(entity.getUbicacionLat())
				.ubicacionGeo(entity.getUbicacionGeo())

				.build();
		// @formatter:on
		return result;
	}

	@Override
	protected UbicacionRuta asEntity(UbicacionRutaDto model, UbicacionRuta entity) {
		val ruta = rutaRepository.findById(model.getRutaId());
		val estado = estadoRepository.findById(model.getEstadoId());

		entity.setRuta(ruta.get());
		entity.setSentido(model.getSentido());
		entity.setFechaHora(model.getFechaHora());
		entity.setEstado(estado.get());
		entity.setUbicacionLon(model.getUbicacionLon());
		entity.setUbicacionLat(model.getUbicacionLat());
		entity.setUbicacionGeo(model.getUbicacionGeo());

		return entity;
	}

	@Override
	protected UbicacionRuta newEntity() {
		return new UbicacionRuta();
	}

	@Override
	public Optional<UbicacionRutaDto> findSiguienteUbicacion(int rutaId, int id) {
		val optional = getRepository().findFirstByRutaIdAndIdGreaterThanOrderById(rutaId, id);

		val result = asModel(optional);
		return result;
	}

	@Override
	public Optional<UbicacionRutaDto> findUltimaUbicacionByRutaIdAndFecha(int rutaId, LocalDate fecha) {
		val fechaHora = fecha.atStartOfDay();
		val optional = getRepository().findFirstByRutaIdAndFechaHoraGreaterThanOrderByIdDesc(rutaId, fechaHora);

		val result = asModel(optional);
		return result;
	}
}