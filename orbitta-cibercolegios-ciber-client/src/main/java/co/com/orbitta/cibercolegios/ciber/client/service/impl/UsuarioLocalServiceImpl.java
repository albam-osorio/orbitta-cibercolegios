package co.com.orbitta.cibercolegios.ciber.client.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.ciber.client.components.CiberRestProperties;
import co.com.orbitta.cibercolegios.ciber.client.service.api.UsuarioLocalService;
import co.com.orbitta.cibercolegios.ciber.constants.CiberRestConstants;
import co.com.orbitta.cibercolegios.ciber.dto.UsuarioDto;
import co.com.orbitta.core.web.client.service.impl.LocalQueryServiceImpl;

@Service
public class UsuarioLocalServiceImpl extends LocalQueryServiceImpl<UsuarioDto, Integer>
		implements UsuarioLocalService {

	@Autowired
	private CiberRestProperties properties;

	protected CiberRestProperties getProperties() {
		return properties;
	}

	@Override
	protected String getResourceName() {
		return CiberRestConstants.usuarios;
	}

	@Override
	protected Class<UsuarioDto> getResponseType() {
		return UsuarioDto.class;
	}

	@Override
	protected Class<UsuarioDto[]> getArrayReponseType() {
		return UsuarioDto[].class;
	}
}