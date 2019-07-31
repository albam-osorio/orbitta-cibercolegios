package com.tbf.cibercolegios.api.routes.services.impl;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tbf.cibercolegios.api.ciber.services.api.CiberService;
import com.tbf.cibercolegios.api.core.services.impl.CrudServiceImpl;
import com.tbf.cibercolegios.api.model.routes.Conversacion;
import com.tbf.cibercolegios.api.routes.model.graph.ConversacionDto;
import com.tbf.cibercolegios.api.routes.model.graph.chat.DatosConversacionDto;
import com.tbf.cibercolegios.api.routes.repository.ConversacionRepository;
import com.tbf.cibercolegios.api.routes.repository.RutaRepository;
import com.tbf.cibercolegios.api.routes.services.api.ConversacionService;

import lombok.val;

@Service
public class ConversacionCrudServiceImpl extends CrudServiceImpl<Conversacion, ConversacionDto, Integer>
		implements ConversacionService {

	@Autowired
	private ConversacionRepository repository;

	@Autowired
	private RutaRepository rutaRepository;

	@Autowired
	private CiberService ciberService;

	@Override
	protected ConversacionRepository getRepository() {
		return repository;
	}

	@Override
	protected ConversacionDto asModel(Conversacion entity) {
		val model = newModel();
		mapModel(entity, model);

		model.setRutaId(entity.getRutaId());
		model.setUsuarioAcudienteId(entity.getUsuarioAcudienteId());
		model.setUsuarioPasajeroId(entity.getUsuarioPasajeroId());

		return model;
	}

	@Override
	protected Conversacion mergeEntity(ConversacionDto model, Conversacion entity) {
		entity.setRutaId(model.getRutaId());
		entity.setUsuarioAcudienteId(model.getUsuarioAcudienteId());
		entity.setUsuarioPasajeroId(model.getUsuarioPasajeroId());

		entity.setVersion(model.getVersion());

		return entity;
	}

	@Override
	protected Conversacion newEntity() {
		return new Conversacion();
	}

	@Override
	protected ConversacionDto newModel() {
		return new ConversacionDto();
	}

	// -----------------------------------------------------------------------------------
	// --
	// -----------------------------------------------------------------------------------
	@Override
	public Optional<DatosConversacionDto> findByRutaIdAndUsuarioAcudienteIdAndUsuarioPasajeroId(int rutaId,
			int acudienteId, int pasajeroId) {
		val optional = getRepository().findByRutaIdAndUsuarioAcudienteIdAndUsuarioPasajeroId(rutaId, acudienteId,
				pasajeroId);

		if (optional.isPresent()) {
			val result = asDto(optional.get());
			return Optional.of(result);
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<DatosConversacionDto> findByRutaId(int rutaId) {
		val entities = getRepository().findByRutaId(rutaId);

		// @formatter:off
		val comparator = Comparator
				.comparing(DatosConversacionDto::getEstudianteNombres)
				.thenComparing(Comparator.comparing(DatosConversacionDto::getAcudienteNombres));
		// @formatter:on

		val result = entities.stream().map(entity -> asDto(entity)).sorted(comparator).collect(Collectors.toList());

		return result;
	}

	protected DatosConversacionDto asDto(Conversacion entity) {
		val result = new DatosConversacionDto();
		int institucionId = 0;
		val ruta = rutaRepository.findById(entity.getRutaId()).get();
		val monitor = ciberService.findUsuarioMonitorByInstitucionIdAndUsuarioId(institucionId, ruta.getMonitorId())
				.get();
		val acudiente = ciberService.findUsuarioAcudienteByUsuarioId(entity.getUsuarioAcudienteId()).get();
		val pasajero = ciberService.findUsuarioByUsuarioId(entity.getUsuarioPasajeroId()).get();

		result.setConversacionId(entity.getId());
		result.setRutaId(entity.getRutaId());
		result.setRutaCodigo(ruta.getCodigo());

		result.setMonitorId(monitor.getId());
		result.setMonitorNombres(monitor.getNombre());
		result.setMonitorApellidos(monitor.getApellido());

		result.setAcudienteId(acudiente.getId());
		result.setAcudienteNombres(acudiente.getNombre());
		result.setAcudienteApellidos(acudiente.getApellido());

		result.setEstudianteId(pasajero.getId());
		result.setEstudianteNombres(pasajero.getNombre());
		result.setEstudianteApellidos(pasajero.getApellido());

		return result;
	}
}