package co.com.orbitta.cibercolegios.rutas.client.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.rutas.client.components.RutasRestProperties;
import co.com.orbitta.cibercolegios.rutas.client.service.api.ConversacionLocalService;
import co.com.orbitta.cibercolegios.rutas.constants.RutasRestConstants;
import co.com.orbitta.cibercolegios.rutas.dto.chat.ConversacionDto;
import co.com.orbitta.core.web.client.service.impl.LocalCrudServiceImpl;

@Service
public class ConversacionRutaLocalServiceImpl extends LocalCrudServiceImpl<ConversacionDto, Integer>
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