package co.com.orbitta.cibercolegios.rutas.client.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.constants.RestConstants;
import co.com.orbitta.cibercolegios.dto.UbicacionRutaDto;
import co.com.orbitta.cibercolegios.rutas.client.components.RutasRestProperties;
import co.com.orbitta.cibercolegios.rutas.client.service.api.UbicacionRutaLocalService;
import co.com.orbitta.core.web.client.service.impl.LocalServiceImpl;

@Service
public class UbicacionRutaLocalServiceImpl extends LocalServiceImpl<UbicacionRutaDto, Integer>
		implements UbicacionRutaLocalService {

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
	protected Class<UbicacionRutaDto> getResponseType() {
		return UbicacionRutaDto.class;
	}

	@Override
	protected Class<UbicacionRutaDto[]> getArrayReponseType() {
		return UbicacionRutaDto[].class;
	}
}