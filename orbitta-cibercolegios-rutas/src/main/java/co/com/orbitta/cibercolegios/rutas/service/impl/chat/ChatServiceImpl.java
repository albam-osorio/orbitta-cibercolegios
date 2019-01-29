package co.com.orbitta.cibercolegios.rutas.service.impl.chat;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.rutas.dto.AcudienteDto;
import co.com.orbitta.cibercolegios.rutas.dto.ConversacionDto;
import co.com.orbitta.cibercolegios.rutas.dto.MensajeDto;
import co.com.orbitta.cibercolegios.rutas.enums.EmisorMensajeEnum;
import co.com.orbitta.cibercolegios.rutas.repository.AcudienteRepository;
import co.com.orbitta.cibercolegios.rutas.service.api.AcudienteService;
import co.com.orbitta.cibercolegios.rutas.service.api.ConversacionService;
import co.com.orbitta.cibercolegios.rutas.service.api.MensajeService;
import co.com.orbitta.cibercolegios.rutas.service.api.PasajeroService;
import co.com.orbitta.cibercolegios.rutas.service.api.chat.ChatService;
import co.com.orbitta.cibercolegios.rutas.service.api.readonly.CiberService;
import lombok.val;

@Service
public class ChatServiceImpl implements ChatService {

	@Autowired
	private CiberService ciberService;

	@Autowired
	private AcudienteRepository acudienteRepository;

	@Autowired
	private AcudienteService acudienteService;

	@Autowired
	private PasajeroService pasajeroService;

	@Autowired
	private ConversacionService conversacionService;

	@Autowired
	private MensajeService mensajeService;

	@Override
	public void registrarAcudiente(int usuarioId, String token) {
		CheckUsuarioExistente(usuarioId);

		val model = new AcudienteDto();
		model.setUsuarioId(usuarioId);
		model.setToken(token);

		val acudiente = acudienteRepository.findByUsuarioId(usuarioId);
		if (!acudiente.isPresent()) {
			acudienteService.create(model);
		} else {
			model.setId(acudiente.get().getId());
			model.setVersion(acudiente.get().getVersion());
			acudienteService.update(model);
		}
	}

	@Override
	public int iniciarConversacion(int rutaId, int acudienteId, int pasajeroId) {
		int result;
		val optional = conversacionService.findByRutaIdAndUsuarioAcudienteIdAndUsuarioPasajeroId(rutaId, acudienteId,
				pasajeroId);

		if (optional.isPresent()) {
			result = optional.get().getConversacionId();
		} else {
			CheckAcudienteIniciarConversacion(acudienteId);
			CheckPasajeroIniciarConversacion(rutaId, acudienteId, pasajeroId);

			ConversacionDto model = new ConversacionDto();

			model.setRutaId(rutaId);
			model.setUsuarioAcudienteId(acudienteId);
			model.setUsuarioPasajeroId(pasajeroId);

			model = conversacionService.create(model);
			result = model.getId();
		}

		return result;
	}

	@Override
	public int enviarMensaje(int conversacionId, EmisorMensajeEnum origen, String mensaje) {
		CheckConversacionEnviarMensaje(conversacionId);

		val conversacion = conversacionService.findOneById(conversacionId);

		val model = new MensajeDto();

		model.setConversacionId(conversacion.getId());
		model.setMonitorId(conversacion.getUsuarioMonitorId());
		model.setOrigen(origen);
		model.setMensaje(StringUtils.left(mensaje, 200));

		val result = mensajeService.create(model);
		return result.getId();
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------------------------
	// ---
	// ---------------------------------------------------------------------------------------------------------------------------------------------------------
	private void CheckUsuarioExistente(int usuarioId) {
		val optional = ciberService.findUsuarioById(usuarioId);
		if (optional.isPresent()) {
		} else {
			val format = "El usuario con id=%d no existe.";
			val msg = String.format(format, usuarioId);
			throw new RuntimeException(msg);
		}
	}

	private void CheckAcudienteIniciarConversacion(int acudienteId) {
		CheckUsuarioExistente(acudienteId);

		val optional = acudienteRepository.findByUsuarioId(acudienteId);
		if (!optional.isPresent()) {
			val acudiente = optional.get();
			if (acudiente.getToken() == null) {
				val format = "El acudiente con usuario id=%d existe, pero no ha registrado un token. Antes de iniciar una conversación debe registrar su token";
				val msg = String.format(format, acudienteId);
				throw new RuntimeException(msg);
			}
		}
	}

	private void CheckPasajeroIniciarConversacion(int rutaId, int usuarioAcudienteId, int usuarioPasajeroId) {
		String msg = null;
		boolean error = false;
		val optional = pasajeroService.findByUsuarioId(usuarioPasajeroId);

		if (optional.isPresent()) {
			val pasajero = optional.get();
			if (pasajero.getRutaId() != rutaId) {
				val format = "El pasajero con usuario id=%d no esta asociado a la ruta con id=%d.";
				msg = String.format(format, usuarioPasajeroId, rutaId);
				error = true;
			} else {
				val acudientes = acudienteRepository.findAllById(pasajero.getAcudientes());
				val o = acudientes.stream().filter(a -> a.getUsuarioId() == usuarioAcudienteId).findFirst();
				if (!o.isPresent()) {
					val format = "El pasajero con usuario id=%d no esta asociado al acudiente con id=%d.";
					msg = String.format(format, usuarioPasajeroId, usuarioAcudienteId);
					error = true;
				}
			}
		} else {
			val format = "El pasajero con usuario id=%d no existe.";
			msg = String.format(format, usuarioPasajeroId);
			error = true;
		}

		if (error) {
			throw new RuntimeException(msg);
		}
	}

	private void CheckConversacionEnviarMensaje(int conversacionId) {
		val optional = conversacionService.findById(conversacionId);

		if (!optional.isPresent()) {
			val format = "La conversación con id=%d no existe.";
			val msg = String.format(format, conversacionId);
			throw new RuntimeException(msg);
		}
	}
}