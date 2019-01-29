package co.com.orbitta.cibercolegios.web.controller.seguimiento;

public interface SeguimientoConstantes {

	String QUERY_STRING_PARAM_REDIRECT = "faces-redirect=true";

	String URL_LIST = "seguimiento?" + QUERY_STRING_PARAM_REDIRECT;

	String QUERY_STRING_DEFAULT = QUERY_STRING_PARAM_REDIRECT + "&idSesion=%s";

	String URL_HOME = "home?" + QUERY_STRING_PARAM_REDIRECT;

}