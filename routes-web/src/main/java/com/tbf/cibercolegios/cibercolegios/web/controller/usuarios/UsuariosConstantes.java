package com.tbf.cibercolegios.cibercolegios.web.controller.usuarios;

public interface UsuariosConstantes {

	String QUERY_STRING_PARAM_REDIRECT = "faces-redirect=true";

	String URL_LIST = "usuarios?" + QUERY_STRING_PARAM_REDIRECT;

	String QUERY_STRING_DEFAULT = QUERY_STRING_PARAM_REDIRECT + "&idSesion=%s";

	String URL_HOME = "home?" + QUERY_STRING_PARAM_REDIRECT;

}