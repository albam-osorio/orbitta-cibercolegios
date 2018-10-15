package co.com.orbitta.cibercolegios.rutas.service.api.chat;

import java.util.List;

import co.com.orbitta.cibercolegios.rutas.dto.chat.DatosMensajeDto;
import co.com.orbitta.cibercolegios.rutas.dto.chat.MensajeDto;
import co.com.orbitta.core.services.crud.api.CrudService;

public interface MensajeService extends CrudService<MensajeDto, Integer> {

	List<DatosMensajeDto> findAllByConversacionId(int conversacionId);
}
