package com.tbf.cibercolegios.api.routes.web;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.tbf.cibercolegios.api.routes.web.utils.UserProfile;

import lombok.Getter;
import lombok.Setter;
import lombok.val;

@Component
@ViewScoped
@Setter
@Getter
public class HomeController implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final int OPCION_USUARIOS = 1;

	private static final int OPCION_ADMINISTRACION_RUTAS = 2;

	private static final int OPCION_SEGUIMIENTO_RUTAS = 3;

	@Value(WebSettings.PARAM_SESION_ID)
	private String sesionId;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private UserProfile preferences;

	private Integer opcion;

	@PostConstruct
	public void init() {
		preferences.init(getSesionId());
	}

	public String submit() {
		String result = null;
		
		if (this.getOpcion() != null) {
			switch (this.getOpcion()) {
			case OPCION_ADMINISTRACION_RUTAS:
				result = WebSettings.URL_RUTAS;
				break;
			case OPCION_USUARIOS:
				result = WebSettings.URL_USUARIOS;
				break;
			case OPCION_SEGUIMIENTO_RUTAS:
				result = WebSettings.URL_SEGUIMIENTO;
				break;
			default:
				break;
			}
		}

		if (result == null) {
			result = getCurrentUrl();
		}
		return result;
	}

	private String getCurrentUrl() {
		String requestURI = request.getRequestURI();
		String queryString = request.getQueryString();

		val result = StringUtils.isEmpty(queryString) ? requestURI : requestURI + "?" + queryString;
		return result;
	}
}
