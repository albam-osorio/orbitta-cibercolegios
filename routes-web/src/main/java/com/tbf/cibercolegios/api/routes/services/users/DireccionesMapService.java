package com.tbf.cibercolegios.api.routes.services.users;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tbf.cibercolegios.api.ciber.model.graph.CiudadDto;
import com.tbf.cibercolegios.api.ciber.model.graph.DepartamentoDto;
import com.tbf.cibercolegios.api.ciber.model.graph.TipoDocumentoDto;
import com.tbf.cibercolegios.api.ciber.model.graph.UsuarioDto;
import com.tbf.cibercolegios.api.ciber.services.api.CiberService;
import com.tbf.cibercolegios.api.routes.models.users.ImportViewModel;
import com.tbf.cibercolegios.api.routes.web.utils.UserProfile;

import lombok.val;

@Service
public class DireccionesMapService {

	// -----------------------------------------------------------------------------------
	// --
	// -----------------------------------------------------------------------------------
	@Autowired
	private UserProfile profile;

	@Autowired
	private CiberService ciberService;

	// -----------------------------------------------------------------------------------
	// --
	// -----------------------------------------------------------------------------------
	public boolean map(List<ImportViewModel> models) {
		val tiposId = ciberService.findAllTiposDocumento();
		tiposId.stream().forEach(a -> a.setDescripcion(stripAccents(a.getDescripcion()).toUpperCase()));

		val institucionId = profile.getInstitucionId();
		val paisId = profile.getPaisId();
		val departamentos = ciberService.findAllDepartamentosByPaisId(paisId);
		departamentos.stream().forEach(a -> a.setNombre(stripAccents(a.getNombre()).toUpperCase()));
		val ciudades = findCiudades(paisId, departamentos, models);

		for (val model : models) {
			model.setTipoIdentificacionId(mapTipoIdentificacionId(model, tiposId));
			model.setUsuario(mapUsuario(model, institucionId));
			model.setInstitucionId(mapInstitucion(model, institucionId));
			model.setDepartamentoAmId(mapDepartamento(model.getDepartamentoAm(), departamentos));
			model.setDepartamentoPmId(mapDepartamento(model.getDepartamentoPm(), departamentos));
			model.setCiudadAmId(mapCiudad(model.getDepartamentoAmId(), model.getCiudadAm(), ciudades));
			model.setCiudadPmId(mapCiudad(model.getDepartamentoPmId(), model.getCiudadPm(), ciudades));
		}

		return check(models);
	}

	private List<CiudadDto> findCiudades(Integer paisId, List<DepartamentoDto> departamentos,
			List<ImportViewModel> models) {
		val codigos = new HashMap<String, List<String>>();
		for (val model : models) {
			asCodigoCiudad(codigos, model.getDepartamentoAm(), model.getCiudadAm());
			asCodigoCiudad(codigos, model.getDepartamentoPm(), model.getCiudadPm());
		}

		val result = new ArrayList<CiudadDto>();
		for (val entry : codigos.entrySet()) {
			val departamento = entry.getKey();
			val list = entry.getValue();
			
			val optional = departamentos.stream().filter(a -> a.getNombre().equalsIgnoreCase(departamento)).findFirst();

			if (optional.isPresent()) {
				val departamentoId = optional.get().getDepartamentoId();
				val ciudades = ciberService.findAllCiudadesByDepartamentoId(paisId, departamentoId);
				ciudades.stream().forEach(a -> a.setNombre(stripAccents(a.getNombre()).toUpperCase()));

				for (val ciudad : list) {
					result.addAll(ciudades.stream().filter(a -> a.getNombre().equalsIgnoreCase(ciudad))
							.collect(toList()));
				}
			}
		}

		return result;
	}

	private void asCodigoCiudad(Map<String, List<String>> codigos, String departamento, String ciudad) {
		departamento = stripAccents(departamento).replaceAll("_", " ").toUpperCase();
		ciudad = stripAccents(ciudad).replaceAll("_", " ").toUpperCase();

		if (isNotEmpty(departamento) && isNotEmpty(ciudad)) {
			List<String> list = codigos.get(departamento);
			if (list == null) {
				list = new ArrayList<>();
				codigos.put(departamento, new ArrayList<>());
			}
			if (!list.contains(ciudad)) {
				list.add(ciudad);
			}
		}
	}

	private Integer mapTipoIdentificacionId(ImportViewModel model, List<TipoDocumentoDto> list) {
		Integer result = null;
		val value = stripAccents(model.getCodigoTipoIdentificacionId());

		if (isNotEmpty(value)) {
			val optional = list.stream().filter(a -> a.getDescripcion().equalsIgnoreCase(value)).findFirst();
			if (optional.isPresent()) {
				result = optional.get().getId();
			}
		}
		return result;
	}

	private UsuarioDto mapUsuario(ImportViewModel model, int institucionId) {
		UsuarioDto result = null;
		val tipoId = model.getTipoIdentificacionId();
		val numeroId = model.getNumeroIdentificacion();

		if (tipoId != null) {
			val optional = ciberService.findUsuarioEstudianteByInstitucionIdAndIdentificacion(institucionId, tipoId,
					numeroId);
			if (optional.isPresent()) {
				result = optional.get();
			}
		}
		return result;
	}

