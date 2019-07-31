package com.tbf.cibercolegios.api.routes.web.users;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RestController;

import com.tbf.cibercolegios.core.Command;

import lombok.Getter;
import lombok.Setter;
import lombok.val;

@RestController
@Scope("view")
@Setter
@Getter
public class UsuariosDireccionDialogEditController extends UsuariosDireccionDialogAbstractController {

	private static final long serialVersionUID = 1L;

	private boolean geoCodificada;

	// ----------------------------------------------------------------------------------------------------
	// --INICIALIZADORES
	// ----------------------------------------------------------------------------------------------------
	@Override
	protected boolean populate() {
		boolean result = super.populate();

		if (result) {
			this.setGeoCodificada(this.getModel().isGeoCodificada());
		}

		return result;
	}

	// ----------------------------------------------------------------------------------------------------
	// -- COMANDOS
	// ----------------------------------------------------------------------------------------------------
	public void restablecerCoordenadas() {
		this.setGeoCodificada(false);
	}

	public void submit() {
		submit(new UpdateCommand(), null);
	}

	private final class UpdateCommand extends Command<String> {

		@Override
		protected void execute() {
			boolean modificado = getModel().isModificado();
			boolean departamentoModificado = false;

			if (!getDepartamentoId().equals(getModel().getDepartamentoId())) {
				val optional = getListaDepartamento().stream().filter(a -> a.getValue().equals(getDepartamentoId()))
						.findFirst();
				if (optional.isPresent()) {
					getModel().setDepartamentoId(getDepartamentoId());
					getModel().setDepartamentoNombre(optional.get().getLabel());
					departamentoModificado = true;
					modificado = true;
				}
			}

			if (departamentoModificado || !getCiudadId().equals(getModel().getCiudadId())) {
				val optional = getListaCiudad().stream().filter(a -> a.getValue().equals(getCiudadId())).findFirst();
				if (optional.isPresent()) {
					getModel().setCiudadId(getCiudadId());
					getModel().setCiudadNombre(optional.get().getLabel());
					modificado = true;
				}
			}

			if (!getDireccion().equals(getModel().getDireccion())) {
				getModel().setDireccion(getDireccion());
				modificado = true;
			}

			if (isGeoCodificada() != getModel().isGeoCodificada()) {
				getModel().setGeoCodificada(isGeoCodificada());
				modificado = true;
			}

			getModel().setModificado(modificado);

			replicarDireccion();
		}
	}
}
