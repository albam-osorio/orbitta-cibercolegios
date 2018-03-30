package co.com.orbitta.cibercolegios.rutas.client.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.constants.RestConstants;
import co.com.orbitta.cibercolegios.dto.LogUsuarioDto;
import co.com.orbitta.cibercolegios.rutas.client.components.RutasRestProperties;
import co.com.orbitta.cibercolegios.rutas.client.service.api.LogUsuarioLocalService;
import co.com.orbitta.core.web.client.service.impl.LocalServiceImpl;

@Service
public class LogUsuarioLocalServiceImpl extends LocalServiceImpl<LogUsuarioDto, Integer>
		implements LogUsuarioLocalService {

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
	protected Class<LogUsuarioDto> getResponseType() {
		return LogUsuarioDto.class;
	}

	@Override
	protected Class<LogUsuarioDto[]> getArrayReponseType() {
		return LogUsuarioDto[].class;
	}
}