	private Integer mapInstitucion(ImportViewModel model, int institucionId) {
		Integer result = null;
		if (model.getUsuario() != null) {
			result = institucionId;
		}
		return result;
	}

	private Integer mapDepartamento(String departamento, List<DepartamentoDto> list) {
		Integer result = null;
		val test = stripAccents(departamento).replaceAll("_", " ");
		if (isNotEmpty(departamento)) {
			val optional = list.stream().filter(a -> a.getNombre().equalsIgnoreCase(test)).findFirst();

			if (optional.isPresent()) {
				result = optional.get().getDepartamentoId();
			}
		}

		return result;
	}

	private Integer mapCiudad(Integer departamentoId, String ciudad, List<CiudadDto> list) {
		Integer result = null;
		if (departamentoId != null) {

			if (isNotEmpty(ciudad)) {
				val test = stripAccents(ciudad).replaceAll("_", " ");
				val optional = list.stream().filter(
						a -> departamentoId.equals(a.getDepartamentoId()) && a.getNombre().equalsIgnoreCase(test))
						.findFirst();

				if (optional.isPresent()) {
					result = optional.get().getCiudadId();
				}
			}
		}
		return result;
	}

	// -----------------------------------------------------------------------------------
	// -- CHECK
	// -----------------------------------------------------------------------------------
	private boolean check(List<ImportViewModel> models) {
		boolean result = true;

		for (val model : models) {
			val errores = new ArrayList<String>();

			if (checkTipoId(model, errores)) {
				if (checkUsuario(model, errores)) {
					checkApellidos(model, errores);
					checkNombres(model, errores);
				}
			}

			{
				String horario = "AM";
				Integer departamentoId = model.getDepartamentoAmId();
				String departamento = model.getDepartamentoAm();
				Integer ciudadId = model.getCiudadAmId();
				String ciudad = model.getCiudadAm();
				checkCiudad(horario, departamentoId, departamento, ciudadId, ciudad, errores);
			}

			{
				String horario = "PM";
				Integer departamentoId = model.getDepartamentoPmId();
				String departamento = model.getDepartamentoPm();
				Integer ciudadId = model.getCiudadPmId();
				String ciudad = model.getCiudadPm();
				checkCiudad(horario, departamentoId, departamento, ciudadId, ciudad, errores);
			}

			model.setErrores(errores);
			if (!errores.isEmpty()) {
				result = false;
			}
		}

		return result;
	}

	private boolean checkTipoId(ImportViewModel model, List<String> errores) {
		boolean result = true;
		if (model.getTipoIdentificacionId() == null) {
			val msg = "Tipo de identificación desconocido: %s";
			errores.add(String.format(msg, model.getCodigoTipoIdentificacionId()));
			result = false;
		}
		return result;
	}

	private boolean checkUsuario(ImportViewModel model, List<String> errores) {
		boolean result = true;
		if (model.getUsuario() == null) {
			val msg = "No se encontró un usuario con este tipo y número de identificación: %s, %s";
			errores.add(String.format(msg, model.getCodigoTipoIdentificacionId(), model.getNumeroIdentificacion()));
			result = false;
		}
		return result;
	}

	private void checkNombres(ImportViewModel model, List<String> errores) {
		{
			String a = stripAccents(model.getNombres().trim());
			String b = stripAccents(model.getUsuario().getNombre().trim());

			if (!a.equalsIgnoreCase(b)) {
				val fmt = "Los nombres suministrados en el archivo [%s], no coinciden con los registrados en la base de datos [%s]";
				val msg = String.format(fmt, model.getNombres(), model.getUsuario().getNombre());
				errores.add(msg);
			}
		}
	}

	private void checkApellidos(ImportViewModel model, List<String> errores) {
		{
			String a = stripAccents(model.getApellidos().trim());
			String b = stripAccents(model.getUsuario().getApellido().trim());

			if (!a.equalsIgnoreCase(b)) {
				val fmt = "Los apellidos suministrados en el archivo [%s], no coinciden con los registrados en la base de datos [%s]";
				val msg = String.format(fmt, model.getApellidos(), model.getUsuario().getApellido());
				errores.add(msg);
			}
		}
	}

	private void checkCiudad(String horario, Integer departamentoId, String departamento, Integer ciudadId,
			String ciudad, List<String> errores) {
		if (isNotEmpty(departamento) && isNotEmpty(ciudad)) {
			if (departamentoId == null) {
				val fmt = "Departamento %s desconocido:%s";
				val msg = String.format(fmt, horario, departamento);
				errores.add(msg);
			} else {
				if (ciudadId == null) {
					val fmt = "Municipio %s desconocida:%s";
					val msg = String.format(fmt, horario, ciudad);
					errores.add(msg);
				}
			}
		}
	}

	// -----------------------------------------------------------------------------------
	// --
	// -----------------------------------------------------------------------------------
	private static String stripAccents(String s) {
		s = Normalizer.normalize(s, Normalizer.Form.NFD);
		s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
		return s;
	}
}