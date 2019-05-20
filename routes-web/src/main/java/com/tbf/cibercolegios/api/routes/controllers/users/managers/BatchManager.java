package com.tbf.cibercolegios.api.routes.controllers.users.managers;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.defaultString;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.upperCase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.tbf.cibercolegios.api.ciber.model.graph.CiudadDto;
import com.tbf.cibercolegios.api.ciber.model.graph.DepartamentoDto;
import com.tbf.cibercolegios.api.ciber.model.graph.TipoDocumentoDto;
import com.tbf.cibercolegios.api.ciber.model.graph.UsuarioDto;
import com.tbf.cibercolegios.api.ciber.services.api.CiberService;
import com.tbf.cibercolegios.api.routes.controllers.users.models.DireccionPasajeroViewModel;
import com.tbf.cibercolegios.api.routes.controllers.users.models.ErrorViewModel;
import com.tbf.cibercolegios.api.routes.controllers.util.FacesMessages;
import com.tbf.cibercolegios.api.routes.controllers.util.UserPreferences;
import com.tbf.cibercolegios.api.routes.model.graph.DireccionDto;
import com.tbf.cibercolegios.api.routes.model.graph.PasajeroDto;
import com.tbf.cibercolegios.api.routes.services.ExcelWorkSheetReader;
import com.tbf.cibercolegios.api.routes.services.api.AcudienteService;
import com.tbf.cibercolegios.api.routes.services.api.DireccionService;
import com.tbf.cibercolegios.api.routes.services.api.PasajeroService;

import lombok.Getter;
import lombok.Setter;
import lombok.val;

