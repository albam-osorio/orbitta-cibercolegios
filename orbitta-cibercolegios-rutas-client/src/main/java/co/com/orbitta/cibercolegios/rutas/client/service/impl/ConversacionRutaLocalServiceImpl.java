package co.com.orbitta.cibercolegios.rutas.client.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.constants.RutasRestConstants;
import co.com.orbitta.cibercolegios.dto.ConversacionDto;
import co.com.orbitta.cibercolegios.rutas.client.components.RutasRestProperties;
import co.com.orbitta.cibercolegios.rutas.client.service.api.ConversacionLocalService;
import co.com.orbitta.core.web.client.service.impl.LocalServiceImpl;

@Service
public class ConversacionRutaLocalServiceImpl extends LocalServiceImpl<ConversacionDto, Integer>
		implements ConversacionLocalService {

	@Autowired
	private RutasRestProperties properties;

	protected RutasRestProperties getProperties() {
		return properties;
	}

	@Override
	protected String getResourceName() {
		return RutasRestConstants.conversacion;
	}

	@Override
	protected Class<ConversacionDto> getResponseType() {
		return ConversacionDto.class;
	}

	@Override
	protected Class<ConversacionDto[]> getArrayReponseType() {
		return ConversacionDto[].class;
	}
}