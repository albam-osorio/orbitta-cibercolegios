package co.com.orbitta.cibercolegios.ciber.client.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.ciber.client.components.CiberRestProperties;
import co.com.orbitta.cibercolegios.ciber.client.service.api.InstitucionLocalService;
import co.com.orbitta.cibercolegios.ciber.constants.CiberRestConstants;
import co.com.orbitta.cibercolegios.ciber.dto.InstitucionDto;
import co.com.orbitta.core.web.client.service.impl.LocalQueryServiceImpl;

@Service
public class InstitucionLocalServiceImpl extends LocalQueryServiceImpl<InstitucionDto, Integer>
		implements InstitucionLocalService {

	@Autowired
	private CiberRestProperties properties;

	protected CiberRestProperties getProperties() {
		return properties;
	}

	@Override
	protected String getResourceName() {
		return CiberRestConstants.instituciones;
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