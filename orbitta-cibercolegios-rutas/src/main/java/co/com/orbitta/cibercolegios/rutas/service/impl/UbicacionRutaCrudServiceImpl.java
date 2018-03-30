package co.com.orbitta.cibercolegios.rutas.service.impl;

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
public class UbicacionRutaCrudServiceImpl extends CrudServiceImpl<UbicacionRuta, UbicacionRutaDto, UbicacionRutaDto, Integer>
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
	protected UbicacionRutaDto getModelFromEntity(UbicacionRuta entity) {

		// @formatter:off
		val result = UbicacionRutaDto
				.builder()
				.id(entity.getId())
				.fecha(entity.getFecha())
				.ubicacionLat(entity.getUbicacionLat())
				.ubicacionLon(entity.getUbicacionLon())
				.ubicacionGeo(entity.getUbicacionGeo())
				.sentido(entity.getSentido())
				.rutaId(entity.getRuta().getId())
				.estadoId(entity.getEstado().getId())

				.build();
		// @formatter:on
		return result;
	}

	@Override
	protected UbicacionRutaDto getItemModelFromEntity(UbicacionRuta entity) {
		return getModelFromEntity(entity);
	}

	@Override
	protected UbicacionRuta mapModelToEntity(UbicacionRutaDto model, UbicacionRuta entity) {
		val ruta = rutaRepository.findById(model.getRutaId());
		val estado = estadoRepository.findById(model.getEstadoId());

		entity.setFecha(model.getFecha());
		entity.setUbicacionLat(model.getUbicacionLat());
		entity.setUbicacionLon(model.getUbicacionLon());
		entity.setUbicacionGeo(model.getUbicacionGeo());
		entity.setSentido(model.getSentido());
		entity.setRuta(ruta.get());
		entity.setEstado(estado.get());

		return entity;
	}

	@Override
	protected UbicacionRuta getNewEntity() {
		return new UbicacionRuta();
	}
}