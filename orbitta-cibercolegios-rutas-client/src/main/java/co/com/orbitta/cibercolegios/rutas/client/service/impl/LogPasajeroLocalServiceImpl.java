package co.com.orbitta.cibercolegios.rutas.client.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.constants.RutasRestConstants;
import co.com.orbitta.cibercolegios.dto.LogPasajeroDto;
import co.com.orbitta.cibercolegios.rutas.client.components.RutasRestProperties;
import co.com.orbitta.cibercolegios.rutas.client.service.api.LogPasajeroLocalService;
import co.com.orbitta.core.web.client.service.impl.LocalServiceImpl;

@Service
public class LogPasajeroLocalServiceImpl extends LocalServiceImpl<LogPasajeroDto, Integer>
		implements LogPasajeroLocalService {

	@Autowired
	private RutasRestProperties properties;

	protected RutasRestProperties getProperties() {
		return properties;
	}

	@Override
	protected String getResourceName() {
		return RutasRestConstants.logPasajero;
	}

	@Override
	protected Class<LogPasajeroDto> getResponseType() {
		return LogPasajeroDto.class;
	}

	@Override
	protected Class<LogPasajeroDto[]> getArrayReponseType() {
		return LogPasajeroDto[].class;
	}
}