package co.com.orbitta.cibercolegios.rutas.client.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.constants.RestConstants;
import co.com.orbitta.cibercolegios.dto.LogDto;
import co.com.orbitta.cibercolegios.rutas.client.components.RutasRestProperties;
import co.com.orbitta.cibercolegios.rutas.client.service.api.LogLocalService;
import co.com.orbitta.core.web.client.service.impl.LocalServiceImpl;

@Service
public class LogLocalServiceImpl extends LocalServiceImpl<LogDto, Integer>
		implements LogLocalService {

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
	protected Class<LogDto> getResponseType() {
		return LogDto.class;
	}

	@Override
	protected Class<LogDto[]> getArrayReponseType() {
		return LogDto[].class;
	}
}