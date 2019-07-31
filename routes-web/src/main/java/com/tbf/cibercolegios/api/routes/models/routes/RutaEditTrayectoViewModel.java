package com.tbf.cibercolegios.api.routes.models.routes;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.tbf.cibercolegios.api.ciber.model.graph.CiudadDto;
import com.tbf.cibercolegios.api.ciber.model.graph.DepartamentoDto;
import com.tbf.cibercolegios.api.ciber.model.graph.UsuarioPasajeroDto;
import com.tbf.cibercolegios.api.model.routes.PasajeroDireccion;
import com.tbf.cibercolegios.api.model.routes.enums.CourseType;
import com.tbf.cibercolegios.api.routes.model.graph.DireccionDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.val;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class RutaEditTrayectoViewModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private UsuarioPasajeroDto pasajero;

	private int correlacion;

	private RutaEditDireccionViewModel direccionAm;

	private RutaEditDireccionViewModel direccionPm;

	public String getId() {
		return "" + getPasajero().getPasajeroId() + "-" + getCorrelacion();
	}

	public boolean isActivo() {
		return isActivo(getDireccionAm()) || isActivo(getDireccionPm());
	}

	protected boolean isActivo(RutaEditDireccionViewModel direccion) {
		boolean result = false;
		if (direccion != null) {
			result = direccion.isActivo();
		}
		return result;
	}

	public Integer getSecuenciaAm() {
		return getSecuencia(getDireccionAm());
	}

	public Integer getSecuenciaPm() {
		return getSecuencia(getDireccionPm());
	}

	protected Integer getSecuencia(RutaEditDireccionViewModel direccion) {
		Integer result = null;
		if (direccion != null) {
			result = direccion.getSecuencia();
		}
		return result;
	}

	public String getOrdenAm() {
		return getOrden(getDireccionAm());
	}

	public String getOrdenPm() {
		return getOrden(getDireccionPm());
	}

	protected String getOrden(RutaEditDireccionViewModel direccion) {
		val secuencia = getSecuencia(direccion);
		String result;
		if (secuencia != null) {
			result = String.valueOf(secuencia);
		} else {
			result = (isActivo() ? "A" : "Z") + getPasajero().getNombreCompleto();
		}
		return result;
	}

	public boolean isTrayectoCompleto() {
		return (isTieneDireccionAm() && isTieneDireccionPm());
	}

	public boolean isDireccionesEquivalentes() {
		boolean result = true;
		if (isTrayectoCompleto()) {
			result = false;
			if (getDireccionAm().getDepartamentoId().equals(getDireccionPm().getDepartamentoId())) {
				if (getDireccionAm().getCiudadId().equals(getDireccionPm().getCiudadId())) {
					if (getDireccionAm().getDireccion().equals(getDireccionPm().getDireccion())) {
						result = true;
					}
				}
			}
		}
		return result;
	}

	public boolean isTieneDireccionAm() {
		return this.getDireccionAm() != null;
	}

	public boolean isTieneDireccionPm() {
		return this.getDireccionPm() != null;
	}

	public static RutaEditTrayectoViewModel asTrayecto(Integer pasajeroId, List<UsuarioPasajeroDto> pasajeros,
			List<PasajeroDireccion> pasajerosDirecciones, List<DireccionDto> direcciones,
			Map<DepartamentoDto, List<CiudadDto>> ciudades) {
		val pasajero = pasajeros.stream().filter(a -> a.getPasajeroId().equals(pasajeroId)).findFirst().get();

		val model = new RutaEditTrayectoViewModel();
		model.setPasajero(pasajero);

		val optional = pasajerosDirecciones.stream().findFirst();
		model.setCorrelacion(optional.get().getCorrelacion());

		model.setDireccionAm(RutaEditDireccionViewModel.asDireccionViewModel(pasajerosDirecciones,
				CourseType.SENTIDO_IDA, direcciones, ciudades));
		model.setDireccionPm(RutaEditDireccionViewModel.asDireccionViewModel(pasajerosDirecciones,
				CourseType.SENTIDO_RETORNO, direcciones, ciudades));

		return model;
	}
}