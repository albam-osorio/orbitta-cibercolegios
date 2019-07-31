package com.tbf.cibercolegios.api.routes.web.users;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static java.util.stream.Collectors.toList;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RestController;

import com.tbf.cibercolegios.api.ciber.model.graph.UsuarioDto;
import com.tbf.cibercolegios.api.ciber.services.api.CiberService;
import com.tbf.cibercolegios.api.routes.model.graph.DireccionDto;
import com.tbf.cibercolegios.api.routes.model.graph.EstadoDireccionDto;
import com.tbf.cibercolegios.api.routes.model.graph.EstadoPasajeroDto;
import com.tbf.cibercolegios.api.routes.model.graph.PasajeroDto;
import com.tbf.cibercolegios.api.routes.models.users.ErrorViewModel;
import com.tbf.cibercolegios.api.routes.models.users.ImportViewModel;
import com.tbf.cibercolegios.api.routes.services.api.AcudienteService;
import com.tbf.cibercolegios.api.routes.services.api.DireccionService;
import com.tbf.cibercolegios.api.routes.services.api.PasajeroService;
import com.tbf.cibercolegios.api.routes.services.users.DireccionesBatchService;
import com.tbf.cibercolegios.api.routes.web.WebSettings;
import com.tbf.cibercolegios.api.routes.web.utils.FacesMessages;
import com.tbf.cibercolegios.api.routes.web.utils.UserProfile;
import com.tbf.cibercolegios.core.AbstractController;
import com.tbf.cibercolegios.core.Command;

import lombok.Getter;
import lombok.Setter;
import lombok.val;

@RestController
@Scope("view")
@Setter
@Getter
public class UsuariosBatchController extends AbstractController<String> {

	private static final long serialVersionUID = 1L;

	private static final String CLIENT_ID = "local";

	// -----------------------------------------------------------------------------------
	// --
	// -----------------------------------------------------------------------------------
	@Autowired
	private UserProfile profile;

	@Autowired
	private CiberService ciberService;

	@Autowired
	private DireccionesBatchService direccionesBatchService;

	@Autowired
	private PasajeroService pasajeroService;

	@Autowired
	private DireccionService direccionService;

	@Autowired
	private AcudienteService acudienteService;

	// -----------------------------------------------------------------------------------
	// --
	// -----------------------------------------------------------------------------------
	@Value(WebSettings.PARAM_FILES_HOME_LOCATION)
	private String filesHomeLocation;

	@Value(WebSettings.PARAM_FILES_TEMPLATES_DIRECCIONES)
	private String fileTemplateName;

	// -----------------------------------------------------------------------------------
	// --
	// -----------------------------------------------------------------------------------
	private StreamedContent streamedContent;

	private List<ImportViewModel> items;

	private List<ErrorViewModel> errors;

	private boolean activo = false;

	private boolean success = false;

	private boolean failure = false;
	
	private Integer progreso = 0;

	// -----------------------------------------------------------------------------------
	// --
	// -----------------------------------------------------------------------------------
	@Override
	protected void init() {
		this.items = new ArrayList<>();
		this.errors = new ArrayList<>();
	}

	@Override
	protected void clear() {
		this.items.clear();
		this.errors.clear();

		this.activo = false;
		this.success = false;
		this.failure = false;

		this.progreso = 0;
	}

	@Override
	protected boolean populate() {
		return true;
	}

	// -----------------------------------------------------------------------------------
	// -- LOAD
	// -----------------------------------------------------------------------------------
	public void fileUploadListener(FileUploadEvent event) {
		this.reset();
		this.activo = true;

		try {
			boolean success = direccionesBatchService.load(event.getFile().getInputstream(), this.getItems());

			if (success) {
				onSuccess();
			} else {
				onError();
			}
		} catch (IOException | RuntimeException e) {
			FacesMessages.fatal(e);
		} finally {
			copy(event.getFile());
		}
	}

