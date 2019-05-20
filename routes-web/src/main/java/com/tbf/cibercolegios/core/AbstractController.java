package com.tbf.cibercolegios.core;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import com.tbf.cibercolegios.api.core.domain.IdentifiedDomainObject;
import com.tbf.cibercolegios.api.routes.controllers.util.FacesMessages;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class AbstractController<E> implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean initialized = false;

	// ----------------------------------------------------------------------------------------------------
	// -- INITIALIZERS
	// ----------------------------------------------------------------------------------------------------
	@PostConstruct
	final protected void postConstructor() {
		try {
			setInitialized(false);
			init();
		} catch (RuntimeException e) {
			FacesMessages.fatal("Ocurrio un error fatal durante la inicialización (@PostConstruct) del Controller: "
					+ getClass().getName(), e.getMessage());
		}
	}

	protected abstract void init();

	final protected void reset() {
		try {
			setInitialized(false);
			clear();
			populate();
			setInitialized(true);
		} catch (RuntimeException e) {
			FacesMessages.fatal("Ocurrio un error fatal durante la inicialización (@PostConstruct) del Controller: "
					+ getClass().getName(), e.getMessage());
		}
	}

	protected void clear() {

	}

	protected abstract void populate();

	// ----------------------------------------------------------------------------------------------------
	// -- UTILS esto deberia estar en una librearia
	// ----------------------------------------------------------------------------------------------------
	protected void error(Exception e) {
		FacesMessages.fatal(e);
	}

	protected void errors(List<E> errors) {
		for (E error : errors) {
			FacesMessages.error(error.toString());
		}
	}

	protected LocalDate toLocalDate(java.util.Date fechaUtilDate) {
		return new java.sql.Date(fechaUtilDate.getTime()).toLocalDate();
	}

	protected <ID, S extends IdentifiedDomainObject<?>> S getOne(ID id, Optional<S> optional, Class<S> clazz) {
		if (optional.isPresent()) {
			return optional.get();
		} else {
			throw new RuntimeException("La entidad de tipo " + clazz.getName() + "con id " + id + " no exixte");
		}
	}
}
