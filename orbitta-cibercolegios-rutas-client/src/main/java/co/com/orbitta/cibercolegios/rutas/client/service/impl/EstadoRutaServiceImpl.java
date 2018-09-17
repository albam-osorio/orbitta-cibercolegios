package co.com.orbitta.cibercolegios.rutas.client.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.rutas.client.components.RutasRestProperties;
import co.com.orbitta.cibercolegios.rutas.client.service.api.EstadoRutaLocalService;
import co.com.orbitta.cibercolegios.rutas.constants.RutasRestConstants;
import co.com.orbitta.cibercolegios.rutas.dto.EstadoRutaDto;
import co.com.orbitta.core.web.client.service.impl.LocalCrudServiceImpl;

@Service
public class EstadoRutaServiceImpl extends LocalCrudServiceImpl<EstadoRutaDto, Integer>
		implements EstadoRutaLocalService {

	@Autowired
	private RutasRestProperties properties;

	protected RutasRestProperties getProperties() {
		return properties;
	}

	@Override
	protected String getResourceName() {
		return RutasRestConstants.estadoRuta;
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