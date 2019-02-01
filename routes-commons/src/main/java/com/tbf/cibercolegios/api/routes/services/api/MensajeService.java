package com.tbf.cibercolegios.api.routes.services.api;

import java.util.List;

import com.tbf.cibercolegios.api.core.services.api.CrudService;
import com.tbf.cibercolegios.api.routes.model.graph.MensajeDto;
import com.tbf.cibercolegios.api.routes.model.graph.chat.DatosMensajeDto;

public interface MensajeService extends CrudService<MensajeDto, Integer> {

	List<DatosMensajeDto> findAllByConversacionId(int conversacionId);
}
