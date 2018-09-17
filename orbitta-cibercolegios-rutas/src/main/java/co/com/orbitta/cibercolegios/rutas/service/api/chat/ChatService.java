package co.com.orbitta.cibercolegios.rutas.service.api.chat;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import co.com.orbitta.cibercolegios.rutas.dto.chat.DatosConversacionDto;
import co.com.orbitta.cibercolegios.rutas.dto.chat.DatosMensajeDto;
import co.com.orbitta.cibercolegios.rutas.enums.EmisorMensajeEnum;

@Transactional(readOnly = true)
public interface ChatService {

	List<DatosConversacionDto> findConversacionesByRutaId(int rutaId);

	List<DatosConversacionDto> findConversacionesByEstudianteId(int estudianteId);

	@Transactional(readOnly = false)
	int iniciarConversacion(int acudienteId, int estudianteId);

	List<DatosMensajeDto> findMensajes(int conversacionId);

	@Transactional(readOnly = false)
	int enviarMensaje(int conversacionId, int rutaId, int monitorId, String mensaje, EmisorMensajeEnum origen);
}
