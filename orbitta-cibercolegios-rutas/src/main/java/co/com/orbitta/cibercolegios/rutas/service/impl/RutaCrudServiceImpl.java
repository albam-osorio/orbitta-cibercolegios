package co.com.orbitta.cibercolegios.rutas.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.dto.RutaDto;
import co.com.orbitta.cibercolegios.rutas.domain.Ruta;
import co.com.orbitta.cibercolegios.rutas.repository.InstitucionRepository;
import co.com.orbitta.cibercolegios.rutas.repository.RutaRepository;
import co.com.orbitta.cibercolegios.rutas.repository.UsuarioRepository;
import co.com.orbitta.cibercolegios.rutas.service.api.RutaCrudService;
import co.com.orbitta.core.services.crud.impl.CrudServiceImpl;
import lombok.val;

@Service
public class RutaCrudServiceImpl extends CrudServiceImpl<Ruta, RutaDto, Integer> implements RutaCrudService {

	@Autowired
	private RutaRepository repository;

	@Autowired
	private InstitucionRepository institucionRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	protected RutaRepository getRepository() {
		return repository;
	}

	@Override
	public RutaDto asModel(Ruta entity) {

		// @formatter:off
		val result = RutaDto
				.builder()
				.id(entity.getId())
				.codigo(entity.getCodigo())
				.descripcion(entity.getDescripcion())
				.marca(entity.getMarca())
				.placa(entity.getPlaca())
				.movil(entity.getMovil())
				.capacidad(entity.getCapacidad())
				.institucionId(entity.getInstitucion().getId())
				.monitorId(entity.getMonitor().getId())
				.conductorId(entity.getConductor().getId())
				.x(entity.getX())
				.y(entity.getY())

				.build();
		// @formatter:on
		return result;
	}

	@Override
	protected Ruta asEntity(RutaDto model, Ruta entity) {
		val institucion = institucionRepository.findById(model.getInstitucionId());
		val monitor = usuarioRepository.findById(model.getMonitorId());
		val conductor = usuarioRepository.findById(model.getConductorId());

		entity.setCodigo(model.getCodigo());
		entity.setDescripcion(model.getDescripcion());
		entity.setMarca(model.getMarca());
		entity.setPlaca(model.getPlaca());
		entity.setMovil(model.getMovil());
		entity.setCapacidad(model.getCapacidad());
		entity.setInstitucion(institucion.get());
		entity.setMonitor(monitor.get());
		entity.setConductor(conductor.get());
		entity.setX(model.getX());
		entity.setY(model.getY());

		return entity;
	}

	@Override
	protected Ruta newEntity() {
		return new Ruta();
	}

	@Override
	public List<RutaDto> findAllByMonitorId(int monitorId) {
		val entities = getRepository().findAllByMonitorId(monitorId);
		
		val result = asModels(entities);
		return result;
	}
}