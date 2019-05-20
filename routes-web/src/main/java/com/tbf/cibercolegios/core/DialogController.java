package com.tbf.cibercolegios.core;

import org.primefaces.PrimeFaces;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class DialogController<T, E> extends AbstractController<E> {

	private static final long serialVersionUID = 1L;

	protected static final String DEFAULT_SUCCESS_MESSAGE = "El registro se grabó exitosamente";

	private T model;

	@Setter(AccessLevel.PROTECTED)
	private String dialogId;

	public void openDialog(String dialogId, T model) {
		this.setModel(model);
		this.reset();
		if (isInitialized()) {
			this.showDialog(dialogId);
		}
	}

	public void closeDialog() {
		this.setInitialized(false);
		this.hideDialog(getDialogId());
		this.setDialogId(null);
		this.clear();
		this.setModel(null);
	}

	protected void showDialog(String dialogId) {
		if (dialogId != null) {
			if (!"".equals(dialogId.trim())) {
				setDialogId(dialogId);
				PrimeFaces.current().executeScript("PF('" + dialogId + "').show();");
			}
		}
	}

	protected void hideDialog(String dialogId) {
		if (dialogId != null) {
			if (!"".equals(dialogId.trim())) {
				PrimeFaces.current().executeScript("PF('" + dialogId + "').hide();");
			}
		}
	}
}
