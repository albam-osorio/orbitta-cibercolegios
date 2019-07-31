package com.tbf.cibercolegios.api.routes.services.impl.chat;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tbf.cibercolegios.api.ciber.services.api.CiberService;
import com.tbf.cibercolegios.api.model.routes.enums.IssuingMessage;
import com.tbf.cibercolegios.api.routes.model.graph.AcudienteDto;
import com.tbf.cibercolegios.api.routes.model.graph.ConversacionDto;
import com.tbf.cibercolegios.api.routes.model.graph.MensajeDto;
import com.tbf.cibercolegios.api.routes.repository.AcudienteRepository;
import com.tbf.cibercolegios.api.routes.services.api.ConversacionService;
import com.tbf.cibercolegios.api.routes.services.api.MensajeService;
import com.tbf.cibercolegios.api.routes.services.api.PasajeroService;
import com.tbf.cibercolegios.api.routes.services.api.chat.ChatService;
import com.tbf.cibercolegios.api.routes.services.impl.AcudienteCrudServiceImpl;

import lombok.val;

@Service
public class ChatServiceImpl implements ChatService {

	@Autowired
	private CiberService ciberService;

	@Autowired
	private AcudienteRepository acudienteRepository;

	@Autowired
	private AcudienteCrudServiceImpl acudienteService;

	@Autowired
	private PasajeroService pasajeroService;

	@Autowired
	private ConversacionService conversacionService;

	@Autowired
	private MensajeService mensajeService;

	@Override
	public void registrarAcudiente(int usuarioId, String token) {
		checkUsuarioAcudienteExistente(usuarioId);

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
	public int enviarMensaje(int conversacionId, IssuingMessage origen, String mensaje) {
		CheckConversacionEnviarMensaje(conversacionId);

		val conversacion = conversacionService.findOneById(conversacionId);

		val model = new MensajeDto();

		model.setConversacionId(conversacion.getId());
		// model.setMonitorId(conversacion.getUsuarioMonitorId());
		model.setOrigen(origen);
		model.setMensaje(StringUtils.left(mensaje, 200));

		val result = mensajeService.create(model);
		return result.getId();
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------------------------
	// ---
	// ---------------------------------------------------------------------------------------------------------------------------------------------------------
	private void checkUsuarioAcudienteExistente(int usuarioId) {
		val optional = ciberService.findUsuarioAcudienteByUsuarioId(usuarioId);
		if (optional.isPresent()) {
		} else {
			val format = "El usuario con id=%d no existe.";
			val msg = String.format(format, usuarioId);
			throw new RuntimeException(msg);
		}
	}

	private void CheckAcudienteIniciarConversacion(int usuarioId) {
		checkUsuarioAcudienteExistente(usuarioId);

		val optional = acudienteRepository.findByUsuarioId(usuarioId);
		if (!optional.isPresent()) {
			val acudiente = optional.get();
			if (acudiente.getToken() == null) {
				val format = "El acudiente con usuario id=%d existe, pero no ha registrado un token. Antes de iniciar una conversación debe registrar su token";
				val msg = String.format(format, usuarioId);
				throw new RuntimeException(msg);
			}
		}
	}

	private void CheckPasajeroIniciarConversacion(int rutaId, int usuarioAcudienteId, int usuarioPasajeroId) {
		// String msg = null;
		// boolean error = false;
		// val optional = pasajeroService.findByUsuarioId(usuarioPasajeroId);
		//
		// if (optional.isPresent()) {
		// val pasajero = optional.get();
		// if (pasajero.getRutaId() != rutaId) {
		// val format = "El pasajero con usuario id=%d no esta asociado a la ruta con
		// id=%d.";
		// msg = String.format(format, usuarioPasajeroId, rutaId);
		// error = true;
		// } else {
		// val acudientes = acudienteRepository.findAllById(pasajero.getAcudientes());
		// val o = acudientes.stream().filter(a -> a.getUsuarioId() ==
		// usuarioAcudienteId).findFirst();
		// if (!o.isPresent()) {
		// val format = "El pasajero con usuario id=%d no esta asociado al acudiente con
		// id=%d.";
		// msg = String.format(format, usuarioPasajeroId, usuarioAcudienteId);
		// error = true;
		// }
		// }
		// } else {
		// val format = "El pasajero con usuario id=%d no existe.";
		// msg = String.format(format, usuarioPasajeroId);
		// error = true;
		// }
		//
		// if (error) {
		// throw new RuntimeException(msg);
		// }
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