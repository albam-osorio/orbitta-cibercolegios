package co.com.orbitta.cibercolegios.rutas.client.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.constants.RutasRestConstants;
import co.com.orbitta.cibercolegios.dto.EstadoRutaDto;
import co.com.orbitta.cibercolegios.rutas.client.components.RutasRestProperties;
import co.com.orbitta.cibercolegios.rutas.client.service.api.EstadoLocalService;
import co.com.orbitta.core.web.client.service.impl.LocalServiceImpl;

@Service
public class EstadoLocalServiceImpl extends LocalServiceImpl<EstadoRutaDto, Integer>
		implements EstadoLocalService {

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
	protected Class<EstadoRutaDto> getResponseType() {
		return EstadoRutaDto.class;
	}

	@Override
	protected Class<EstadoRutaDto[]> getArrayReponseType() {
		return EstadoRutaDto[].class;
	}
}