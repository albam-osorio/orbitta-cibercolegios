package co.com.orbitta.cibercolegios.rutas.service.impl.chat;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.rutas.domain.chat.Conversacion;
import co.com.orbitta.cibercolegios.rutas.dto.chat.ConversacionDto;
import co.com.orbitta.cibercolegios.rutas.dto.chat.DatosConversacionDto;
import co.com.orbitta.cibercolegios.rutas.repository.RutaRepository;
import co.com.orbitta.cibercolegios.rutas.repository.chat.ConversacionRepository;
import co.com.orbitta.cibercolegios.rutas.service.api.chat.ConversacionService;
import co.com.orbitta.cibercolegios.rutas.service.api.ciber.CiberService;
import co.com.orbitta.core.services.crud.impl.CrudServiceImpl;
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
		val result = new ConversacionDto();

		result.setId(entity.getId());
		result.setRutaId(entity.getRuta().getId());
		result.setUsuarioMonitorId(entity.getRuta().getMonitorId());
		result.setUsuarioAcudienteId(entity.getUsuarioAcudienteId());
		result.setUsuarioPasajeroId(entity.getUsuarioPasajeroId());

		result.setVersion(entity.getVersion());
		result.setFechaCreacion(entity.getFechaCreacion());
		result.setFechaModificacion(entity.getFechaModificacion());

		return result;
	}

	@Override
	protected Conversacion mergeEntity(ConversacionDto model, Conversacion entity) {
		val ruta = rutaRepository.getOne(model.getRutaId());

		entity.setRuta(ruta);
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

		val monitor = ciberService.findUsuarioById(entity.getRuta().getMonitorId()).get();
		val acudiente = ciberService.findUsuarioById(entity.getUsuarioAcudienteId()).get();
		val pasajero = ciberService.findUsuarioById(entity.getUsuarioPasajeroId()).get();

		result.setConversacionId(entity.getId());
		result.setRutaId(entity.getRuta().getId());
		result.setRutaCodigo(entity.getRuta().getCodigo());

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