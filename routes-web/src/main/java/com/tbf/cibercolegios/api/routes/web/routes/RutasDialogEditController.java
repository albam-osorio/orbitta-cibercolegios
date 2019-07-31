package com.tbf.cibercolegios.api.routes.web.routes;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RestController;

import com.tbf.cibercolegios.api.routes.model.graph.RutaDto;
import com.tbf.cibercolegios.api.routes.models.routes.RutaViewModel;
import com.tbf.cibercolegios.api.routes.web.utils.FacesMessages;
import com.tbf.cibercolegios.core.Command;
import com.tbf.cibercolegios.core.DialogCommandEditByIdController;

import lombok.Getter;
import lombok.Setter;
import lombok.val;

@RestController
@Scope("view")
@Setter
@Getter
public class RutasDialogEditController extends RutasDialogAbstractController
		implements DialogCommandEditByIdController<RutaViewModel, Integer> {

	private static final long serialVersionUID = 1L;

	@Override
	public String getTitle() {
		return "EDITAR RUTA";
	}

	@Override
	public String getActionLabel() {
		return "Actualizar";
	}

	// ----------------------------------------------------------------------------------------------------
	// --INICIALIZADORES
	// ----------------------------------------------------------------------------------------------------
	@Override
	public RutaViewModel findById(Integer id) {
		val ruta = getOne(id, getRutasService().findById(id), RutaDto.class);

		val monitor = getCiberService()
				.findUsuarioMonitorByInstitucionIdAndUsuarioId(ruta.getInstitucionId(), ruta.getMonitorId()).get();
		val direccion = getDireccionesService().findById(ruta.getDireccionSedeId()).get();

		val result = new RutaViewModel();
		result.setRuta(ruta);

		result.setMonitor(monitor);
		result.setTipoIdentificacionId(monitor.getTipoIdentificacionId());
		result.setNumeroIdentificacion(monitor.getNumeroIdentificacion());
		result.setNombreMonitor(monitor.getNombreCompleto());

		result.setUsarDireccionExistente(true);
		result.setDepartamentoId(direccion.getDepartamentoId());
		result.setCiudadId(direccion.getCiudadId());
		result.setDireccionNueva("");
		result.setDireccionExistente("");

		return result;
	}

	@Override
	protected boolean populate() {
		boolean success = super.populate();

		if (success) {
			String value = "";
			val id = this.getModel().getRuta().getDireccionSedeId();
			if (id != null) {
				val optional = this.getListaDireccion().stream().filter(a -> a.getValue().equals(id)).findFirst();
				if (optional.isPresent()) {
					value = optional.get().getLabel();
				}
			}
			this.getModel().setDireccionExistente(value);
		}

		return success;
	}

	// ----------------------------------------------------------------------------------------------------
	// -- COMANDOS
	// ----------------------------------------------------------------------------------------------------
	public void submit() {
		submit(new UpdateCommand(), null);
	}

	private final class UpdateCommand extends Command<String> {

		@Override
		protected void test(List<String> errors) {
			super.test(errors);

			val model = getModel();
			if (model.getMonitor().getId() == null) {
				errors.add("Debe suministrar los datos del monitor de la ruta");
				return;
			}
		}

		@Override
		protected void execute() {
			val model = getModel();

			if (model.getRuta().getDireccionSedeId() == null) {
				val direccion = asDireccion(model);
				val direccionId = getDireccionesService().create(direccion).getId();
				model.getRuta().setDireccionSedeId(direccionId);
			}

			RutaDto ruta = asModel(model);
			ruta = getRutasService().update(ruta);

			String summary = String.format("La ruta con código %s ha sido modificada", ruta.getCodigo());
			FacesMessages.info("Ruta modificada", summary);
		}
	}
}
