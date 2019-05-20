package com.tbf.cibercolegios.api.routes.controllers;

public interface WebSettings {

	String QUERY_STRING_PARAM_REDIRECT = "faces-redirect=true";

	String QUERY_STRING_DEFAULT = QUERY_STRING_PARAM_REDIRECT + "&idSesion=%s";

	String URL_HOME = "home?" + QUERY_STRING_PARAM_REDIRECT;

	String URL_RUTAS = "rutas/default?" + QUERY_STRING_PARAM_REDIRECT;

	String URL_USUARIOS = "usuarios/default?" + QUERY_STRING_PARAM_REDIRECT;
	
	String URL_SEGUIMIENTO = "seguimiento/default?" + QUERY_STRING_PARAM_REDIRECT;
}