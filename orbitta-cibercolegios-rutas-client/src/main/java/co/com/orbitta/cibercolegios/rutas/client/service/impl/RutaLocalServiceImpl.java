package co.com.orbitta.cibercolegios.rutas.client.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.constants.RutasRestConstants;
import co.com.orbitta.cibercolegios.dto.RutaDto;
import co.com.orbitta.cibercolegios.rutas.client.components.RutasRestProperties;
import co.com.orbitta.cibercolegios.rutas.client.service.api.RutaLocalService;
import co.com.orbitta.core.web.client.service.impl.LocalServiceImpl;

@Service
public class RutaLocalServiceImpl extends LocalServiceImpl<RutaDto, Integer>
		implements RutaLocalService {

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
	protected Class<RutaDto> getResponseType() {
		return RutaDto.class;
	}

	@Override
	protected Class<RutaDto[]> getArrayReponseType() {
		return RutaDto[].class;
	}
}