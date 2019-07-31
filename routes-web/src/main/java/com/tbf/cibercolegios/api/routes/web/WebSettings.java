package com.tbf.cibercolegios.api.routes.web;

public interface WebSettings {

	String QUERY_STRING_PARAM_REDIRECT = "faces-redirect=true";

	String QUERY_STRING_DEFAULT = QUERY_STRING_PARAM_REDIRECT + "&idSesion=%s";

	String URL_HOME = "home?" + QUERY_STRING_PARAM_REDIRECT;

	String URL_RUTAS = "rutas/default?" + QUERY_STRING_PARAM_REDIRECT;

	String URL_USUARIOS = "usuarios/default?" + QUERY_STRING_PARAM_REDIRECT;
	
	String URL_SEGUIMIENTO = "seguimiento/default?" + QUERY_STRING_PARAM_REDIRECT;
	
	String PARAM_SESION_ID = "#{request.getParameter('idSesion')}";

	String PARAM_SESION_URL_REDIRECCCION_SESION_FINALIZADA = "${sesion.url-redireccion-sesion-finalizada}";
	
	String PARAM_SESION_TIEMPO_MINIMO_RENOVACION_SESION = "${sesion.tiempo-minimo-renovacion-sesion}";
	
	String PARAM_FILES_HOME_LOCATION = "${files.location}";
	
	String PARAM_FILES_TEMPLATES_DIRECCIONES = "${files.templates.direcciones}";
}