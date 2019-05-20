package com.tbf.cibercolegios.api.routes.controllers.routes;

import java.io.Serializable;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RestController;

import com.tbf.cibercolegios.api.routes.controllers.routes.models.RutaViewModel;
import com.tbf.cibercolegios.api.routes.controllers.util.FacesMessages;
import com.tbf.cibercolegios.api.routes.model.graph.RutaDto;
import com.tbf.cibercolegios.core.DialogCommandCreateController;

import lombok.Getter;
import lombok.Setter;
import lombok.val;

@RestController
@Scope("view")
@Setter
@Getter
public class RutasDialogCreateController extends RutasDialogCommandController implements DialogCommandCreateController<RutaViewModel>, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public RutaViewModel newModel() {
		return new RutaViewModel();
	}

	@Override
	public String getTitle() {
		return "AGREGAR NUEVA RUTA";
	}

	@Override
	public String getActionLabel() {
		return "Agregar";
	}

	// ----------------------------------------------------------------------------------------------------
	// -- COMANDOS
	// ----------------------------------------------------------------------------------------------------
	@Override
	protected void executeSubmit(List<String> errors) {
		try {
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
		} catch (RuntimeException e) {
			FacesMessages.fatal(e);
		}
	}
}
