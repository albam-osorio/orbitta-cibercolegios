package com.tbf.cibercolegios.api.routes.controllers.routes;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RestController;

import com.tbf.cibercolegios.api.routes.controllers.routes.models.RutaViewModel;
import com.tbf.cibercolegios.api.routes.controllers.util.FacesMessages;
import com.tbf.cibercolegios.api.routes.model.graph.RutaDto;
import com.tbf.cibercolegios.core.DialogCommandEditByIdController;

import lombok.Getter;
import lombok.Setter;
import lombok.val;

@RestController
@Scope("view")
@Setter
@Getter
public class RutasDialogEditController extends RutasDialogCommandController
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
		val optional = getRutasService().findById(id);
		return asViewModel(getOne(id, optional, RutaDto.class));
	}
	
	@Override
	protected void populate() {
		super.populate();

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
			ruta = getRutasService().update(ruta);

			String summary = String.format("La ruta con código %s ha sido modificada", ruta.getCodigo());
			FacesMessages.info("Ruta modificada", summary);
		} catch (RuntimeException e) {
			FacesMessages.fatal(e);
		}
	}
}
