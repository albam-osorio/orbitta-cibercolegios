package co.com.orbitta.cibercolegios.rutas.client.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.constants.RestConstants;
import co.com.orbitta.cibercolegios.dto.InstitucionDto;
import co.com.orbitta.cibercolegios.rutas.client.components.RutasRestProperties;
import co.com.orbitta.cibercolegios.rutas.client.service.api.InstitucionLocalService;
import co.com.orbitta.core.web.client.service.impl.LocalServiceImpl;

@Service
public class InstitucionLocalServiceImpl extends LocalServiceImpl<InstitucionDto, Integer>
		implements InstitucionLocalService {

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
	protected Class<InstitucionDto> getResponseType() {
		return InstitucionDto.class;
	}

	@Override
	protected Class<InstitucionDto[]> getArrayReponseType() {
		return InstitucionDto[].class;
	}
}