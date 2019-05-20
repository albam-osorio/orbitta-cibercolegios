package com.tbf.cibercolegios.api.routes.controllers.util;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import lombok.Getter;
import lombok.Setter;
import lombok.val;

@SessionScope
@Component
@Setter
@Getter
public class UserPreferences implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	private String sesionId;

	private Integer usuarioId;

	private Integer institucionId;

	private Integer jornadaId;

	private Integer paisId;

	public void init(String sesionId) {
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

				setSesionId(id_sesion);
				setUsuarioId(id_usuario);
				setInstitucionId(id_institucion);
				setJornadaId(id_jornada);
				setPaisId(id_pais);
			}
		});
		
		/*
		setSesionId("70D35609");
		setUsuarioId(24411491);
		setInstitucionId(277);
		setJornadaId(1);
		setPaisId(169);
		*/
		if (getSesionId() == null) {
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
}
