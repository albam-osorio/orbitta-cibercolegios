package co.com.orbitta.cibercolegios.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.domain.Pasajero;
import co.com.orbitta.cibercolegios.repository.PasajeroRepository;
import co.com.orbitta.cibercolegios.rutas.dto.PasajeroDto;
import co.com.orbitta.cibercolegios.service.api.PasajeroQueryService;
import co.com.orbitta.core.services.crud.impl.QueryServiceImpl;
import lombok.val;

@Service
public class PasajeroQueryServiceImpl extends QueryServiceImpl<Pasajero, PasajeroDto, Integer>
		implements PasajeroQueryService {

	@Autowired
	private PasajeroRepository repository;

	@Override
	protected PasajeroRepository getRepository() {
		return repository;
	}

	@Override
	public PasajeroDto asModel(Pasajero entity) {

		// @formatter:off
		val result = PasajeroDto
				.builder()
				.id(entity.getId())
				.rutaId(entity.getRuta().getId())
				.secuencia(entity.getSecuencia())
				.usuarioId(entity.getUsuarioId())
				.direccionId(entity.getDireccion().getId())

				.build();
		// @formatter:on
		return result;
	}

	@Override
	public List<PasajeroDto> findAllByRutaId(int rutaId) {
		val entities = getRepository().findAllByRutaId(rutaId);

		val result = asModels(entities);
		return result;
	}

	@Override
	public Optional<PasajeroDto> findByUsuarioId(int usuarioId) {
		val entities = getRepository().findAllByUsuarioId(usuarioId);

		if (entities.size() > 0) {
			return Optional.of(asModel(entities.get(0)));
		} else {
			return Optional.empty();
		}
	}
}