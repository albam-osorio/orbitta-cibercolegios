package com.tbf.cibercolegios.api.routes.services.api;

import java.util.List;

import com.tbf.cibercolegios.api.routes.model.graph.MensajeDto;
import com.tbf.cibercolegios.api.routes.model.graph.chat.DatosMensajeDto;

import co.com.orbitta.core.services.crud.api.CrudService;

public interface MensajeService extends CrudService<MensajeDto, Integer> {

	List<DatosMensajeDto> findAllByConversacionId(int conversacionId);
}
