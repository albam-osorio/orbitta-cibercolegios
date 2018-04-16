package co.com.orbitta.cibercolegios.rutas.client.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.constants.RutasRestConstants;
import co.com.orbitta.cibercolegios.dto.PerfilDto;
import co.com.orbitta.cibercolegios.rutas.client.components.RutasRestProperties;
import co.com.orbitta.cibercolegios.rutas.client.service.api.PerfilLocalService;
import co.com.orbitta.core.web.client.service.impl.LocalServiceImpl;

@Service
public class PerfilLocalServiceImpl extends LocalServiceImpl<PerfilDto, Integer>
		implements PerfilLocalService {

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
	protected Class<PerfilDto> getResponseType() {
		return PerfilDto.class;
	}

	@Override
	protected Class<PerfilDto[]> getArrayReponseType() {
		return PerfilDto[].class;
	}
}