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
		if (selection != null) {
			selection.clear();
		} else {
			selection = new ArrayList<>();
		}
	}

	// ----------------------------------------------------------------------------------------------------
	// -- FIND
	// ----------------------------------------------------------------------------------------------------
	public boolean findEnabled() {
		return true;
	}

	public void find() {
		val command = new Command<E>() {

			@Override
			protected void test(List<E> errors) {
				testFind(errors);
			}
			
			@Override
			protected void execute() {
				clearModels();

				executeFind();
				successFind();
			}
		};

		submit(command);
	}

	protected void testFind(List<E> errors) {

	}
	
	protected abstract void executeFind();

	protected void successFind() {

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
