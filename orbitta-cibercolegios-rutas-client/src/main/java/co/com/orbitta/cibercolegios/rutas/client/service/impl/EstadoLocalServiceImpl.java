package co.com.orbitta.cibercolegios.rutas.client.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.constants.RestConstants;
import co.com.orbitta.cibercolegios.dto.EstadoDto;
import co.com.orbitta.cibercolegios.rutas.client.components.RutasRestProperties;
import co.com.orbitta.cibercolegios.rutas.client.service.api.EstadoLocalService;
import co.com.orbitta.core.web.client.service.impl.LocalServiceImpl;

@Service
public class EstadoLocalServiceImpl extends LocalServiceImpl<EstadoDto, Integer>
		implements EstadoLocalService {

	@Autowired
	private RutasRestProperties properties;

	protected RutasRestProperties getProperties() {
		return properties;
	}

	@Override
	protected String getResourceName() {
		return RestConstants.direccionUsuario;
	}

	@Override
	protected Class<EstadoDto> getResponseType() {
		return EstadoDto.class;
	}

	@Override
	protected Class<EstadoDto[]> getArrayReponseType() {
		return EstadoDto[].class;
	}
}