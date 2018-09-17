package co.com.orbitta.cibercolegios.rutas.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.rutas.domain.Ruta;
import co.com.orbitta.cibercolegios.rutas.dto.RutaDto;
import co.com.orbitta.cibercolegios.rutas.repository.RutaRepository;
import co.com.orbitta.cibercolegios.rutas.service.api.RutaQueryService;
import co.com.orbitta.core.services.crud.impl.QueryServiceImpl;
import lombok.val;

@Service
public class RutaQueryServiceImpl extends QueryServiceImpl<Ruta, RutaDto, Integer> implements RutaQueryService {

	@Autowired
	private RutaRepository repository;

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
				.institucionId(entity.getInstitucionId())
				.x(entity.getX())
				.y(entity.getY())
				.monitorId(entity.getMonitorId())
				.conductorId(entity.getConductorId())
				.capacidad(entity.getCapacidad())

				.build();
		// @formatter:on
		return result;
	}

	@Override
	public List<RutaDto> findAllByMonitorId(int monitorId) {
		val optional = getRepository().findAllByMonitorId(monitorId);

		val result = asModels(optional);
		return result;
	}
}