package co.com.orbitta.cibercolegios.rutas.service.impl.tracking;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.ciber.client.service.api.UsuarioLocalService;
import co.com.orbitta.cibercolegios.rutas.dto.chat.ConversacionDto;
import co.com.orbitta.cibercolegios.rutas.dto.chat.DatosConversacionDto;
import co.com.orbitta.cibercolegios.rutas.dto.chat.DatosMensajeDto;
import co.com.orbitta.cibercolegios.rutas.dto.chat.MensajeDto;
import co.com.orbitta.cibercolegios.rutas.enums.EmisorMensajeEnum;
import co.com.orbitta.cibercolegios.rutas.service.api.PasajeroQueryService;
import co.com.orbitta.cibercolegios.rutas.service.api.RutaQueryService;
import co.com.orbitta.cibercolegios.rutas.service.api.chat.ChatService;
import co.com.orbitta.cibercolegios.rutas.service.api.chat.ConversacionCrudService;
import co.com.orbitta.cibercolegios.rutas.service.api.chat.MensajeCrudService;
import lombok.val;

@Service
public class ChatTrackingServiceImpl implements ChatService {

	@Autowired
	private UsuarioLocalService usuarioService;

	@Autowired
	private PasajeroQueryService pasajeroService;

	@Autowired
	private RutaQueryService rutaService;

	@Autowired
	private ConversacionCrudService conversacionService;

	@Autowired
	private MensajeCrudService mensajeService;

	// -----------------------------------------------------------------------------------------------------------------
	// -- Acudiente
	// -----------------------------------------------------------------------------------------------------------------
	@Override
	public List<DatosConversacionDto> findConversacionesByRutaId(int rutaId) {
		val result = new ArrayList<DatosConversacionDto>();
		val pasajeros = pasajeroService.findAllByRutaId(rutaId);

		for (val pasajero : pasajeros) {
			result.addAll(findConversacionesByEstudianteId(pasajero.getUsuarioId()));
		}

		val comparator = Comparator.comparing(DatosConversacionDto::getEstudianteNombres)
				.thenComparing(Comparator.comparing(DatosConversacionDto::getAcudienteNombres));

		result.stream().sorted(comparator);

		return result;
	}

	@Override
	public List<DatosConversacionDto> findConversacionesByEstudianteId(int estudianteId) {
		val result = new ArrayList<DatosConversacionDto>();
		val conversaciones = conversacionService.findAllByEstudianteId(estudianteId);

		for (val conversacion : conversaciones) {

			val acudiente = usuarioService.findOneById(conversacion.getAcudienteId());
			val estudiante = usuarioService.findOneById(conversacion.getEstudianteId());

			// @formatter:off
			val model = DatosConversacionDto
					.builder()
					.conversacionId(conversacion.getId())
					.acudienteId(acudiente.getId())
					.acudienteNombres(acudiente.getNombre())
					.acudienteApellidos(acudiente.getApellido())
					.estudianteId(estudiante.getId())
					.estudianteNombres(estudiante.getNombre())
					.estudianteApellidos(estudiante.getApellido())
					.build();
			// @formatter:on

			result.add(model);
		}

		return result;
	}

	@Override
	public int iniciarConversacion(int acudienteId, int estudianteId) {
		int result;
		val optional = conversacionService.findByAcudienteIdAndEstudianteId(acudienteId, estudianteId);
		if (optional.isPresent()) {
			result = optional.get().getId();
		} else {
			// @formatter:off
			ConversacionDto model = ConversacionDto
					.builder()
					.acudienteId(acudienteId)
					.estudianteId(estudianteId)
					.build();
			// @formatter:on

			model = conversacionService.create(model);
			result = model.getId();
		}

		return result;
	}

	@Override
	public List<DatosMensajeDto> findMensajes(int conversacionId) {
		val result = new ArrayList<DatosMensajeDto>();
		val mensajes = mensajeService.findAllByConversacionId(conversacionId);

		for (val mensaje : mensajes) {

			val ruta = rutaService.findOneById(mensaje.getRutaId());
			val monitor = usuarioService.findOneById(mensaje.getMonitorId());

			// @formatter:off
			val model = DatosMensajeDto
					.builder()
					.mensajeId(mensaje.getId())
					.conversacionId(mensaje.getId())
					.rutaId(ruta.getId())
					.rutaCodigo(ruta.getCodigo())
					.monitorId(monitor.getId())
					.monitorNombres(monitor.getNombre())
					.monitorApellidos(monitor.getApellido())
					.origen(mensaje.getOrigen())
					.fechaHora(mensaje.getFechaHora())
					.mensaje(mensaje.getMensaje())
					.build();
			// @formatter:on

			result.add(model);
		}

		result.sort((a, b) -> Integer.compare(a.getMensajeId(), b.getMensajeId()));
		
		return result;
	}

	@Override
	public int enviarMensaje(int conversacionId, int rutaId, int monitorId, String mensaje, EmisorMensajeEnum origen) {
		// @formatter:off
		val model = MensajeDto
					.builder()
					.conversacionId(conversacionId)
					.rutaId(rutaId)
					.monitorId(monitorId)
					.origen(origen)
					.fechaHora(LocalDateTime.now())
					.mensaje(mensaje)
					.build();
		// @formatter:on
		val result = mensajeService.create(model);
		return result.getId();
	}
}