package com.tbf.cibercolegios.core;

import java.io.IOException;
import java.io.Serializable;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.tbf.cibercolegios.api.core.domain.IdentifiedDomainObject;
import com.tbf.cibercolegios.api.routes.web.utils.FacesMessages;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Setter
@Getter
@Slf4j
public abstract class AbstractController<E> implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean initialized = false;
	
	private String backUrl;

	public boolean isInitialized() {
		return initialized;
	}

	public void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}

	// ----------------------------------------------------------------------------------------------------
	// -- INITIALIZERS
	// ----------------------------------------------------------------------------------------------------
	@PostConstruct
	final protected void postConstructor() {
		try {
			setInitialized(false);
			init();
		} catch (RuntimeException e) {
			val format = "Ocurrio un error fatal durante la inicialización (@PostConstruct) del Controller: %s.";
			error(new RuntimeException(String.format(format, getClass().getName()), e));
		}
	}

	/**
	 * Utilice este metodo para
	 * <ul>
	 * <li>Instanciar variables que no deban estar nunca con valores null. Por
	 * ejemplo listas y valores usados en la pagina de nodo que no ocurran errores
	 * de tipo NullPointerException.</li>
	 * <li>Poblar datos que no vayan a cambiar durante la interaccion del usuario,
	 * en especvial si son costosos de consultar. Por ejemplo ciudades.</li>
	 * <ul>
	 */
	protected abstract void init();

	final public void reset() {
		try {
			setInitialized(false);
			clear();

			if (populate()) {
				setInitialized(true);
			}
		} catch (RuntimeException e) {
			error(e);
		}
	}

	protected void clear() {

	}

	protected abstract boolean populate();

	// ----------------------------------------------------------------------------------------------------
	// --
	// ----------------------------------------------------------------------------------------------------
	protected void submit(final Command<E> command) {
		try {
			val errors = new ArrayList<E>();
			command.submit(errors);
			if (!errors.isEmpty()) {
				errors(errors);
			}
		} catch (RuntimeException e) {
			error(e);
		}
	}

	public String back() {
		return this.getBackUrl();
	}

	// ----------------------------------------------------------------------------------------------------
	// -- UTILS esto deberia estar en una librearia
	// ----------------------------------------------------------------------------------------------------
	protected static void redirect(String queryString, Object... arg) {
		ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
		val url = ctx.getRequestContextPath() + String.format(queryString, arg);

		try {
			ctx.redirect(url);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	protected static String getUrlBase() {
		val request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String fullurl = request.getRequestURL().toString();
		return fullurl.substring(0, fullurl.length() - request.getRequestURI().length()) + request.getContextPath();
	}

	public String getParamBackUrl() {
		StringBuilder sb = new StringBuilder();
		val object = FacesContext.getCurrentInstance().getExternalContext().getRequest();

		String parameter;
		try {
			if (object instanceof HttpServletRequest) {
				val request = (HttpServletRequest) object;
				sb.append(request.getRequestURI().toString());
				sb.append("?faces-redirect=true");

				Enumeration<String> parameters = request.getParameterNames();

				while (parameters.hasMoreElements()) {
					parameter = parameters.nextElement();
					if(parameter.startsWith("javax.faces")) {
						continue;
					}
					if(parameter.contains(":")) {
						continue;
					}
					
					sb.append("&");
					sb.append(parameter);
					sb.append("=");
					sb.append(URLEncoder.encode(request.getParameter(parameter), "UTF-8"));
				}
			}
		} catch (Exception e) {
			// Do nothing
		}

		return sb.toString();
	}

	protected void error(Exception e) {
		val cause = (e.getCause() != null)
				? e.getCause().getMessage() != null ? e.getCause().getMessage() : e.getCause().getClass().getName()
				: null;
		val message = e.getMessage() != null ? e.getMessage() : e.getClass().getName();

		val sb = new StringBuilder();
		sb.append(message);
		if (cause != null) {
			sb.append(". Causa:").append(cause);
		}
		val msg = String.format(sb.toString());

		log.error(msg);
		FacesMessages.fatal(msg, "");
	}

	protected void errors(List<E> errors) {
		for (E error : errors) {
			FacesMessages.error(error.toString());
		}
	}

	private static DateTimeFormatter DATETIME_FORMATTER;

	protected static String getDateTimePatternFormatter() {
		return "yyyy-mm-dd HH:mm";
	}

	protected static DateTimeFormatter getDateTimeFormatter() {
		if (DATETIME_FORMATTER == null) {
			DATETIME_FORMATTER = DateTimeFormatter.ofPattern(getDateTimePatternFormatter());
		}
		return DATETIME_FORMATTER;
	}

	protected LocalDate toLocalDate(java.util.Date fecha) {
		return new java.sql.Date(fecha.getTime()).toLocalDate();
	}

	public String convertDateTime(LocalDateTime date) {
		if (date != null) {
			return getDateTimeFormatter().format(date);
		}
		return "";
	}

	protected <ID, S extends IdentifiedDomainObject<?>> S getOne(ID id, Optional<S> optional, Class<S> clazz) {
		if (optional.isPresent()) {
			return optional.get();
		} else {
			throw new RuntimeException("La entidad de tipo " + clazz.getName() + "con id " + id + " no exixte");
		}
	}
}
