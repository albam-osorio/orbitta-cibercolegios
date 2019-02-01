package com.tbf.cibercolegios.cibercolegios.web.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class FacesMessages {
	public static void info(String detail) {
		info("Info", detail);
	}

	public static void warning(String detail) {
		warning("Warning!", detail);
	}

	public static void error(String detail) {
		error("Error!", detail);
	}

	public static void fatal(Exception e) {
		fatal("Fatal!", "" + e);
	}

	public static void info(String summary, String detail) {
		message(FacesMessage.SEVERITY_INFO, summary, detail);
	}

	public static void warning(String summary, String detail) {
		message(FacesMessage.SEVERITY_WARN, summary, detail);
	}

	public static void error(String summary, String detail) {
		message(FacesMessage.SEVERITY_ERROR, summary, detail);
	}

	public static void fatal(String summary, String detail) {
		message(FacesMessage.SEVERITY_FATAL, summary, detail);
	}

	private static void message(FacesMessage.Severity severity, String summary, String detail) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
	}
}
