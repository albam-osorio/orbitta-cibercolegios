package com.tbf.cibercolegios.core;

import java.util.ArrayList;
import java.util.List;

import com.tbf.cibercolegios.api.routes.controllers.util.FacesMessages;

import lombok.Getter;
import lombok.Setter;
import lombok.val;

@Setter
@Getter
public abstract class DialogCommandController<T, E> extends DialogController<T, E> {

	private static final long serialVersionUID = 1L;

	public boolean isSubmitEnabled() {
		return true;
	}

	public void submit() {
		submit(null);
	}

	public void submit(String confirmDialogId) {
		try {
			hideDialog(confirmDialogId);

			val errors = new ArrayList<E>();
			testSubmit(errors);
			if (errors.isEmpty()) {
				executeSubmit(errors);
				if (errors.isEmpty()) {
					successSubmit();
				}
			}

			if (!errors.isEmpty()) {
				errorsSubmit(errors);
			}
		} catch (Exception e) {
			error(e);
		}
	}

	protected void testSubmit(List<E> errors) {
		errors.clear();
	}

	protected abstract void executeSubmit(List<E> errors);

	protected void successSubmit() {
		closeDialog();

		FacesMessages.info(getSuccessMessage());
	}

	protected void errorsSubmit(List<E> errors) {
		errors(errors);
	}

	protected String getSuccessMessage() {
		return DEFAULT_SUCCESS_MESSAGE;
	}
}