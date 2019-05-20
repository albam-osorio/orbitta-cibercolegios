package com.tbf.cibercolegios.core;

import java.util.ArrayList;
import java.util.List;

import org.primefaces.event.data.PageEvent;

import lombok.Getter;
import lombok.Setter;
import lombok.val;

@Setter
@Getter
public abstract class CrudController<T, E> extends AbstractController<E> {

	private static final long serialVersionUID = 1L;

	private List<T> models;

	private List<T> selection;

	// ----------------------------------------------------------------------------------------------------
	// -- INITIALIZERS
	// ----------------------------------------------------------------------------------------------------
	@Override
	protected void init() {
		models = new ArrayList<>();
		selection = new ArrayList<>();
	}

	@Override
	protected void clear() {
		super.clear();
		clearModels();
	}

	protected void clearModels() {
		models.clear();
		selection.clear();
	}

	// ----------------------------------------------------------------------------------------------------
	// -- FIND
	// ----------------------------------------------------------------------------------------------------
	public boolean findEnabled() {
		return true;
	}

	public void find() {
		try {
			clearModels();

			val errors = new ArrayList<E>();
			testFind(errors);
			if (errors.isEmpty()) {
				executeFind();
				successFind();
			}

			if (!errors.isEmpty()) {
				errorsFind(errors);
			}
		} catch (Exception e) {
			error(e);
		}
	}

	protected void testFind(List<E> errors) {
		errors.clear();
	}

	protected abstract void executeFind();

	protected void successFind() {

	}

	protected void errorsFind(List<E> errors) {
		errors(errors);
	}

	// ----------------------------------------------------------------------------------------------------
	// -- PAGINATION
	// ----------------------------------------------------------------------------------------------------
	public void onChangePage(PageEvent event) {
		if (event.getPage() == 0) {
			// TODO
		}
	}

}
