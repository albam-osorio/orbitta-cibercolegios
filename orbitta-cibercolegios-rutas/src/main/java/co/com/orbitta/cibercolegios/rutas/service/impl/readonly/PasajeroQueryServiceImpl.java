package co.com.orbitta.cibercolegios.rutas.service.impl.readonly;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.dto.readonly.PasajeroDto;
import co.com.orbitta.cibercolegios.rutas.domain.readonly.Pasajero;
import co.com.orbitta.cibercolegios.rutas.repository.readonly.PasajeroRepository;
import co.com.orbitta.cibercolegios.rutas.service.api.readonly.PasajeroQueryService;
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
				.usuarioId(entity.getUsuario().getId())
				.direccionId(entity.getDireccion().getId())
				.fechaHora(entity.getFechaHora())
				.secuencia(entity.getSecuencia())
				
				.build();
		// @formatter:on
		return result;
	}

	@Override
	public List<PasajeroDto> findAllByRutaId(int rutaId) {
		val ids = getRepository().findAllCurrentByRutaId(rutaId);
		val entities = getRepository().findAllById(ids);
		
		val result = asModels(entities);
		return result;
	}
	
	@Override
	public int numeroParadas(int rutaId) {
		val ids = getRepository().findAllCurrentByRutaId(rutaId);
		return ids.size();
	}

}