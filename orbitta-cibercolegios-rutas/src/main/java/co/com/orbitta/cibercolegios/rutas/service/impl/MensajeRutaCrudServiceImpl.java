package co.com.orbitta.cibercolegios.rutas.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.dto.MensajeRutaDto;
import co.com.orbitta.cibercolegios.rutas.domain.MensajeRuta;
import co.com.orbitta.cibercolegios.rutas.repository.EstadoRutaRepository;
import co.com.orbitta.cibercolegios.rutas.repository.MensajeRutaRepository;
import co.com.orbitta.cibercolegios.rutas.repository.RutaRepository;
import co.com.orbitta.cibercolegios.rutas.repository.UsuarioRepository;
import co.com.orbitta.cibercolegios.rutas.service.api.MensajeRutaCrudService;
import co.com.orbitta.core.services.crud.impl.CrudServiceImpl;
import lombok.val;

@Service
public class MensajeRutaCrudServiceImpl extends CrudServiceImpl<MensajeRuta, MensajeRutaDto, Integer>
		implements MensajeRutaCrudService {

	@Autowired
	private MensajeRutaRepository repository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private RutaRepository rutaRepository;

	@Autowired
	private EstadoRutaRepository estadoRepository;

	@Override
	protected MensajeRutaRepository getRepository() {
		return repository;
	}

	@Override
	public MensajeRutaDto asModel(MensajeRuta entity) {

		// @formatter:off
		val result = MensajeRutaDto
				.builder()
				.id(entity.getId())
				.fecha(entity.getFecha())
				.mensaje(entity.getMensaje())
				.origenMensaje(entity.getOrigenMensaje())
				.padreId(entity.getPadre().getId())
				.monitorId(entity.getMonitor().getId())
				.rutaId(entity.getRuta().getId())
				.estadoId(entity.getEstado().getId())

				.build();
		// @formatter:on
		return result;
	}

	@Override
	protected MensajeRuta asEntity(MensajeRutaDto model, MensajeRuta entity) {
		val padre = usuarioRepository.findById(model.getPadreId());
		val monitor = usuarioRepository.findById(model.getMonitorId());
		val ruta = rutaRepository.findById(model.getRutaId());
		val estado = estadoRepository.findById(model.getEstadoId());

		entity.setFecha(entity.getFecha());
		entity.setMensaje(entity.getMensaje());
		entity.setOrigenMensaje(entity.getOrigenMensaje());
		entity.setPadre(padre.get());
		entity.setMonitor(monitor.get());
		entity.setRuta(ruta.get());
		entity.setEstado(estado.get());

		return entity;
	}

	@Override
	protected MensajeRuta newEntity() {
		return new MensajeRuta();
	}
}