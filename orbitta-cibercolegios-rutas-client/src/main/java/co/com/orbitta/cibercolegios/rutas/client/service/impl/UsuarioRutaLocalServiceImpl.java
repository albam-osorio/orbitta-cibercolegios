package co.com.orbitta.cibercolegios.rutas.client.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.constants.RutasRestConstants;
import co.com.orbitta.cibercolegios.dto.UsuarioRutaDto;
import co.com.orbitta.cibercolegios.rutas.client.components.RutasRestProperties;
import co.com.orbitta.cibercolegios.rutas.client.service.api.UsuarioRutaLocalService;
import co.com.orbitta.core.web.client.service.impl.LocalServiceImpl;

@Service
public class UsuarioRutaLocalServiceImpl extends LocalServiceImpl<UsuarioRutaDto, Integer>
		implements UsuarioRutaLocalService {

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
	protected Class<UsuarioRutaDto> getResponseType() {
		return UsuarioRutaDto.class;
	}

	@Override
	protected Class<UsuarioRutaDto[]> getArrayReponseType() {
		return UsuarioRutaDto[].class;
	}
}