package co.com.orbitta.cibercolegios.rutas.client.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.constants.RutasRestConstants;
import co.com.orbitta.cibercolegios.dto.MensajeDto;
import co.com.orbitta.cibercolegios.rutas.client.components.RutasRestProperties;
import co.com.orbitta.cibercolegios.rutas.client.service.api.MensajeLocalService;
import co.com.orbitta.core.web.client.service.impl.LocalServiceImpl;

@Service
public class MensajeLocalServiceImpl extends LocalServiceImpl<MensajeDto, Integer>
		implements MensajeLocalService {

	@Autowired
	private RutasRestProperties properties;

	protected RutasRestProperties getProperties() {
		return properties;
	}

	@Override
	protected String getResourceName() {
		return RutasRestConstants.mensaje;
	}

	@Override
	protected Class<MensajeDto> getResponseType() {
		return MensajeDto.class;
	}

	@Override
	protected Class<MensajeDto[]> getArrayReponseType() {
		return MensajeDto[].class;
	}
}