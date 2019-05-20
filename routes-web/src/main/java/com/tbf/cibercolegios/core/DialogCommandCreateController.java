package com.tbf.cibercolegios.core;

public interface DialogCommandCreateController<T> {

	T newModel();

	default void openDialog(String dialogId) {
		this.openDialog(dialogId, newModel());
	}

	void openDialog(String dialogId, T model);
}
