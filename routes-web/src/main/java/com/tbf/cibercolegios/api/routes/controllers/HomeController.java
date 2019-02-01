package com.tbf.cibercolegios.api.routes.controllers;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.annotation.RequestScope;

import com.tbf.cibercolegios.api.routes.controllers.routes.RutasConstantes;
import com.tbf.cibercolegios.api.routes.controllers.tracking.SeguimientoConstantes;
import com.tbf.cibercolegios.api.routes.controllers.users.UsuariosConstantes;
import com.tbf.cibercolegios.api.routes.controllers.util.UserPreferences;

import lombok.Getter;
import lombok.Setter;
import lombok.val;

@Component
@RequestScope
@Setter
@Getter
public class HomeController implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String PARAM_SESION = "#{request.getParameter('idSesion')}";

	private static final int OPCION_USUARIOS = 1;

	private static final int OPCION_ADMINISTRACION_RUTAS = 2;

	private static final int OPCION_SEGUIMIENTO_RUTAS = 3;

	@Value(PARAM_SESION)
	private String sesionId;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private UserPreferences preferences;

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	private Integer opcion;

	@PostConstruct
	public void init() {
		if (preferences.getSesionId() == null) {
			initPreferrences(getSesionId());
		}
	}

	private void initPreferrences(String sesionId) {
		val sql = getSql();
		val paramMap = new HashMap<String, Object>();
		paramMap.put("idSesion", sesionId);

		jdbcTemplate.query(sql, paramMap, new RowCallbackHandler() {
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				String id_sesion = rs.getString("ID_SESION");
				int id_usuario = rs.getInt("ID_USUARIO");
				int id_institucion = rs.getInt("ID_INSTITUCION");
				int id_jornada = rs.getInt("ID_JORNADA");
				int id_pais = rs.getInt("ID_PAISES");

				preferences.setSesionId(id_sesion);
				preferences.setUsuarioId(id_usuario);
				preferences.setInstitucionId(id_institucion);
				preferences.setJornadaId(id_jornada);
				preferences.setPaisId(id_pais);
			}
		});

		if (preferences.getSesionId() == null) {
			throw new RuntimeException("No fue posible obtener los datos de la sesión con id=" + getSesionId());
		}
	}

	private String getSql() {
		// @formatter:off
		val result ="SELECT \n" + 
				"    a.id_sesion AS ID_SESION,\n" + 
				"    a.usuario.ID_USUARIO AS ID_USUARIO,\n" + 
				"    a.usuario.ID_INSTITUCION AS ID_INSTITUCION,\n" +
				"    b.ID_PAISES AS ID_PAISES,\n" +
				"    a.usuario.ID_JORNADA AS ID_JORNADA\n" + 
				"FROM CIBER.SESION a\n" + 
				"INNER JOIN CIBER.INSTITUCIONES b ON\n" + 
				"    b.id_institucion = a.usuario.id_institucion\n" + 
				"WHERE \n" + 
				"    a.id_sesion = :idSesion \n" + 
				"AND a.estado = 'ACT'\n" + 
				""; 
		// @formatter:on

		return result;
	}

	public String submit() {
		String result = null;
		if (this.getOpcion() != null) {
			switch (this.getOpcion()) {
			case OPCION_USUARIOS:
				result = UsuariosConstantes.URL_LIST;
				break;
			case OPCION_ADMINISTRACION_RUTAS:
				result = RutasConstantes.URL_LIST;
				break;
			case OPCION_SEGUIMIENTO_RUTAS:
				result = SeguimientoConstantes.URL_LIST;
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
