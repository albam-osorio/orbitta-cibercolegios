package com.tbf.cibercolegios.api.routes.web.users;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RestController;

import com.tbf.cibercolegios.api.model.routes.enums.CourseType;
import com.tbf.cibercolegios.api.routes.models.users.DireccionViewModel;
import com.tbf.cibercolegios.core.Command;
import com.tbf.cibercolegios.core.DialogCommandCreateController;

import lombok.Getter;
import lombok.Setter;
import lombok.val;

@RestController
@Scope("view")
@Setter
@Getter
public class UsuariosDireccionDialogCreateController extends UsuariosDireccionDialogAbstractController
		implements DialogCommandCreateController<DireccionViewModel>, Serializable {

	private static final long serialVersionUID = 1L;

	private CourseType sentido;

	// ----------------------------------------------------------------------------------------------------
	// --INICIALIZADORES
	// ----------------------------------------------------------------------------------------------------
	@Override
	public DireccionViewModel newModel() {
		val result = new DireccionViewModel();
		result.setSentido(getSentido());
		return result;
	}

	// ----------------------------------------------------------------------------------------------------
	// -- COMANDOS
	// ----------------------------------------------------------------------------------------------------
	public void submit() {
		submit(new CreateCommand(), null);
	}

	private final class CreateCommand extends Command<String> {

		@Override
		protected void execute() {
			{
				val optional = getListaDepartamento().stream().filter(a -> a.getValue().equals(getDepartamentoId()))
						.findFirst();
				if (optional.isPresent()) {
					getModel().setDepartamentoId(getDepartamentoId());
					getModel().setDepartamentoNombre(optional.get().getLabel());
				}
			}

			{
				val optional = getListaCiudad().stream().filter(a -> a.getValue().equals(getCiudadId())).findFirst();
				if (optional.isPresent()) {
					getModel().setCiudadId(getCiudadId());
					getModel().setCiudadNombre(optional.get().getLabel());
				}
			}

			getModel().setDireccion(getDireccion());
			getModel().setModificado(true);

			if (CourseType.SENTIDO_IDA == getModel().getSentido()) {
				getTrayecto().setDireccionAm(getModel());
			}else {
				getTrayecto().setDireccionPm(getModel());
			}
			
			replicarDireccion();
		}
	}
}
