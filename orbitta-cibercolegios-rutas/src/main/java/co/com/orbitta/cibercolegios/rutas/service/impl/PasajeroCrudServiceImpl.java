package co.com.orbitta.cibercolegios.rutas.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.rutas.domain.Direccion;
import co.com.orbitta.cibercolegios.rutas.domain.Pasajero;
import co.com.orbitta.cibercolegios.rutas.dto.PasajeroDto;
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
	private EstadoPasajeroRepository estadoPasajeroRepository;

	@Override
	protected PasajeroRepository getRepository() {
		return repository;
	}

	@Override
	public PasajeroDto asModel(Pasajero entity) {

		val result = new PasajeroDto();

		result.setId(entity.getId());

		result.setRutaId(entity.getRuta().getId());
		result.setUsuarioId(entity.getUsuarioId());
		result.setSecuenciaIda(entity.getSecuenciaIda());
		result.setDireccionIdaId(entity.getDireccionIda().getId());
		result.setSecuenciaRetorno(entity.getSecuenciaRetorno());
		result.setDireccionRetornoId(entity.getDireccionRetorno().getId());

		result.setEstadoId(entity.getEstado().getId());
		result.setTipoEstado(entity.getEstado().getTipo());
		result.setEstadoDescripcion(entity.getEstado().getDescripcion());

		result.setAcudientes(asAcudientes(entity));

		result.setVersion(entity.getVersion());
		result.setFechaCreacion(entity.getFechaCreacion());
		result.setCreadoPor(entity.getCreadoPor());
		result.setFechaModificacion(entity.getFechaModificacion());
		result.setModificadoPor(entity.getModificadoPor());

		return result;
	}

	private List<Integer> asAcudientes(Pasajero entity) {
		val result = new ArrayList<Integer>();
		result.addAll(entity.getAcudientes());
		return result;
	}

	@Override
	protected Pasajero mergeEntity(PasajeroDto model, Pasajero entity) {
		val ruta = rutaRepository.findById(model.getRutaId());
		val direccionIda = findDireccionById(model.getDireccionIdaId());
		val direccionRetorno = findDireccionById(model.getDireccionRetornoId());
		val estado = estadoPasajeroRepository.findById(model.getEstadoId());

		entity.setRuta(ruta.get());
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
}