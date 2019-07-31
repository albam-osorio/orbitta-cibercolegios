package com.tbf.cibercolegios.api.routes.services.impl.tracking;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tbf.cibercolegios.api.model.routes.Direccion;
import com.tbf.cibercolegios.api.routes.model.graph.RutaDto;
import com.tbf.cibercolegios.api.routes.model.graph.tracking.DatosEstadoParadaDto;
import com.tbf.cibercolegios.api.routes.model.graph.tracking.DatosParadaDto;
import com.tbf.cibercolegios.api.routes.repository.PasajeroRepository;
import com.tbf.cibercolegios.api.routes.services.api.RutaService;
import com.tbf.cibercolegios.api.routes.services.api.tracking.AcudienteTrackingService;

import lombok.val;

@Service
public class AcudienteTrackingServiceImpl implements AcudienteTrackingService {

	@Autowired
	private PasajeroRepository pasajeroRepository;

	@Autowired
	private RutaService rutaService;

	// -----------------------------------------------------------------------------------------------------------------
	// -- Acudiente
	// -----------------------------------------------------------------------------------------------------------------
	@Override
	public Optional<DatosParadaDto> findParadaByUsuarioId(int usuarioId) {
//		val optional = pasajeroRepository.findByUsuarioIdAndRutaId(usuarioId);
//
//		if (optional.isPresent()) {
//			val p = optional.get();
//			val ruta = rutaService.findByIdAsMonitorDatosRuta(p.getRuta().getId()).get();
//			val pasajero = ruta.getPasajeros().stream().filter(a -> a.getUsuarioId().equals(p.getUsuarioId()))
//					.findFirst().get();
//
//			val result = new DatosParadaDto();
//
//			result.setRutaId(ruta.getRutaId());
//
//			result.setInstitucionId(ruta.getInstitucionId());
//			result.setInstitucionNombre(ruta.getInstitucionNombre());
//			result.setInstitucionX(ruta.getInstitucionX());
//			result.setInstitucionY(ruta.getInstitucionY());
//
//			result.setCodigo(ruta.getCodigo());
//			result.setDescripcion(ruta.getDescripcion());
//			result.setMarca(ruta.getMarca());
//			result.setPlaca(ruta.getPlaca());
//			result.setCapacidad(ruta.getCapacidad());
//
//			result.setMovil(ruta.getMovil());
//			result.setToken(ruta.getToken());
//
//			result.setConductorId(ruta.getConductorId());
//			result.setConductorNombres(ruta.getConductorNombres());
//			result.setConductorApellidos(ruta.getConductorApellidos());
//
//			result.setMonitorId(ruta.getMonitorId());
//			result.setMonitorNombres(ruta.getMonitorNombres());
//			result.setMonitorApellidos(ruta.getMonitorApellidos());
//
//			result.setActiva(ruta.isActiva());
//
//			result.setEstadoRutaId(ruta.getEstadoId());
//			result.setEstadoRutaNombre(ruta.getEstadoNombre());
//			result.setTipoEstadoRuta(ruta.getTipoEstado());
//			result.setRutaX(ruta.getX());
//			result.setRutaY(ruta.getY());
//
//			result.setTotalParadas(ruta.getTotalParadas());
//			result.setParadaActual(ruta.getParadaActual());
//
//			result.setEstudianteId(pasajero.getUsuarioId());
//			result.setEstudianteNombres(pasajero.getNombres());
//			result.setEstudianteApellidos(pasajero.getApellidos());
//			result.setSecuencia(pasajero.getSecuencia());
//			result.setEstadoEstudianteId(pasajero.getEstadoId());
//			result.setEstadoEstudianteNombre(pasajero.getEstadoDescripcion());
//			result.setTipoEstadoEstudiante(pasajero.getTipoEstado());
//
//			result.setCiudadNombre(pasajero.getCiudadNombre());
//			result.setDireccion(pasajero.getDireccion());
//			result.setParadaX(pasajero.getX());
//			result.setParadaY(pasajero.getY());
//
//			return Optional.of(result);
//		} else {
//			return Optional.empty();
//		}
		return Optional.empty();
	}

	@Override
	public Optional<DatosEstadoParadaDto> findEstadoParadaByUsuarioId(int usuarioId) {
//		val optional = pasajeroRepository.findByUsuarioIdAndRutaId(usuarioId);
//
//		if (optional.isPresent()) {
//			val pasajero = optional.get();
//			val ruta = pasajero.getRuta();
//			Direccion direccion = null;
//			BigDecimal paradaX = null;
//			BigDecimal paradaY = null;
//
//			switch (ruta.getSentido()) {
//			case RutaDto.SENTIDO_IDA:
//				direccion = pasajero.getDireccionIda();
//				break;
//			case RutaDto.SENTIDO_RETORNO:
//				direccion = pasajero.getDireccionRetorno();
//				break;
//			default:
//				break;
//			}
//			if (direccion != null) {
//				paradaX = direccion.getX();
//				paradaY = direccion.getY();
//			}
//
//			val result = new DatosEstadoParadaDto();
//
//			result.setEstadoEstudianteId(pasajero.getEstado().getId());
//			result.setEstadoEstudianteNombre(pasajero.getEstado().getDescripcion());
//			result.setTipoEstadoEstudiante(pasajero.getEstado().getTipo());
//			result.setParadaX(paradaX);
//			result.setParadaY(paradaY);
//
//			result.setEstadoRutaId(ruta.getEstado().getId());
//			result.setEstadoRutaNombre(ruta.getEstado().getDescripcion());
//			result.setTipoEstadoRuta(ruta.getEstado().getTipo());
//			result.setRutaX(ruta.getX());
//			result.setRutaY(ruta.getY());
//
//			result.setInstitucionX(ruta.getDireccionSede().getX());
//			result.setInstitucionY(ruta.getDireccionSede().getY());
//			
//			return Optional.of(result);
//		} else {
//			return Optional.empty();
//
//		}
		return Optional.empty();
	}
}