	private void onSuccess() {
		val sb = new StringBuilder();
		sb.append("La información del archivo plano fue validada correctamente ");
		sb.append("y se ha generado un nuevo proceso de cargue masivo.");
		sb.append("<br>");
		sb.append("Puede oprimir el botón <strong>Aprobar</strong> para ejecutar el proceso y ver el resumen.");

		val msg = new FacesMessage(FacesMessage.SEVERITY_INFO, sb.toString(), "");
		FacesContext.getCurrentInstance().addMessage(CLIENT_ID, msg);
		this.success = true;
		this.failure = false;
	}

	private void onError() {
		this.items.stream().filter(a -> !a.getErrores().isEmpty()).forEach(a -> {
			a.getErrores().forEach(e -> {
				this.errors.add(new ErrorViewModel(a.getIndex(), a.getNumeroIdentificacion(), e));
			});
		});
		this.success = false;
		this.failure = true;
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

	public StreamedContent getTemplate() throws FileNotFoundException {
		File file = Paths.get(this.getFilesHomeLocation(), this.getFileTemplateName()).toFile();
		InputStream inputStream = new FileInputStream(file);
		String fileName = file.getName();
		String contentType = "application/vnd.ms-excel";
		val result = new DefaultStreamedContent(inputStream, contentType, fileName);
		return result;
	}

	// -----------------------------------------------------------------------------------
	// --
	// -----------------------------------------------------------------------------------
	public void save() {
		submit(new SaveCommand());
	}

	// -----------------------------------------------------------------------------------
	// --
	// -----------------------------------------------------------------------------------
	private final class SaveCommand extends Command<String> {

		@Override
		protected void execute() {
			setProgreso(0);
			double i = 0;
			double n = getItems().size();

			Integer institucionId = getProfile().getInstitucionId();
			for (val item : getItems()) {
				int usuarioId = item.getUsuario().getId();

				boolean activo = getItems().stream().filter(a -> a.getUsuario().getId().equals(usuarioId))
						.count() == 1L;

				val direccionIda = asNewDireccion(item.getDepartamentoAmId(), item.getCiudadAmId(),
						item.getDireccionAm());
				val direccionRetorno = asNewDireccion(item.getDepartamentoPmId(), item.getCiudadPmId(),
						item.getDireccionPm());

				val usuariosAcudientesId = getCiberService()
						.findUsuariosAcudientesByInstitucionIdAndUsuarioPasajeroId(institucionId, usuarioId).stream().map(UsuarioDto::getId).distinct().collect(toList());

				val optional = getPasajeroService().findByInstitucionIdAndUsuarioId(institucionId, usuarioId);
				if (!optional.isPresent()) {
					val pasajero = asNewPasajero(item);
					getPasajeroService().create(pasajero, direccionIda, direccionRetorno, usuariosAcudientesId, activo);
				} else {
					val pasajero = optional.get();
					getPasajeroService().update(pasajero, direccionIda, direccionRetorno, usuariosAcudientesId, activo);
				}
				i++;

				setProgreso((int) (100.0 * i / n));
			}
			setProgreso(100);

			val sb = new StringBuilder();
			sb.append("El proceso de cargue fue ejecutado con éxito.");

			val msg = new FacesMessage(FacesMessage.SEVERITY_INFO, sb.toString(), "");
			FacesContext.getCurrentInstance().addMessage(CLIENT_ID, msg);

			reset();
		}

		private Optional<DireccionDto> asNewDireccion(Integer departamentoId, Integer ciudadId, String direccion) {
			if (departamentoId != null && ciudadId != null && isNotEmpty(direccion)) {
				val result = new DireccionDto();

				result.setInstitucionId(profile.getInstitucionId());
				result.setEstadoId(EstadoDireccionDto.ESTADO_NO_GEOREFERENCIADA);
				result.setPaisId(profile.getPaisId());
				result.setDepartamentoId(departamentoId);
				result.setCiudadId(ciudadId);
				result.setDireccion(direccion);

				return Optional.of(result);
			} else {
				return Optional.empty();
			}
		}

		private PasajeroDto asNewPasajero(ImportViewModel item) {
			val result = new PasajeroDto();

			result.setInstitucionId(profile.getInstitucionId());
			result.setUsuarioId(item.getUsuario().getId());
			result.setEstadoId(EstadoPasajeroDto.ESTADO_INACTIVO);

			return result;
		}
	}
}
