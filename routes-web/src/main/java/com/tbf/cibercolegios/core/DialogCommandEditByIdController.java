package com.tbf.cibercolegios.core;

public interface DialogCommandEditByIdController<T, ID> {

	default void openDialogWithId(String dialogId, ID id) {
		this.openDialog(dialogId, findById(id));
	}
	
	T findById(ID id);
	
	void openDialog(String dialogId, T model);
}
