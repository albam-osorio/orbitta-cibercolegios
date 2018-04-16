package co.com.orbitta.cibercolegios.rutas.client.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.constants.RutasRestConstants;
import co.com.orbitta.cibercolegios.dto.LogUsuarioRutaDto;
import co.com.orbitta.cibercolegios.rutas.client.components.RutasRestProperties;
import co.com.orbitta.cibercolegios.rutas.client.service.api.LogUsuarioLocalService;
import co.com.orbitta.core.web.client.service.impl.LocalServiceImpl;

@Service
public class LogUsuarioLocalServiceImpl extends LocalServiceImpl<LogUsuarioRutaDto, Integer>
		implements LogUsuarioLocalService {

	@Autowired
	private RutasRestProperties properties;

	protected RutasRestProperties getProperties() {
		return properties;
	}

	@Override
	protected String getResourceName() {
		return RutasRestConstants.direccionUsuario;
	}

	@Override
	protected Class<LogUsuarioRutaDto> getResponseType() {
		return LogUsuarioRutaDto.class;
	}

	@Override
	protected Class<LogUsuarioRutaDto[]> getArrayReponseType() {
		return LogUsuarioRutaDto[].class;
	}
}