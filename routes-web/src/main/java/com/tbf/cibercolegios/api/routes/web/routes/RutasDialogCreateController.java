package com.tbf.cibercolegios.api.routes.web.routes;

import java.io.Serializable;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RestController;

import com.tbf.cibercolegios.api.routes.model.graph.RutaDto;
import com.tbf.cibercolegios.api.routes.models.routes.RutaViewModel;
import com.tbf.cibercolegios.api.routes.web.utils.FacesMessages;
import com.tbf.cibercolegios.core.Command;
import com.tbf.cibercolegios.core.DialogCommandCreateController;

import lombok.Getter;
import lombok.Setter;
import lombok.val;

@RestController
@Scope("view")
@Setter
@Getter
public class RutasDialogCreateController extends RutasDialogAbstractController
		implements DialogCommandCreateController<RutaViewModel>, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public String getTitle() {
		return "AGREGAR NUEVA RUTA";
	}

	@Override
	public String getActionLabel() {
		return "Agregar";
	}

	// ----------------------------------------------------------------------------------------------------
	// --INICIALIZADORES
	// ----------------------------------------------------------------------------------------------------
	@Override
	public RutaViewModel newModel() {
		return new RutaViewModel();
	}

	// ----------------------------------------------------------------------------------------------------
	// -- COMANDOS
	// ----------------------------------------------------------------------------------------------------
	public void submit() {
		submit(new CreateCommand(), null);
	}

	private final class CreateCommand extends Command<String> {

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
			ruta = getRutasService().create(ruta);

			String summary = String.format("La ruta con código %s ha sido creada", ruta.getCodigo());
			FacesMessages.info("Ruta creada", summary);
		}
	}
}
