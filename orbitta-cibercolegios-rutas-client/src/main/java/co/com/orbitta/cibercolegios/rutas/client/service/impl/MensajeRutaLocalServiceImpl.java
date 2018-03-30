package co.com.orbitta.cibercolegios.rutas.client.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.constants.RestConstants;
import co.com.orbitta.cibercolegios.dto.MensajeRutaDto;
import co.com.orbitta.cibercolegios.rutas.client.components.RutasRestProperties;
import co.com.orbitta.cibercolegios.rutas.client.service.api.MensajeRutaLocalService;
import co.com.orbitta.core.web.client.service.impl.LocalServiceImpl;

@Service
public class MensajeRutaLocalServiceImpl extends LocalServiceImpl<MensajeRutaDto, Integer>
		implements MensajeRutaLocalService {

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
	protected Class<MensajeRutaDto> getResponseType() {
		return MensajeRutaDto.class;
	}

	@Override
	protected Class<MensajeRutaDto[]> getArrayReponseType() {
		return MensajeRutaDto[].class;
	}
}