@Component
@Setter
@Getter
public class BatchManager implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String[] HEADERS = { "TIPO DE DOCUMENTO", "NÚMERO DE DOCUMENTO", "NOMBRES", "APELLIDOS",
			"DEPARTAMENTO AM", "MUNICIPIO AM", "DIRECCIÓN AM", "DEPARTAMENTO PM", "MUNICIPIO PM", "DIRECCIÓN PM" };

	private static final int NUMERO_MAXIMO_FILAS_VACIAS = 10;

	private static final int NUMERO_MAXIMO_FILAS = 10000;

	@Autowired
	private UserPreferences preferences;

	@Autowired
	private CiberService ciberService;

	@Autowired
	private PasajeroService pasajeroService;

	@Autowired
	private DireccionService direccionService;

	@Autowired
	private AcudienteService acudienteService;

	@Autowired
	private ExcelWorkSheetReader reader;

	@Value("${files.location}")
	private String filesLocation;

	@Value("${files.templates.addresses}")
	private String fileTemplate;

	private List<DireccionPasajeroViewModel> items;

	private List<ErrorViewModel> errors;

	private StreamedContent streamedContent;

	private boolean success = false;

	private boolean failure = false;

	private Integer getInstitucionId() {
		val result = preferences.getInstitucionId();
		return result;
	}

	private Integer getPaisId() {
		val result = preferences.getPaisId();
		return result;
	}

	public StreamedContent getTemplate() throws FileNotFoundException {
		File file = Paths.get(getFilesLocation(), getFileTemplate()).toFile();
		InputStream inputStream = new FileInputStream(file);
		String fileName = file.getName();
		String fileType = "application/vnd.ms-excel";
		val result = new DefaultStreamedContent(inputStream, fileType, fileName);
		return result;
	}

	// -----------------------------------------------------------------------------------
	public boolean init() {
		boolean result = true;
		reset();
		return result;
	}

	public void reset() {
		this.setItems(new ArrayList<>());
		this.setErrors(new ArrayList<>());
		this.setStreamedContent(null);
		this.setSuccess(false);
		this.setFailure(false);
	}

	// -----------------------------------------------------------------------------------
	// -- LOAD
	// -----------------------------------------------------------------------------------
	public void fileUploadListener(FileUploadEvent event) {
		this.setSuccess(false);
		this.setFailure(false);
		this.setItems(new ArrayList<>());
		this.setErrors(new ArrayList<>());

		val file = event.getFile();
		try {
			boolean success = true;
			val data = load(file);

			success = checkColumns(data);
			if (success) {
				val direcciones = asModels(data);

				this.getItems().addAll(direcciones);

				success = checkNotEmptyFields(direcciones);
				if (success) {
					map(direcciones);

					success = check(direcciones);
					if (success) {
						this.setSuccess(true);
					}
				}
				if (!success) {
					this.setErrors(asErrors(getItems()));
					this.setFailure(true);
				}
			}
		} catch (IOException | RuntimeException e) {
			FacesMessages.fatal(e);
		} finally {
			copy(file);
		}
	}

	private List<List<String>> load(UploadedFile file) throws IOException {
		int sheetIndex = 0;
		int rowStart = 0;
		int colStart = 0;
		int rowEnd = NUMERO_MAXIMO_FILAS;
		int colEnd = HEADERS.length;
		int maxEmptyRows = NUMERO_MAXIMO_FILAS_VACIAS;

		val result = reader.read(file.getInputstream(), sheetIndex, rowStart, colStart, rowEnd, colEnd, maxEmptyRows);

		return result;
	}

	// -----------------------------------------------------------------------------------
	// -- CHECK COLUMNS REQUIRED
	// -----------------------------------------------------------------------------------
	private boolean checkColumns(List<List<String>> data) {
		val errores = new ArrayList<String>();
		boolean result = reader.checkColumns(data, HEADERS, errores);

		for (val error : errores) {
			FacesMessages.error(error);
		}

		return result;
	}

	// -----------------------------------------------------------------------------------
	// -- AS MODELS
	// -----------------------------------------------------------------------------------
	private List<DireccionPasajeroViewModel> asModels(List<List<String>> data) {
		val result = new ArrayList<DireccionPasajeroViewModel>();
		int n = data.size();
		for (int index = 1; index < n; index++) {
			val row = data.get(index);
			val model = new DireccionPasajeroViewModel();

			int i = 0;
			model.setIndex(index);
			model.setCodigoTipoIdentificacionId(getField(row, i++));
			model.setNumeroIdentificacion(getField(row, i++));
			model.setNombres(getField(row, i++));
			model.setApellidos(getField(row, i++));
			model.setDepartamentoAm(getField(row, i++));
			model.setCiudadAm(getField(row, i++));
			model.setDireccionAm(getField(row, i++));
			model.setDepartamentoPm(getField(row, i++));
			model.setCiudadPm(getField(row, i++));
			model.setDireccionPm(getField(row, i++));
			model.setErrores(new ArrayList<>());

			if (!model.isEmpty()) {
				result.add(model);
			}
		}

		return result;
	}

	private String getField(List<String> a, int index) {
		return defaultString(upperCase(a.get(index))).trim();
	}

	// -----------------------------------------------------------------------------------
	// -- CHECK EMPTY FILEDS
	// -----------------------------------------------------------------------------------
	private boolean checkNotEmptyFields(List<DireccionPasajeroViewModel> models) {
		boolean result = true;

		for (val model : models) {
			val empties = new ArrayList<String>();

			int i = 0;
			addEmptyField(empties, HEADERS[i++], model.getCodigoTipoIdentificacionId());
			addEmptyField(empties, HEADERS[i++], model.getNumeroIdentificacion());
			addEmptyField(empties, HEADERS[i++], model.getNombres());
			addEmptyField(empties, HEADERS[i++], model.getApellidos());
			addEmptyField(empties, HEADERS[i++], model.getDepartamentoAm());
			addEmptyField(empties, HEADERS[i++], model.getCiudadAm());
			addEmptyField(empties, HEADERS[i++], model.getDepartamentoPm());
			addEmptyField(empties, HEADERS[i++], model.getCiudadPm());
			addEmptyField(empties, HEADERS[i++], model.getDireccionPm());

			if (!empties.isEmpty()) {
				result = false;
				val e = String.join(",", empties);
				val msg = String.format("Los siguientes campos son requeridos y estan vacíos: %s.", e);
				model.getErrores().add(msg);
			}
		}

		return result;
	}

	private void addEmptyField(List<String> empties, String field, String value) {
		if (isEmpty(value)) {
			empties.add(field);
		}
	}

	// -----------------------------------------------------------------------------------
	// -- MAP
	// -----------------------------------------------------------------------------------
	private void map(List<DireccionPasajeroViewModel> models) {
		val tiposId = ciberService.findAllTiposDocumento();
		tiposId.stream().forEach(a -> a.setDescripcion(stripAccents(a.getDescripcion()).toUpperCase()));

		val departamentos = ciberService.findAllDepartamentosByPais(getPaisId());
		departamentos.stream().forEach(a -> a.setNombre(stripAccents(a.getNombre()).toUpperCase()));

		val ciudades = findCiudades(getPaisId(), departamentos, models);

		for (val model : models) {
			model.setTipoIdentificacionId(mapTipoIdentificacionId(model, tiposId));
			model.setUsuario(mapUsuario(model));
			model.setInstitucionId(mapInstitucion(model));
			model.setDepartamentoAmId(mapDepartamento(model.getDepartamentoAm(), departamentos));
			model.setDepartamentoPmId(mapDepartamento(model.getDepartamentoPm(), departamentos));
			model.setCiudadAmId(mapCiudad(model.getDepartamentoAmId(), model.getCiudadAm(), ciudades));
			model.setCiudadPmId(mapCiudad(model.getDepartamentoPmId(), model.getCiudadPm(), ciudades));
		}
	}

	private List<CiudadDto> findCiudades(Integer paisId, List<DepartamentoDto> departamentos,
			List<DireccionPasajeroViewModel> pasajeros) {
		val codigos = new HashMap<String, List<String>>();
		for (val mode : pasajeros) {
			asCodigoCiudad(codigos, mode.getDepartamentoAm(), mode.getCiudadAm());
			asCodigoCiudad(codigos, mode.getDepartamentoPm(), mode.getCiudadPm());
		}

		val result = new ArrayList<CiudadDto>();
		for (val departamento : codigos.keySet()) {
			val list = codigos.get(departamento);
			val optional = departamentos.stream().filter(a -> a.getNombre().equalsIgnoreCase(departamento)).findFirst();

			if (optional.isPresent()) {
				val departamentoId = optional.get().getDepartamentoId();
				val ciudades = ciberService.findAllCiudadesByDepartamento(paisId, departamentoId);
				ciudades.stream().forEach(a -> a.setNombre(stripAccents(a.getNombre()).toUpperCase()));

				for (val ciudad : list) {
					result.addAll(ciudades.stream().filter(a -> a.getNombre().equalsIgnoreCase(ciudad))
							.collect(Collectors.toList()));
				}
			}
		}

		return result;
	}

	private void asCodigoCiudad(Map<String, List<String>> codigos, String departamento, String ciudad) {
		departamento = stripAccents(departamento).replaceAll("_", " ").toUpperCase();
		ciudad = stripAccents(departamento).replaceAll("_", " ").toUpperCase();
		List<String> list = codigos.get(departamento);
		if (list == null) {
			list = new ArrayList<>();
			codigos.put(departamento, new ArrayList<>());
		}
		if (!list.contains(ciudad)) {
			list.add(ciudad);
		}
	}

	private Integer mapTipoIdentificacionId(DireccionPasajeroViewModel model, List<TipoDocumentoDto> list) {
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

	private UsuarioDto mapUsuario(DireccionPasajeroViewModel model) {
		UsuarioDto result = null;
		val tipoId = model.getTipoIdentificacionId();
		val numeroId = model.getNumeroIdentificacion();

		if (tipoId != null) {
			val optional = ciberService.findUsuarioByIdentificacion(tipoId, numeroId);
			if (optional.isPresent()) {
				result = optional.get();
			}
		}
		return result;
	}

	private Integer mapInstitucion(DireccionPasajeroViewModel model) {
		Integer result = null;
		if (model.getUsuario() != null) {
			int usuarioId = model.getUsuario().getId();
			int institucionId = getInstitucionId();

			boolean pertenece = ciberService.isEstudianteBelongToInstitucion(usuarioId, institucionId);
			if (pertenece) {
				result = institucionId;
			}
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
	private boolean check(List<DireccionPasajeroViewModel> models) {
		boolean result = true;

		for (val model : models) {
			val errores = new ArrayList<String>();

			if (checkTipoId(model, errores)) {
				if (checkUsuario(model, errores)) {
					if (checkInstitucion(model, errores)) {
						checkApellidos(model, errores);
						checkNombres(model, errores);
					}
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

	private boolean checkTipoId(DireccionPasajeroViewModel model, List<String> errores) {
		boolean result = true;
		if (model.getTipoIdentificacionId() == null) {
			val msg = "Tipo de identificación desconocido: %s";
			errores.add(String.format(msg, model.getCodigoTipoIdentificacionId()));
			result = false;
		}
		return result;
	}

	private boolean checkUsuario(DireccionPasajeroViewModel model, List<String> errores) {
		boolean result = true;
		if (model.getUsuario() == null) {
			val msg = "No se encontró un usuario con este tipo y número de identificación: %s, %s";
			errores.add(String.format(msg, model.getCodigoTipoIdentificacionId(), model.getNumeroIdentificacion()));
			result = false;
		}
		return result;
	}

	private boolean checkInstitucion(DireccionPasajeroViewModel model, List<String> errores) {
		boolean result = true;
		if (model.getInstitucionId() == null) {
			val msg = "El usuario no pertenece a esta institución";
			errores.add(msg);
			result = false;
		}
		return result;
	}

	private void checkNombres(DireccionPasajeroViewModel model, List<String> errores) {
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

	private void checkApellidos(DireccionPasajeroViewModel model, List<String> errores) {
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
		if (departamentoId == null) {
			val fmt = "Departamento %s desconocido:%s";
			val msg = String.format(fmt, horario, departamento);
			errores.add(msg);
		} else {
			if (ciudadId == null) {
				val fmt = "Ciudad %s desconocida:%s";
				val msg = String.format(fmt, horario, ciudad);
				errores.add(msg);
			}
		}
	}

	// -----------------------------------------------------------------------------------
	// --
	// -----------------------------------------------------------------------------------
	private static List<ErrorViewModel> asErrors(List<DireccionPasajeroViewModel> models) {
		val result = models.stream().filter(a -> !a.getErrores().isEmpty())
				.map(a -> a.getErrores().stream()
						.map(b -> new ErrorViewModel(a.getIndex(), a.getNumeroIdentificacion(), b)).collect(toList()))
				.flatMap(a -> a.stream()).collect(toList());
		return result;
	}

	private void copy(UploadedFile file) {
		try {
			val stream = file.getInputstream();
			val contentType = file.getContentType();
			val fileName = file.getFileName();
			this.streamedContent = new DefaultStreamedContent(stream, contentType, fileName);
		} catch (Exception e) {
			FacesMessages.fatal(e);
		}
	}

	// -----------------------------------------------------------------------------------
	// --
	// -----------------------------------------------------------------------------------
	public boolean save() {
		boolean result = true;
		for (val item : this.items) {
			int usuarioId = item.getUsuario().getId();

			DireccionDto direccionIda = asNewDirecccionIda(item);
			DireccionDto direccionRetorno = asNewDirecccionRetorno(item);
			val usuariosIdDeLosAcudientes = ciberService.findUsuariosIdDeAcudientesByUsuarioId(usuarioId);

			val optional = pasajeroService.findByUsuarioId(usuarioId);

			if (!optional.isPresent()) {
				PasajeroDto pasajero = asNewPasajero(item);

				pasajero = pasajeroService.create(pasajero, direccionIda, direccionRetorno, usuariosIdDeLosAcudientes);
			} else {
				PasajeroDto pasajero = optional.get();
				//Si el pasajero ya existe, e le coloca a las nuevas direcciones el id de las direcciones del pasajero para que se obligue un UPDATE 
				direccionIda.setId(pasajero.getDireccionIdaId());
				direccionRetorno.setId(pasajero.getDireccionRetornoId());

				pasajero = pasajeroService.update(pasajero, direccionIda, direccionRetorno, usuariosIdDeLosAcudientes);
			}
		}
		return result;
	}

	private DireccionDto asNewDirecccionIda(DireccionPasajeroViewModel item) {
		DireccionDto result;

		result = new DireccionDto();
		result.setInstitucionId(getInstitucionId());
		result.setEstadoId(DireccionDto.ESTADO_NO_PROCESADA);
		result.setPaisId(getPaisId());
		result.setDepartamentoId(item.getDepartamentoAmId());
		result.setCiudadId(item.getCiudadAmId());
		result.setDireccion(item.getDireccionAm());

		return result;
	}

	private DireccionDto asNewDirecccionRetorno(DireccionPasajeroViewModel item) {
		DireccionDto result;

		result = new DireccionDto();
		result.setInstitucionId(getInstitucionId());
		result.setEstadoId(DireccionDto.ESTADO_NO_PROCESADA);
		result.setPaisId(getPaisId());
		result.setDepartamentoId(item.getDepartamentoPmId());
		result.setCiudadId(item.getCiudadPmId());
		result.setDireccion(item.getDireccionPm());

		return result;
	}

	private PasajeroDto asNewPasajero(DireccionPasajeroViewModel item) {
		val result = new PasajeroDto();

		result.setUsuarioId(item.getUsuario().getId());
		result.setSecuenciaIda(0);
		result.setDireccionIdaId(null);
		result.setSecuenciaRetorno(0);
		result.setDireccionRetornoId(null);
		result.setEstadoId(PasajeroDto.ESTADO_INACTIVO);
		result.setAcudientes(new ArrayList<>());

		return result;
	}

	public static String stripAccents(String s) {
		s = Normalizer.normalize(s, Normalizer.Form.NFD);
		s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
		return s;
	}
}