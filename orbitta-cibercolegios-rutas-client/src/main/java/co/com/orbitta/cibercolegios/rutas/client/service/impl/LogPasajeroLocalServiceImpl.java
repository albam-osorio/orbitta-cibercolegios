package co.com.orbitta.cibercolegios.rutas.client.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.rutas.client.components.RutasRestProperties;
import co.com.orbitta.cibercolegios.rutas.client.service.api.LogPasajeroLocalService;
import co.com.orbitta.cibercolegios.rutas.constants.RutasRestConstants;
import co.com.orbitta.cibercolegios.rutas.dto.LogPasajeroDto;
import co.com.orbitta.core.web.client.service.impl.LocalCrudServiceImpl;

@Service
public class LogPasajeroLocalServiceImpl extends LocalCrudServiceImpl<LogPasajeroDto, Integer>
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