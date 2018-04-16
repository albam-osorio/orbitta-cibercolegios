package co.com.orbitta.cibercolegios.rutas.client.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.constants.RutasRestConstants;
import co.com.orbitta.cibercolegios.dto.UsuarioDto;
import co.com.orbitta.cibercolegios.rutas.client.components.RutasRestProperties;
import co.com.orbitta.cibercolegios.rutas.client.service.api.UsuarioLocalService;
import co.com.orbitta.core.web.client.service.impl.LocalServiceImpl;

@Service
public class UsuarioLocalServiceImpl extends LocalServiceImpl<UsuarioDto, Integer>
		implements UsuarioLocalService {

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
	protected Class<UsuarioDto> getResponseType() {
		return UsuarioDto.class;
	}

	@Override
	protected Class<UsuarioDto[]> getArrayReponseType() {
		return UsuarioDto[].class;
	}
}