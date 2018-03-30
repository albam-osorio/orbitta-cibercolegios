package co.com.orbitta.cibercolegios.rutas.client.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.constants.RestConstants;
import co.com.orbitta.cibercolegios.dto.TipoPerfilDto;
import co.com.orbitta.cibercolegios.rutas.client.components.RutasRestProperties;
import co.com.orbitta.cibercolegios.rutas.client.service.api.TipoPerfilLocalService;
import co.com.orbitta.core.web.client.service.impl.LocalServiceImpl;

@Service
public class TipoPerfilLocalServiceImpl extends LocalServiceImpl<TipoPerfilDto, Integer>
		implements TipoPerfilLocalService {

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
	protected Class<TipoPerfilDto> getResponseType() {
		return TipoPerfilDto.class;
	}

	@Override
	protected Class<TipoPerfilDto[]> getArrayReponseType() {
		return TipoPerfilDto[].class;
	}
}