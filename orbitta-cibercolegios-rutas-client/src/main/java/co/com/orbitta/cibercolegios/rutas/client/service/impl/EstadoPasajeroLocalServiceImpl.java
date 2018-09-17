package co.com.orbitta.cibercolegios.rutas.client.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.rutas.client.components.RutasRestProperties;
import co.com.orbitta.cibercolegios.rutas.client.service.api.EstadoPasajeroLocalService;
import co.com.orbitta.cibercolegios.rutas.constants.RutasRestConstants;
import co.com.orbitta.cibercolegios.rutas.dto.EstadoPasajeroDto;
import co.com.orbitta.core.web.client.service.impl.LocalCrudServiceImpl;

@Service
public class EstadoPasajeroLocalServiceImpl extends LocalCrudServiceImpl<EstadoPasajeroDto, Integer>
		implements EstadoPasajeroLocalService {

	@Autowired
	private RutasRestProperties properties;

	protected RutasRestProperties getProperties() {
		return properties;
	}

	@Override
	protected String getResourceName() {
		return RutasRestConstants.estadoPasajero;
	}

	@Override
	protected Class<EstadoPasajeroDto> getResponseType() {
		return EstadoPasajeroDto.class;
	}

	@Override
	protected Class<EstadoPasajeroDto[]> getArrayReponseType() {
		return EstadoPasajeroDto[].class;
	}
}