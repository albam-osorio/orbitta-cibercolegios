package com.tbf.cibercolegios.api.routes.models.routes;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.tbf.cibercolegios.api.ciber.model.graph.CiudadDto;
import com.tbf.cibercolegios.api.ciber.model.graph.DepartamentoDto;
import com.tbf.cibercolegios.api.model.routes.PasajeroDireccion;
import com.tbf.cibercolegios.api.model.routes.enums.CourseType;
import com.tbf.cibercolegios.api.routes.model.graph.DireccionDto;

import lombok.Data;
import lombok.val;

@Data
public class RutaEditDireccionViewModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private int version;

	private CourseType sentido;

	private Integer direccionId;

	private Integer departamentoId;

	private String departamentoNombre;

	private Integer ciudadId;

	private String ciudadNombre;

	private String direccion;

	private boolean activo;

	private Integer secuencia;

	public String getDireccionCompleta() {
		return getDireccion() + ", " + getCiudadNombre();
	}

	public static RutaEditDireccionViewModel asDireccionViewModel(List<PasajeroDireccion> pasajerosDirecciones,
			CourseType sentido, List<DireccionDto> direcciones, Map<DepartamentoDto, List<CiudadDto>> ciudades) {

		val optional = pasajerosDirecciones.stream().filter(a -> a.getSentido() == sentido.getIntValue()).findFirst();

		if (optional.isPresent()) {
			return asDireccionViewModel(optional.get(), direcciones, ciudades);
		}

		return null;
	}

	public static RutaEditDireccionViewModel asDireccionViewModel(PasajeroDireccion pasajeroDireccion,
			List<DireccionDto> direcciones, Map<DepartamentoDto, List<CiudadDto>> ciudades) {
		val direccion = direcciones.stream().filter(a -> a.getId().equals(pasajeroDireccion.getDireccionId()))
				.findFirst().get();

		val entry = ciudades.entrySet().stream().filter(a -> a.getKey().getPaisId() == direccion.getPaisId()
				&& a.getKey().getDepartamentoId() == direccion.getDepartamentoId()).findFirst().get();
		val departamento = entry.getKey();
		val ciudad = entry.getValue().stream().filter(a -> a.getCiudadId() == direccion.getCiudadId()).findFirst()
				.get();

		val result = new RutaEditDireccionViewModel();
		result.setId(pasajeroDireccion.getId());
		result.setVersion(pasajeroDireccion.getVersion());

		result.setSentido(CourseType.asEnum(pasajeroDireccion.getSentido()));
		result.setDireccionId(pasajeroDireccion.getDireccionId());
		result.setDepartamentoId(departamento.getDepartamentoId());
		result.setDepartamentoNombre(departamento.getNombre());
		result.setCiudadId(ciudad.getDepartamentoId());
		result.setCiudadNombre(ciudad.getNombre());
		result.setDireccion(direccion.getDireccion());

		result.setActivo(pasajeroDireccion.isActivo());
		result.setSecuencia(pasajeroDireccion.getSecuencia());

		return result;
	}
}
