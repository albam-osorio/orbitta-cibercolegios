package co.com.orbitta.cibercolegios.rutas.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.rutas.domain.Acudiente;
import co.com.orbitta.cibercolegios.rutas.domain.Direccion;
import co.com.orbitta.cibercolegios.rutas.domain.Pasajero;
import co.com.orbitta.cibercolegios.rutas.domain.Ruta;
import co.com.orbitta.cibercolegios.rutas.dto.DireccionDto;
import co.com.orbitta.cibercolegios.rutas.dto.PasajeroDto;
import co.com.orbitta.cibercolegios.rutas.repository.AcudienteRepository;
import co.com.orbitta.cibercolegios.rutas.repository.DireccionRepository;
import co.com.orbitta.cibercolegios.rutas.repository.EstadoPasajeroRepository;
import co.com.orbitta.cibercolegios.rutas.repository.PasajeroRepository;
import co.com.orbitta.cibercolegios.rutas.repository.RutaRepository;
import co.com.orbitta.cibercolegios.rutas.service.api.PasajeroService;
import co.com.orbitta.core.services.crud.impl.CrudServiceImpl;
import lombok.val;

@Service
public class PasajeroCrudServiceImpl extends CrudServiceImpl<Pasajero, PasajeroDto, Integer>
		implements PasajeroService {

	@Autowired
	private PasajeroRepository repository;

	@Autowired
	private RutaRepository rutaRepository;

	@Autowired
	private DireccionRepository direccionRepository;

	@Autowired
	private AcudienteRepository acudienteRepository;

	@Autowired
	private EstadoPasajeroRepository estadoPasajeroRepository;

	@Override
	protected PasajeroRepository getRepository() {
		return repository;
	}

	@Override
	public PasajeroDto asModel(Pasajero entity) {
		val model = newModel();
		mapModel(entity, model);

		if (entity.getRuta() != null) {
			model.setRutaId(entity.getRuta().getId());
		}
		model.setUsuarioId(entity.getUsuarioId());
		model.setSecuenciaIda(entity.getSecuenciaIda());
		model.setDireccionIdaId(entity.getDireccionIda().getId());
		model.setSecuenciaRetorno(entity.getSecuenciaRetorno());
		model.setDireccionRetornoId(entity.getDireccionRetorno().getId());
		model.setEstadoId(entity.getEstado().getId());
		model.setTipoEstado(entity.getEstado().getTipo());
		model.setEstadoDescripcion(entity.getEstado().getDescripcion());

		model.setAcudientes(asAcudientes(entity));

		return model;
	}

	@Override
	protected PasajeroDto newModel() {
		return new PasajeroDto();
	}

	private List<Integer> asAcudientes(Pasajero entity) {
		val result = new ArrayList<Integer>();
		result.addAll(entity.getAcudientes());
		return result;
	}

	@Override
	protected Pasajero mergeEntity(PasajeroDto model, Pasajero entity) {
		Ruta ruta = null;
		if (model.getRutaId() != null) {
			ruta = rutaRepository.findById(model.getRutaId()).get();
		}
		val direccionIda = findDireccionById(model.getDireccionIdaId());
		val direccionRetorno = findDireccionById(model.getDireccionRetornoId());
		val estado = estadoPasajeroRepository.findById(model.getEstadoId());

		entity.setRuta(ruta);
		entity.setUsuarioId(model.getUsuarioId());
		entity.setSecuenciaIda(model.getSecuenciaIda());
		entity.setDireccionIda(direccionIda);
		entity.setSecuenciaRetorno(model.getSecuenciaRetorno());
		entity.setDireccionRetorno(direccionRetorno);

		entity.setEstado(estado.get());

		mergeItemEntities(model, entity);

		entity.setVersion(model.getVersion());
		return entity;
	}

	protected void mergeItemEntities(PasajeroDto model, Pasajero entity) {
		val inserted = new ArrayList<Integer>();
		val deleted = new ArrayList<Integer>();

		if (model.getAcudientes() == null) {
			model.setAcudientes(new ArrayList<>());
		}

		if (entity.getAcudientes() == null) {
			entity.setAcudientes(new ArrayList<>());
		}

		entity.getAcudientes().stream().forEach(a -> {
			if (!model.getAcudientes().contains(a)) {
				deleted.add(a);
			}
		});

		deleted.stream().forEach(a -> entity.getAcudientes().remove(a));

		model.getAcudientes().stream().forEach(a -> {
			if (!entity.getAcudientes().contains(a)) {
				inserted.add(a);
			}
		});

		entity.getAcudientes().addAll(inserted);
	}

	@Override
	protected Pasajero newEntity() {
		return new Pasajero();
	}

	@Override
	public List<PasajeroDto> findAllByRutaId(int rutaId) {
		val entities = getRepository().findAllByRutaId(rutaId);

		val result = asModels(entities);
		return result;
	}

	@Override
	public Optional<PasajeroDto> findByUsuarioId(int usuarioId) {
		val optional = getRepository().findByUsuarioId(usuarioId);

		val result = asModel(optional);
		return result;
	}

	protected Direccion findDireccionById(Integer id) {
		Direccion result = null;
		if (id != null) {
			val optional = direccionRepository.findById(id);
			if (optional.isPresent()) {
				result = optional.get();
			}
		}

		return result;
	}

	@Override
	public long countByRutaId(int rutaId) {
		val result = getRepository().countByRutaId(rutaId);

		return result;
	}

	@Override
	public PasajeroDto create(PasajeroDto model, DireccionDto direccionIda, DireccionDto direccionRetorno,
			List<Integer> usuariosAcudientesId) {
		Direccion direccion;

		direccion = asDireccion(direccionIda);
		direccion = direccionRepository.save(direccion);
		model.setDireccionIdaId(direccion.getId());

		direccion = asDireccion(direccionRetorno);
		direccion = direccionRepository.save(direccion);
		model.setDireccionRetornoId(direccion.getId());

		val acudientes = createAcudientes(usuariosAcudientesId);
		model.setAcudientes(acudientes);

		val result = create(model);
		return result;
	}

	@Override
	public PasajeroDto update(PasajeroDto model, DireccionDto direccionIda, DireccionDto direccionRetorno,
			List<Integer> usuariosAcudientesId) {
		Direccion direccion;
		direccion = mergeDireccion(direccionIda);
		direccionRepository.save(direccion);

		direccion = mergeDireccion(direccionRetorno);
		direccionRepository.save(direccion);

		val acudientes = createAcudientes(usuariosAcudientesId);
		model.setAcudientes(acudientes);

		val result = update(model);
		return result;
	}

	private Direccion asDireccion(DireccionDto direccionIda) {
		Direccion entity = new Direccion();

		entity.setInstitucionId(direccionIda.getInstitucionId());
		entity.setEstadoId(direccionIda.getEstadoId());
		entity.setPaisId(direccionIda.getPaisId());
		entity.setDepartamentoId(direccionIda.getDepartamentoId());
		entity.setCiudadId(direccionIda.getCiudadId());
		entity.setDireccion(direccionIda.getDireccion());
		entity.setX(null);
		entity.setY(null);
		return entity;
	}

	private Direccion mergeDireccion(DireccionDto direccionIda) {
		Direccion entity;
		entity = direccionRepository.findById(direccionIda.getId()).get();

		entity.setInstitucionId(direccionIda.getInstitucionId());
		entity.setPaisId(direccionIda.getPaisId());
		entity.setDepartamentoId(direccionIda.getDepartamentoId());
		entity.setCiudadId(direccionIda.getCiudadId());
		entity.setDireccion(direccionIda.getDireccion());
		entity.setX(null);
		entity.setY(null);
		return entity;
	}

	private List<Integer> createAcudientes(List<Integer> usuariosId) {
		val acudientes = acudienteRepository.findAllByUsuarioIdIn(usuariosId);

		val insert = new ArrayList<Acudiente>();
		for (val id : usuariosId) {
			val optional = acudientes.stream().filter(a -> id.equals(a.getUsuarioId())).findFirst();
			if (!optional.isPresent()) {
				val acudiente = new Acudiente();
				acudiente.setUsuarioId(id);
				insert.add(acudiente);
			}
		}

		if (!insert.isEmpty()) {
			acudientes.addAll(acudienteRepository.saveAll(insert));
		}

		val result = acudientes.stream().map(a -> a.getId()).collect(Collectors.toList());
		return result;
	}
}