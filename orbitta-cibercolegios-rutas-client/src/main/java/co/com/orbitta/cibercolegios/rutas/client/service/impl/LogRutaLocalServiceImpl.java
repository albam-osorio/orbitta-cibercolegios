package co.com.orbitta.cibercolegios.rutas.client.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.rutas.client.components.RutasRestProperties;
import co.com.orbitta.cibercolegios.rutas.client.service.api.LogRutaLocalService;
import co.com.orbitta.cibercolegios.rutas.constants.RutasRestConstants;
import co.com.orbitta.cibercolegios.rutas.dto.LogRutaDto;
import co.com.orbitta.core.web.client.service.impl.LocalCrudServiceImpl;

@Service
public class LogRutaLocalServiceImpl extends LocalCrudServiceImpl<LogRutaDto, Integer>
		implements LogRutaLocalService {

	@Autowired
	private RutasRestProperties properties;

	protected RutasRestProperties getProperties() {
		return properties;
	}

	@Override
	protected String getResourceName() {
		return RutasRestConstants.logRuta;
	}

	@Override
	protected Class<LogRutaDto> getResponseType() {
		return LogRutaDto.class;
	}

	@Override
	protected Class<LogRutaDto[]> getArrayReponseType() {
		return LogRutaDto[].class;
	}
}