package com.tbf.cibercolegios.core;

import java.io.Serializable;
import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;
import lombok.val;

@Setter
@Getter
public abstract class DialogCommandController<T, E> extends DialogController<T, E> implements Serializable {

	private static final long serialVersionUID = 1L;

	public boolean isSubmitEnabled() {
		return true;
	}

	protected void submit(final Command<E> command, String confirmDialogId) {
		try {
			hideDialog(confirmDialogId);

			val errors = new ArrayList<E>();
			command.submit(errors);
			if (!errors.isEmpty()) {
				errors(errors);
			} else {
				closeDialog();
			}
		} catch (RuntimeException e) {
			error(e);
		}
	}

	protected String getSuccessMessage() {
		return DEFAULT_SUCCESS_MESSAGE;
	}
}