package co.com.orbitta.cibercolegios.rutas.client.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.constants.RutasRestConstants;
import co.com.orbitta.cibercolegios.dto.EstadoUsuarioRutaDto;
import co.com.orbitta.cibercolegios.rutas.client.components.RutasRestProperties;
import co.com.orbitta.cibercolegios.rutas.client.service.api.LogLocalService;
import co.com.orbitta.core.web.client.service.impl.LocalServiceImpl;

@Service
public class LogLocalServiceImpl extends LocalServiceImpl<EstadoUsuarioRutaDto, Integer>
		implements LogLocalService {

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
	protected Class<EstadoUsuarioRutaDto> getResponseType() {
		return EstadoUsuarioRutaDto.class;
	}

	@Override
	protected Class<EstadoUsuarioRutaDto[]> getArrayReponseType() {
		return EstadoUsuarioRutaDto[].class;
	}
}