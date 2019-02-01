package com.tbf.cibercolegios.api.routes.controllers.routes;

public interface RutasConstantes {

	String QUERY_STRING_PARAM_REDIRECT = "faces-redirect=true";

	String URL_LIST = "rutas?" + QUERY_STRING_PARAM_REDIRECT;

	String QUERY_STRING_DEFAULT = QUERY_STRING_PARAM_REDIRECT + "&idSesion=%s";

	String URL_HOME = "home?" + QUERY_STRING_PARAM_REDIRECT;

}