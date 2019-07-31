package com.tbf.cibercolegios.api.ciber.services.impl;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.defaultString;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.tbf.cibercolegios.api.ciber.model.graph.CiudadDto;
import com.tbf.cibercolegios.api.ciber.model.graph.CursoDto;
import com.tbf.cibercolegios.api.ciber.model.graph.DepartamentoDto;
import com.tbf.cibercolegios.api.ciber.model.graph.GradoDto;
import com.tbf.cibercolegios.api.ciber.model.graph.InstitucionDto;
import com.tbf.cibercolegios.api.ciber.model.graph.NivelEducativoDto;
import com.tbf.cibercolegios.api.ciber.model.graph.ProgramaDto;
import com.tbf.cibercolegios.api.ciber.model.graph.TipoDocumentoDto;
import com.tbf.cibercolegios.api.ciber.model.graph.UsuarioDto;
import com.tbf.cibercolegios.api.ciber.model.graph.UsuarioPasajeroDto;
import com.tbf.cibercolegios.api.ciber.services.api.CiberService;
import com.tbf.cibercolegios.api.routes.model.graph.DireccionDto;

import lombok.val;

@Service
public class CiberServiceImpl implements CiberService {
	// @formatter:off
	private static final String FOTO_USUARIO_BY_USUARIO_ID = "" +
			"SELECT \r\n" +  
			"    a.FOTO.SOURCE.LOCALDATA AS FOTO \r\n" + 
			"FROM CIBER.USUARIOS a\r\n" + 
			"WHERE 1 = 1\r\n" + 
			"AND a.ID_USUARIO      = :ID_USUARIO \r\n" + 
			"";

	private static final String USUARIOS_BY_USUARIO_ID_IN = "" +
			"SELECT \r\n" + 
			"  	a.ID_USUARIO, a.LOGIN, a.ID_TIPOID, a.NUMERO_ID, a.NOMBRE, a.APELLIDO, \r\n" + 
			"   CASE WHEN a.FOTO IS NOT NULL THEN 1 ELSE 0 END AS TIENE_FOTO, \r\n" + 
			"   b.DESCRIPCION AS TIPO_IDENTIFICACION \r\n" + 				
			"FROM CIBER.USUARIOS a \r\n" +
			"INNER JOIN CIBER.TIPO_ID b ON \r\n" + 
			"    b.ID_TIPOID = a.ID_TIPOID\r\n" + 
			"WHERE 1 = 1 \r\n" + 
			"AND a.ID_USUARIO IN (:ID_USUARIO)" + 
			"";
	
	private static final String USUARIOS_BY_INSTITUCION_ID_AND_PERFIL_ID_AND_USUARIO_ID = "" 
			+ "SELECT \r\n"
			+ "  b.ID_USUARIO, b.LOGIN, b.ID_TIPOID, b.NUMERO_ID, b.NOMBRE, b.APELLIDO, \r\n"
			+ "  CASE WHEN b.FOTO IS NOT NULL THEN 1 ELSE 0 END AS TIENE_FOTO, \r\n"			
			+ "  c.DESCRIPCION AS TIPO_IDENTIFICACION \r\n"
			+ "FROM CIBER.PERFIL a \r\n" 
			+ "INNER JOIN CIBER.USUARIOS b ON \r\n"
			+ "    b.ID_USUARIO = a.ID_USUARIO \r\n" 
			+ "AND b.BORRADO = 'N' \r\n"
			+ "INNER JOIN CIBER.TIPO_ID c ON \r\n"
			+ "    c.ID_TIPOID = b.ID_TIPOID\r\n" 
			+ "WHERE 1 = 1 \r\n"
			+ "AND a.ID_INSTITUCION = :ID_INSTITUCION \r\n" 
			+ "AND a.ID_PERFIL 		= :ID_PERFIL \r\n"
			+ "AND a.ACTIVO 		= 1 \r\n" 
			+ "AND a.ID_USUARIO 	= :ID_USUARIO \r\n" 
			+ "";
	
	private static final String USUARIOS_BY_INSTITUCION_ID_AND_PERFIL_ID_AND_IDENTIFICACION = "" 
			+ "SELECT \r\n"
			+ "  b.ID_USUARIO, b.LOGIN, b.ID_TIPOID, b.NUMERO_ID, b.NOMBRE, b.APELLIDO, \r\n"
			+ "  CASE WHEN b.FOTO IS NOT NULL THEN 1 ELSE 0 END AS TIENE_FOTO, \r\n"			
			+ "  c.DESCRIPCION AS TIPO_IDENTIFICACION \r\n"
			+ "FROM CIBER.PERFIL a \r\n" 
			+ "INNER JOIN CIBER.USUARIOS b ON \r\n"
			+ "    b.ID_USUARIO = a.ID_USUARIO \r\n" 
			+ "AND b.BORRADO = 'N' \r\n"
			+ "INNER JOIN CIBER.TIPO_ID c ON \r\n"
			+ "    c.ID_TIPOID = b.ID_TIPOID\r\n" 
			+ "WHERE 1 = 1 \r\n"
			+ "AND a.ID_INSTITUCION = :ID_INSTITUCION \r\n" 
			+ "AND a.ID_PERFIL 		= :ID_PERFIL \r\n"
			+ "AND a.ACTIVO 		= 1 \r\n"
			+ "AND b.ID_TIPOID 		= :ID_TIPOID \r\n" 
			+ "AND b.NUMERO_ID 		= :NUMERO_ID \r\n" 
			+ "";

	// -----------------------------------------------------------------------------------
	// --
	// -----------------------------------------------------------------------------------
	private static final String USUARIOS_ACUDIENTE_BY_INSTITUCION_ID_AND_USUARIO_PASAJERO_ID = "" 
			+ "SELECT \r\n"
			+ "    d.ID_USUARIO, d.LOGIN, d.ID_TIPOID, d.NUMERO_ID, d.NOMBRE, d.APELLIDO,  \r\n"
			+ "    CASE WHEN d.FOTO IS NOT NULL THEN 1 ELSE 0 END AS TIENE_FOTO, \r\n"			
			+ "    e.DESCRIPCION AS TIPO_IDENTIFICACION \r\n"
			+ "FROM CIBER.PERFIL a  \r\n" 
			+ "INNER JOIN CIBER.USUARIOS b ON  \r\n"
			+ "    b.ID_USUARIO = a.ID_USUARIO\r\n" 
			+ "AND b.BORRADO = 'N' \r\n"
			+ "INNER JOIN CIBER.RELACION_X_USUARIO c ON\r\n"
			+ "    c.ID_USUARIO_EST = a.ID_USUARIO\r\n" 
			+ "INNER JOIN CIBER.USUARIOS d ON\r\n"
			+ "    d.ID_USUARIO = c.ID_USUARIO_ACU\r\n" 
			+ "AND d.BORRADO = 'N' \r\n"
			+ "INNER JOIN CIBER.TIPO_ID e ON \r\n"
			+ "    e.ID_TIPOID = d.ID_TIPOID\r\n" 
			+ "WHERE 1 = 1  \r\n"
			+ "AND a.ID_INSTITUCION = :ID_INSTITUCION \r\n" 
			+ "AND a.ID_PERFIL = :ID_PERFIL  \r\n"
			+ "AND a.ACTIVO = 1 \r\n" 
			+ "AND a.ID_USUARIO = :ID_USUARIO \r\n" 
			+ "";

	private static final String USUARIO_ACUDIENTE_BY_USUARIO_ID = "" 
			+ "SELECT\r\n"
			+ "   b.ID_USUARIO, b.LOGIN, b.ID_TIPOID, b.NUMERO_ID, b.NOMBRE, b.APELLIDO,  \r\n"
			+ "   CASE WHEN b.FOTO IS NOT NULL THEN 1 ELSE 0 END AS TIENE_FOTO, \r\n"			
			+ "   c.DESCRIPCION AS TIPO_IDENTIFICACION \r\n"
			+ "FROM ACUDIENTES a  \r\n" 
			+ "INNER JOIN CIBER.USUARIOS b ON  \r\n" 
			+ "    b.ID_USUARIO = a.ID_USUARIO\r\n"
			+ "INNER JOIN CIBER.TIPO_ID c ON \r\n"
			+ "    c.ID_TIPOID = b.ID_TIPOID\r\n" 
			+ "WHERE 1 = 1  \r\n" 
			+ "AND a.ID_USUARIO = :ID_USUARIO";	

	// -----------------------------------------------------------------------------------
	// --
	// -----------------------------------------------------------------------------------
	private static final String USUARIO_PASAJERO_BY_INSTITUCION_ID_AND_USUARIO_ID = "" 
			+ "SELECT \r\n"
			+ "    c.ID_USUARIO, c.LOGIN, c.ID_TIPOID, c.NUMERO_ID, c.NOMBRE, c.APELLIDO, \r\n"
			+ "    CASE WHEN c.FOTO IS NOT NULL THEN 1 ELSE 0 END AS TIENE_FOTO, \r\n" 						
			+ "    d.DESCRIPCION AS TIPO_IDENTIFICACION,\r\n" 
			+ "    c.TEL_CASA,\r\n" 
			+ "    c.DIR_CASA,\r\n"
			+ "    c.EMAIL,\r\n" 
			+ "    e.ID_PASAJERO\r\n" 
			+ "FROM  CIBER.USUARIOS_X_GRUPO a\r\n"
			+ "INNER JOIN CIBER.PERFIL b ON\r\n" 
			+ "    b.ID_INSTITUCION = a.ID_INSTITUCION \r\n"
			+ "AND b.ID_JORNADA     = a.ID_JORNADA\r\n" 
			+ "AND b.ID_USUARIO     = a.ID_USUARIO\r\n"
			+ "AND b.ID_PERFIL      = :ID_PERFIL\r\n" 
			+ "AND b.ACTIVO         = 1\r\n"
			+ "INNER JOIN CIBER.USUARIOS c ON\r\n" 
			+ "    c.ID_USUARIO      = a.ID_USUARIO\r\n"
			+ "AND c.BORRADO         = 'N'\r\n" 
			+ "INNER JOIN CIBER.TIPO_ID d ON \r\n"
			+ "    d.ID_TIPOID = c.ID_TIPOID\r\n" 
			+ "LEFT OUTER JOIN PASAJEROS e ON\r\n"
			+ "    e.ID_USUARIO = a.ID_USUARIO\r\n" 
			+ "AND e.ID_INSTITUCION = a.ID_INSTITUCION\r\n" 
			+ "WHERE 1 = 1\r\n"
			+ "AND a.ID_INSTITUCION  = :ID_INSTITUCION \r\n" 
			+ "AND a.ID_USUARIO      = :ID_USUARIO \r\n" 
			+ "";
	
	private static final String USUARIOS_PASAJERO_BY_INSTITUCION_ID_AND_PASAJERO_ID_IN = "" 
			+ "SELECT\r\n"
			+ "    b.ID_USUARIO, b.LOGIN, b.ID_TIPOID, b.NUMERO_ID, b.NOMBRE, b.APELLIDO,  \r\n"
			+ "    CASE WHEN b.FOTO IS NOT NULL THEN 1 ELSE 0 END AS TIENE_FOTO, \r\n" 			
			+ "    c.DESCRIPCION AS TIPO_IDENTIFICACION, \r\n" 
			+ "    b.TEL_CASA, \r\n" 
			+ "    b.DIR_CASA, \r\n"
			+ "    b.EMAIL, \r\n" 
			+ "    a.ID_PASAJERO \r\n" 
			+ "FROM PASAJEROS a \r\n"
			+ "INNER JOIN CIBER.USUARIOS b ON \r\n" 
			+ "    b.ID_USUARIO      = a.ID_USUARIO \r\n"
			+ "AND b.BORRADO         = 'N' \r\n" 
			+ "INNER JOIN CIBER.TIPO_ID c ON  \r\n"
			+ "    c.ID_TIPOID = b.ID_TIPOID \r\n" 
			+ "WHERE 1 = 1 \r\n" 
			+ "AND a.ID_INSTITUCION = :ID_INSTITUCION  \r\n"
			+ "AND a.ID_PASAJERO IN (:ID_PASAJERO)" 
			+ "";

	// -----------------------------------------------------------------------------------
	// --
	// -----------------------------------------------------------------------------------
	private static final String USUARIOS_ESTUDIANTES_BY_CURSO = "" 
			+ "SELECT \r\n"
			+ "    c.ID_USUARIO, c.LOGIN, c.ID_TIPOID, c.NUMERO_ID, c.NOMBRE, c.APELLIDO, "
			+ "    CASE WHEN c.FOTO IS NOT NULL THEN 1 ELSE 0 END AS TIENE_FOTO, \r\n" 
			+ "    d.DESCRIPCION AS TIPO_IDENTIFICACION, \r\n"
			+ "    a.ID_NEDUCATIVO, a.ID_PROGRAMA, a.ID_GRADO\r\n"
			+ "FROM  CIBER.USUARIOS_X_GRUPO a\r\n" 
			+ "INNER JOIN CIBER.PERFIL b ON\r\n"
			+ "    b.ID_INSTITUCION = a.ID_INSTITUCION \r\n" 
			+ "AND b.ID_JORNADA     = a.ID_JORNADA\r\n"
			+ "AND b.ID_USUARIO     = a.ID_USUARIO\r\n" 
			+ "AND b.ID_PERFIL      = :ID_PERFIL\r\n"
			+ "AND b.ACTIVO         = 1\r\n" 
			+ "INNER JOIN CIBER.USUARIOS c ON\r\n"
			+ "    c.ID_USUARIO      = a.ID_USUARIO\r\n" 
			+ "AND c.BORRADO         = 'N'\r\n" 
			+ "INNER JOIN CIBER.TIPO_ID d ON \r\n" 
			+ "    d.ID_TIPOID = c.ID_TIPOID\r\n"
			+ "WHERE 1 = 1\r\n"
			+ "AND a.ID_INSTITUCION 	= :ID_INSTITUCION \r\n" 
			+ "AND a.ID_JORNADA 		= :ID_JORNADA \r\n"
			+ "AND a.ID_NEDUCATIVO 		= :ID_NEDUCATIVO \r\n" 
			+ "AND a.ID_PROGRAMA 		= :ID_PROGRAMA \r\n"
			+ "AND a.ID_GRADO    		= :ID_GRADO \r\n" 
			+ "AND a.ID_GRUPOGRAD    	= :ID_CURSO \r\n" 
			+ "ORDER BY \r\n"
			+ "    c.APELLIDO, c.NOMBRE\r\n" 
			+ "";
	
	private static final String USUARIOS_PASAJEROS_BY_GRADO = "" 
			+ "SELECT \r\n"
			+ "    c.ID_USUARIO, c.LOGIN, c.ID_TIPOID, c.NUMERO_ID, c.NOMBRE, c.APELLIDO, \r\n"
			+ "    CASE WHEN c.FOTO IS NOT NULL THEN 1 ELSE 0 END AS TIENE_FOTO, \r\n" 
			+ "    d.DESCRIPCION AS TIPO_IDENTIFICACION, \r\n"
			+ "    c.TEL_CASA, \r\n" 
			+ "    c.DIR_CASA, \r\n"
			+ "    c.EMAIL, \r\n" 
			+ "    e.ID_PASAJERO, \r\n" 
			+ "    a.ID_NEDUCATIVO, a.ID_PROGRAMA, a.ID_GRADO\r\n"
			+ "FROM  CIBER.USUARIOS_X_GRUPO a\r\n" 
			+ "INNER JOIN CIBER.PERFIL b ON\r\n"
			+ "    b.ID_INSTITUCION = a.ID_INSTITUCION \r\n" 
			+ "AND b.ID_JORNADA     = a.ID_JORNADA\r\n"
			+ "AND b.ID_USUARIO     = a.ID_USUARIO\r\n" 
			+ "AND b.ID_PERFIL      = :ID_PERFIL\r\n"
			+ "AND b.ACTIVO         = 1\r\n" 
			+ "INNER JOIN CIBER.USUARIOS c ON\r\n"
			+ "    c.ID_USUARIO      = a.ID_USUARIO\r\n" 
			+ "AND c.BORRADO         = 'N'\r\n" 
			+ "INNER JOIN CIBER.TIPO_ID d ON \r\n" 
			+ "    d.ID_TIPOID = c.ID_TIPOID\r\n"
			+ "INNER JOIN PASAJEROS e ON \r\n" 
			+ "    e.ID_INSTITUCION = a.ID_INSTITUCION \r\n" 
			+ "AND e.ID_USUARIO     = a.ID_USUARIO\r\n" 
			+ "WHERE 1 = 1\r\n"
			+ "AND a.ID_INSTITUCION 	= :ID_INSTITUCION \r\n" 
			+ "AND a.ID_JORNADA 		= :ID_JORNADA \r\n"
			+ "AND a.ID_NEDUCATIVO 		= :ID_NEDUCATIVO \r\n" 
			+ "AND a.ID_PROGRAMA 		= :ID_PROGRAMA \r\n"
			+ "AND a.ID_GRADO    		= :ID_GRADO \r\n" 
			+ "ORDER BY \r\n"
			+ "    c.APELLIDO, c.NOMBRE\r\n" 
			+ "";	
	// @formatter:on

	private static final Integer PERFIL_ESTUDIANTE = 2;
	private static final Integer PERFIL_MONITOR = 6;

	private static final String PARAM_ID_INSTITUCION = "ID_INSTITUCION";
	private static final String PARAM_ID_USUARIO = "ID_USUARIO";
	private static final String PARAM_ID_PERFIL = "ID_PERFIL";
	private static final String PARAM_ID_TIPO_IDENTIFICACION = "ID_TIPOID";
	private static final String PARAM_NUMERO_IDENTIFICACION = "NUMERO_ID";
	private static final String PARAM_ID_PASAJERO = "ID_PASAJERO";

	private static final String PARAM_ID_PAIS = "ID_PAIS";
	private static final String PARAM_ID_DEPARTAMENTO = "ID_ESTADO";
	private static final String PARAM_ID_CIUDAD = "ID_CIUDAD";
	private static final String PARAM_NOMBRES = "NOMBRES";

	private static final String PARAM_ID_NIVEL_EDUCATIVO = "ID_NEDUCATIVO";
	private static final String PARAM_ID_JORNADA = "ID_JORNADA";
	private static final String PARAM_ID_PROGRAMA = "ID_PROGRAMA";
	private static final String PARAM_ID_GRADO = "ID_GRADO";
	private static final String PARAM_ID_CURSO = "ID_CURSO";

	private static final String ID_USUARIO = "ID_USUARIO";
	private static final String LOGIN = "LOGIN";
	private static final String ID_TIPO_IDENTIFICACION = "ID_TIPOID";
	private static final String TIPO_IDENTIFICACION = "TIPO_IDENTIFICACION";
	private static final String NUMERO_IDENTIFICACION = "NUMERO_ID";
	private static final String NOMBRE = "NOMBRE";
	private static final String APELLIDO = "APELLIDO";
	private static final String TIENE_FOTO = "TIENE_FOTO";
	private static final String FOTO = "FOTO";

	private static final String ID_PASAJERO = "ID_PASAJERO";

	private static final String ID_PAIS = "ID_PAISES";
	private static final String ID_DEPARTAMENTO = "ID_ESTADO";
	private static final String ID_CIUDAD = "ID_CIUDAD";
	private static final String DESCRIPCION = "DESCRIPCION";

	private static final String ID_PROGRAMA = "ID_PROGRAMA";
	private static final String ID_GRADO = "ID_GRADO";
	private static final String ID_GRUPO = "ID_GRUPOGRAD";

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	protected NamedParameterJdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public Optional<byte[]> findImagenUsuarioByUsuarioId(int usuarioId) {
		val paramMap = new HashMap<String, Object>();
		paramMap.put(PARAM_ID_USUARIO, usuarioId);

		val result = getJdbcTemplate().query(FOTO_USUARIO_BY_USUARIO_ID, paramMap, (rs, rowNum) -> {
			byte[] result = null;
			Blob blob = rs.getBlob(FOTO);
			if (blob != null) {
				int blobLength = (int) blob.length();
				result = blob.getBytes(1, blobLength);
				blob.free();
			}

			return result;
		});

		return result.stream().findFirst();
	}

	// -----------------------------------------------------------------------------------
	// --
	// -----------------------------------------------------------------------------------
	@Override
	public Optional<UsuarioDto> findUsuarioByUsuarioId(int usuarioId) {
		val list = new ArrayList<Integer>();
		list.add(usuarioId);

		return findAllUsuariosByUsuarioIdIn(list).stream().findFirst();
	}

	@Override
	public List<UsuarioDto> findAllUsuariosByUsuarioIdIn(List<Integer> usuariosId) {
		val paramMap = new HashMap<String, Object>();
		paramMap.put(PARAM_ID_USUARIO, usuariosId);

		val result = getJdbcTemplate().query(USUARIOS_BY_USUARIO_ID_IN, paramMap, getUsuarioRowMapper());

		return result;
	}

	// -----------------------------------------------------------------------------------
	// --
	// -----------------------------------------------------------------------------------
	@Override
	public Optional<UsuarioDto> findUsuarioEstudianteByInstitucionIdAndUsuarioId(int institucionId, int usuarioId) {
		return findUsuarioByInstitucionIdAndPerfilIdAndUsuarioId(institucionId, PERFIL_ESTUDIANTE, usuarioId);
	}

	@Override
	public Optional<UsuarioDto> findUsuarioEstudianteByInstitucionIdAndIdentificacion(int institucionId,
			int tipoIdentificacion, String numeroIdentificacion) {
		return findUsuarioByInstitucionIdAndPerfilIdAndIdentificacion(institucionId, PERFIL_ESTUDIANTE,
				tipoIdentificacion, numeroIdentificacion);
	}

	// -----------------------------------------------------------------------------------
	// --
	// -----------------------------------------------------------------------------------
	@Override
	public Optional<UsuarioDto> findUsuarioMonitorByInstitucionIdAndUsuarioId(int institucionId, int usuarioId) {
		return findUsuarioByInstitucionIdAndPerfilIdAndUsuarioId(institucionId, PERFIL_MONITOR, usuarioId);
	}

	@Override
	public Optional<UsuarioDto> findUsuarioMonitorByInstitucionIdAndIdentificacion(int institucionId,
			int tipoIdentificacion, String numeroIdentificacion) {
		return findUsuarioByInstitucionIdAndPerfilIdAndIdentificacion(institucionId, PERFIL_MONITOR, tipoIdentificacion,
				numeroIdentificacion);
	}

	// -----------------------------------------------------------------------------------
	// --
	// -----------------------------------------------------------------------------------
	private Optional<UsuarioDto> findUsuarioByInstitucionIdAndPerfilIdAndUsuarioId(int institucionId, int perfilId,
			int usuarioId) {
		val paramMap = new HashMap<String, Object>();
		paramMap.put(PARAM_ID_INSTITUCION, institucionId);
		paramMap.put(PARAM_ID_PERFIL, perfilId);
		paramMap.put(PARAM_ID_USUARIO, usuarioId);

		val result = getJdbcTemplate().query(USUARIOS_BY_INSTITUCION_ID_AND_PERFIL_ID_AND_USUARIO_ID, paramMap,
				getUsuarioRowMapper());
		return result.stream().findFirst();
	}

	private Optional<UsuarioDto> findUsuarioByInstitucionIdAndPerfilIdAndIdentificacion(int institucionId, int perfilId,
			int tipoIdentificacion, String numeroIdentificacion) {
		val paramMap = new HashMap<String, Object>();
		paramMap.put(PARAM_ID_INSTITUCION, institucionId);
		paramMap.put(PARAM_ID_PERFIL, perfilId);
		paramMap.put(PARAM_ID_TIPO_IDENTIFICACION, tipoIdentificacion);
		paramMap.put(PARAM_NUMERO_IDENTIFICACION, numeroIdentificacion);

		val result = getJdbcTemplate().query(USUARIOS_BY_INSTITUCION_ID_AND_PERFIL_ID_AND_IDENTIFICACION, paramMap,
				getUsuarioRowMapper());
		return result.stream().findFirst();
	}

	private RowMapper<UsuarioDto> getUsuarioRowMapper() {
		return (rs, rowNum) -> {
			UsuarioDto r = new UsuarioDto();

			r.setId(rs.getInt(ID_USUARIO));
			r.setLogin(rs.getString(LOGIN));
			r.setTipoIdentificacionId(rs.getInt(ID_TIPO_IDENTIFICACION));
			r.setTipoIdentificacion(rs.getString(TIPO_IDENTIFICACION));
			r.setNumeroIdentificacion(rs.getString(NUMERO_IDENTIFICACION));
			r.setNombre(rs.getString(NOMBRE));
			r.setApellido(rs.getString(APELLIDO));
			r.setTieneFoto(rs.getBoolean(TIENE_FOTO));

			return r;
		};
	}

	// -----------------------------------------------------------------------------------
	// --
	// -----------------------------------------------------------------------------------
	@Override
	public Optional<UsuarioDto> findUsuarioAcudienteByUsuarioId(int usuarioId) {
		val paramMap = new HashMap<String, Object>();
		paramMap.put(PARAM_ID_USUARIO, usuarioId);

		return getJdbcTemplate().query(USUARIO_ACUDIENTE_BY_USUARIO_ID, paramMap, getUsuarioRowMapper()).stream()
				.findFirst();
	}

	@Override
	public List<UsuarioDto> findUsuariosAcudientesByInstitucionIdAndUsuarioPasajeroId(int institucionId,
			int usuarioId) {
		val paramMap = new HashMap<String, Object>();
		paramMap.put(PARAM_ID_INSTITUCION, institucionId);
		paramMap.put(PARAM_ID_PERFIL, PERFIL_ESTUDIANTE);
		paramMap.put(PARAM_ID_USUARIO, usuarioId);

		val result = getJdbcTemplate().query(USUARIOS_ACUDIENTE_BY_INSTITUCION_ID_AND_USUARIO_PASAJERO_ID, paramMap,
				getUsuarioRowMapper());
		return result;
	}

	// -----------------------------------------------------------------------------------
	// --
	// -----------------------------------------------------------------------------------
	@Override
	public Optional<UsuarioPasajeroDto> findUsuarioPasajeroByInstitucionIdAndUsuarioIdAsUsuarioPasajeroDto(
			int institucionId, int usuarioId) {
		val paramMap = new HashMap<String, Object>();
		paramMap.put(PARAM_ID_INSTITUCION, institucionId);
		paramMap.put(PARAM_ID_USUARIO, usuarioId);
		paramMap.put(PARAM_ID_PERFIL, PERFIL_ESTUDIANTE);

		val result = getJdbcTemplate().query(USUARIO_PASAJERO_BY_INSTITUCION_ID_AND_USUARIO_ID, paramMap,
				getUsuarioPasajeroRowMapper());

		return result.stream().findFirst();
	}

	@Override
	public List<UsuarioPasajeroDto> findAllUsuariosPasajerosByInstitucionIdAndPasajeroIdInAsUsuarioPasajeroDto(
			int institucionId, List<Integer> pasajerosId) {
		val ids = new ArrayList<Integer>();
		ids.addAll(pasajerosId);
		if (ids.isEmpty()) {
			ids.add(null);
		}

		val paramMap = new HashMap<String, Object>();
		paramMap.put(PARAM_ID_INSTITUCION, institucionId);
		paramMap.put(PARAM_ID_PASAJERO, ids);

		val result = getJdbcTemplate().query(USUARIOS_PASAJERO_BY_INSTITUCION_ID_AND_PASAJERO_ID_IN, paramMap,
				getUsuarioPasajeroRowMapper());

		return result;
	}

	private RowMapper<UsuarioPasajeroDto> getUsuarioPasajeroRowMapper() {
		return (rs, rowNum) -> {
			UsuarioPasajeroDto r = new UsuarioPasajeroDto();
			r.setId(rs.getInt(ID_USUARIO));
			r.setLogin(defaultString(rs.getString(LOGIN)));
			r.setTipoIdentificacionId(rs.getInt(ID_TIPO_IDENTIFICACION));
			r.setTipoIdentificacion(rs.getString(TIPO_IDENTIFICACION));
			r.setNumeroIdentificacion(rs.getString(NUMERO_IDENTIFICACION));
			r.setNombre(rs.getString(NOMBRE));
			r.setApellido(rs.getString(APELLIDO));
			r.setTieneFoto(rs.getBoolean(TIENE_FOTO));

			r.setTelefonoDomicilio(rs.getString("TEL_CASA"));
			r.setDireccionDomicilio(rs.getString("DIR_CASA"));
			r.setEmail(defaultString(rs.getString("EMAIL")));

			val pasajeroId = rs.getBigDecimal(ID_PASAJERO);
			r.setPasajeroId(pasajeroId != null ? pasajeroId.intValue() : null);

			return r;
		};
	}

	// -----------------------------------------------------------------------------------
	// --
	// -----------------------------------------------------------------------------------
	@Override
	public List<DepartamentoDto> findAllDepartamentosByPaisId(int paisId) {
		val sql = "SELECT ID_PAISES, ID_ESTADO, DESCRIPCION FROM CIBER.ESTADOS WHERE ID_PAISES = :ID_PAIS ORDER BY DESCRIPCION";

		val paramMap = new HashMap<String, Object>();
		paramMap.put(PARAM_ID_PAIS, paisId);

		val result = getJdbcTemplate().query(sql, paramMap, rowMapperDepartamento());

		return result;
	}

	@Override
	public List<DepartamentoDto> findAllDepartamentosByNombres(int paisId, List<String> departamentosNombre) {
		val sql = "SELECT ID_PAISES, ID_ESTADO, DESCRIPCION FROM CIBER.ESTADOS WHERE ID_PAISES = :ID_PAIS AND UPPER(DESCRIPCION) IN (:NOMBRES) ORDER BY DESCRIPCION";
		val nombres = departamentosNombre.stream().filter(a -> a != null).map(a -> a.toUpperCase()).distinct()
				.collect(toList());

		val paramMap = new HashMap<String, Object>();
		paramMap.put(PARAM_ID_PAIS, paisId);
		paramMap.put(PARAM_NOMBRES, nombres);

		val result = getJdbcTemplate().query(sql, paramMap, rowMapperDepartamento());
		return result;
	}

	@Override
	public Optional<DepartamentoDto> findDepartamentoByDepartamentoId(int paisId, int departamentoId) {
		val sql = "SELECT ID_PAISES, ID_ESTADO, DESCRIPCION FROM CIBER.ESTADOS WHERE ID_PAISES = :ID_PAIS AND ID_ESTADO = :ID_ESTADO";

		val paramMap = new HashMap<String, Object>();
		paramMap.put(PARAM_ID_PAIS, paisId);
		paramMap.put(PARAM_ID_DEPARTAMENTO, departamentoId);

		val result = getJdbcTemplate().queryForObject(sql, paramMap, rowMapperDepartamento());
		return Optional.of(result);
	}

	private RowMapper<DepartamentoDto> rowMapperDepartamento() {
		return (rs, rowNum) -> {
			DepartamentoDto r = new DepartamentoDto();

			r.setPaisId(rs.getInt(ID_PAIS));
			r.setDepartamentoId(rs.getInt(ID_DEPARTAMENTO));
			r.setNombre(rs.getString(DESCRIPCION));

			return r;
		};
	}

	@Override
	public List<CiudadDto> findAllCiudadesByDepartamentoId(int paisId, int departamentoId) {
		val sql = "SELECT ID_PAISES, ID_ESTADO, ID_CIUDAD, DESCRIPCION FROM CIBER.CIUDADES WHERE ID_PAISES = :ID_PAIS AND ID_ESTADO = :ID_ESTADO ORDER BY DESCRIPCION";

		val paramMap = new HashMap<String, Object>();
		paramMap.put(PARAM_ID_PAIS, paisId);
		paramMap.put(PARAM_ID_DEPARTAMENTO, departamentoId);

		val result = getJdbcTemplate().query(sql, paramMap, rowMapperCiudad());

		return result;
	}

	@Override
	public List<CiudadDto> findAllCiudadesByNombres(int paisId, int departamentoId, List<String> ciudadesNombre) {
		val sql = "SELECT ID_PAISES, ID_ESTADO, ID_CIUDAD, DESCRIPCION FROM CIBER.CIUDADES WHERE ID_PAISES = :ID_PAIS AND ID_ESTADO = :ID_ESTADO AND UPPER(DESCRIPCION) IN (:NOMBRES) ORDER BY DESCRIPCION";

		val nombres = ciudadesNombre.stream().filter(a -> a != null).map(a -> a.toUpperCase()).distinct()
				.collect(toList());

		val paramMap = new HashMap<String, Object>();
		paramMap.put(PARAM_ID_PAIS, paisId);
		paramMap.put(PARAM_ID_DEPARTAMENTO, departamentoId);
		paramMap.put(PARAM_NOMBRES, nombres);

		val result = getJdbcTemplate().query(sql, paramMap, rowMapperCiudad());
		return result;
	}

	@Override
	public List<CiudadDto> findAllCiudadesByIdIn(int paisId, int departamentoId, Collection<Integer> ciudadesId) {
		val sql = "SELECT ID_PAISES, ID_ESTADO, ID_CIUDAD, DESCRIPCION FROM CIBER.CIUDADES WHERE ID_PAISES = :ID_PAIS AND ID_ESTADO = :ID_ESTADO AND ID_CIUDAD IN (:ID_CIUDAD) ORDER BY DESCRIPCION";

		val list = new ArrayList<Integer>();
		list.addAll(ciudadesId);
		if (list.isEmpty()) {
			list.add(null);
		}

		val paramMap = new HashMap<String, Object>();
		paramMap.put(PARAM_ID_PAIS, paisId);
		paramMap.put(PARAM_ID_DEPARTAMENTO, departamentoId);
		paramMap.put(PARAM_ID_CIUDAD, list);

		val result = getJdbcTemplate().query(sql, paramMap, rowMapperCiudad());
		return result;
	}

	@Override
	public Optional<CiudadDto> findCiudadByCiudadId(int paisId, int departamentoId, int ciudadId) {
		val sql = "SELECT ID_PAISES,ID_ESTADO,ID_CIUDAD,DESCRIPCION FROM CIBER.CIUDADES WHERE ID_PAISES = :ID_PAIS AND ID_ESTADO = :ID_ESTADO AND ID_CIUDAD = :ID_CIUDAD";

		val paramMap = new HashMap<String, Object>();
		paramMap.put(PARAM_ID_PAIS, paisId);
		paramMap.put(PARAM_ID_DEPARTAMENTO, departamentoId);
		paramMap.put(PARAM_ID_CIUDAD, ciudadId);

		val result = getJdbcTemplate().queryForObject(sql, paramMap, rowMapperCiudad());
		return Optional.of(result);
	}

	private RowMapper<CiudadDto> rowMapperCiudad() {
		return (rs, rowNum) -> {
			CiudadDto r = new CiudadDto();

			r.setPaisId(rs.getInt(ID_PAIS));
			r.setDepartamentoId(rs.getInt(ID_DEPARTAMENTO));
			r.setCiudadId(rs.getInt(ID_CIUDAD));
			r.setNombre(rs.getString(DESCRIPCION));

			return r;
		};
	}

	@Override
	public Map<DepartamentoDto, List<CiudadDto>> findCiudadesByDirecciones(List<DireccionDto> direcciones) {
		val ciudadesId = direcciones.stream().collect(groupingBy(DireccionDto::getPaisId,
				groupingBy(DireccionDto::getDepartamentoId, groupingBy(DireccionDto::getCiudadId))));

		val result = new HashMap<DepartamentoDto, List<CiudadDto>>();

		ciudadesId.entrySet().forEach(pais -> {
			pais.getValue().entrySet().forEach(departamento -> {
				val opd = findDepartamentoByDepartamentoId(pais.getKey(), departamento.getKey());
				if (opd.isPresent()) {
					List<CiudadDto> ciudades = findAllCiudadesByIdIn(pais.getKey(), departamento.getKey(),
							departamento.getValue().keySet());
					result.put(opd.get(), ciudades);
				}
			});
		});

		return result;
	}

	// -----------------------------------------------------------------------------------
	// --
	// -----------------------------------------------------------------------------------
	@Override
	public List<TipoDocumentoDto> findAllTiposDocumento() {
		val sql = "SELECT ID_TIPOID,ABREVIATURA,DESCRIPCION FROM CIBER.TIPO_ID";

		val paramMap = new HashMap<String, Object>();

		val result = getJdbcTemplate().query(sql, paramMap, (rs, rowNum) -> {
			TipoDocumentoDto r = new TipoDocumentoDto();

			r.setId(rs.getInt(PARAM_ID_TIPO_IDENTIFICACION));
			r.setAbreviatura(rs.getString("ABREVIATURA"));
			r.setDescripcion(rs.getString(DESCRIPCION));

			return r;
		});

		return result;
	}

	@Override
	public Optional<InstitucionDto> findInstitucionById(int institucionId) {
		val sql = "SELECT ID_INSTITUCION, NOMBRE FROM CIBER.INSTITUCIONES WHERE ID_INSTITUCION = :ID_INSTITUCION";

		val paramMap = new HashMap<String, Object>();
		paramMap.put(PARAM_ID_INSTITUCION, institucionId);

		val result = getJdbcTemplate().queryForObject(sql, paramMap, (rs, rowNum) -> {
			InstitucionDto r = new InstitucionDto();

			r.setId(rs.getInt(ID_TIPO_IDENTIFICACION));
			r.setNombre(rs.getString(NOMBRE));

			return r;
		});
		return Optional.of(result);
	}

	@Override
	public List<NivelEducativoDto> findAllNivelesEducativosByInstitucionId(int institucionId) {
		// @formatter:off
		val sql = "SELECT \r\n" + 
				"    b.ID_NEDUCATIVO,b.DESCRIPCION,a.ID_JORNADA \r\n" + 
				"FROM CIBER.NIVEL_X_INSTITUCION a \r\n" + 
				"INNER JOIN CIBER.NIVEL_EDUCATIVO b ON \r\n" + 
				"    b.ID_NEDUCATIVO = a.ID_NEDUCATIVO \r\n" + 
				"WHERE \r\n" + 
				"    a.ID_INSTITUCION = :ID_INSTITUCION \r\n" + 
				"ORDER BY \r\n" + 
				"    b.ID_NEDUCATIVO";
		// @formatter:on

		val paramMap = new HashMap<String, Object>();
		paramMap.put(PARAM_ID_INSTITUCION, institucionId);

		val result = getJdbcTemplate().query(sql, paramMap, (rs, rowNum) -> {
			NivelEducativoDto r = new NivelEducativoDto();

			r.setJornadaId(rs.getInt(PARAM_ID_JORNADA));
			r.setId(rs.getInt(PARAM_ID_NIVEL_EDUCATIVO));
			r.setDescripcion(rs.getString(DESCRIPCION));

			return r;
		});

		return result;
	}

	@Override
	public List<ProgramaDto> findAllProgramasByNivelEducativoId(int institucionId, int jornadaId, int nivelId) {
		// @formatter:off
		val sql = "SELECT \r\n" + 
				"    b.ID_PROGRAMA, b.DESCRIPCION \r\n" + 
				"FROM CIBER.PROGRAMAS_X_INSTITUCION a \r\n" + 
				"INNER JOIN CIBER.PROGRAMAS b ON \r\n" + 
				"    b.ID_PROGRAMA = a.ID_PROGRAMA \r\n" + 
				"WHERE\r\n" + 
				"    a.ID_INSTITUCION = :ID_INSTITUCION \r\n" + 
				"AND a.ID_JORNADA = :ID_JORNADA \r\n" + 
				"AND a.ID_NEDUCATIVO = :ID_NEDUCATIVO \r\n" +
				"ORDER BY \r\n" + 
				"    b.ID_PROGRAMA";		
		// @formatter:on

		val paramMap = new HashMap<String, Object>();
		paramMap.put(PARAM_ID_INSTITUCION, institucionId);
		paramMap.put(PARAM_ID_JORNADA, jornadaId);
		paramMap.put(PARAM_ID_NIVEL_EDUCATIVO, nivelId);

		val result = getJdbcTemplate().query(sql, paramMap, (rs, rowNum) -> {
			ProgramaDto r = new ProgramaDto();

			r.setId(rs.getInt(ID_PROGRAMA));
			r.setDescripcion(rs.getString(DESCRIPCION));

			return r;
		});

		return result;
	}

	@Override
	public List<GradoDto> findAllGradosByProgramaId(int institucionId, int jornadaId, int nivelId, int programaId) {
		// @formatter:off
				val sql = "SELECT \r\n" + 
						"    a.ID_GRADO, a.DESCRIPCION \r\n" + 
						"FROM CIBER.GRADOS_X_INSTITUCION a \r\n" + 
						"WHERE\r\n" + 
						"    a.ID_INSTITUCION = :ID_INSTITUCION \r\n" + 
						"AND a.ID_JORNADA = :ID_JORNADA \r\n" + 
						"AND a.ID_NEDUCATIVO = :ID_NEDUCATIVO \r\n" +
						"AND a.ID_PROGRAMA = :ID_PROGRAMA \r\n" +
						"ORDER BY \r\n" + 
						"    a.ID_GRADO";		

				// @formatter:on

		val paramMap = new HashMap<String, Object>();
		paramMap.put(PARAM_ID_INSTITUCION, institucionId);
		paramMap.put(PARAM_ID_JORNADA, jornadaId);
		paramMap.put(PARAM_ID_NIVEL_EDUCATIVO, nivelId);
		paramMap.put(PARAM_ID_PROGRAMA, programaId);

		val result = getJdbcTemplate().query(sql, paramMap, (rs, rowNum) -> {
			GradoDto r = new GradoDto();

			r.setId(rs.getInt(ID_GRADO));
			r.setDescripcion(rs.getString(DESCRIPCION));

			return r;
		});

		return result;
	}

	@Override
	public List<CursoDto> findAllCursosByGradoId(int institucionId, int jornadaId, int nivelId, int programaId,
			int gradoId) {
		// @formatter:off
		val sql = "SELECT \r\n" + 
				"    a.ID_GRUPOGRAD, a.DESCRIPCION \r\n" + 
				"FROM CIBER.GRUPOS_X_GRADO a \r\n" + 
				"WHERE\r\n" + 
				"    a.ID_INSTITUCION = :ID_INSTITUCION \r\n" + 
				"AND a.ID_JORNADA = :ID_JORNADA \r\n" + 
				"AND a.ID_NEDUCATIVO = :ID_NEDUCATIVO \r\n" +
				"AND a.ID_PROGRAMA = :ID_PROGRAMA \r\n" +
				"AND a.ID_GRADO = :ID_GRADO \r\n" +
				"ORDER BY \r\n" + 
				"    a.DESCRIPCION";		

		// @formatter:on

		val paramMap = new HashMap<String, Object>();
		paramMap.put(PARAM_ID_INSTITUCION, institucionId);
		paramMap.put(PARAM_ID_JORNADA, jornadaId);
		paramMap.put(PARAM_ID_NIVEL_EDUCATIVO, nivelId);
		paramMap.put(PARAM_ID_PROGRAMA, programaId);
		paramMap.put(PARAM_ID_GRADO, gradoId);

		val result = getJdbcTemplate().query(sql, paramMap, (rs, rowNum) -> {
			CursoDto r = new CursoDto();

			r.setId(rs.getInt(ID_GRUPO));
			r.setDescripcion(rs.getString(DESCRIPCION));

			return r;
		});

		return result;
	}

	@Override
	public List<UsuarioDto> findAllUsuariosEstudiantesByCurso(int institucionId, int jornadaId, int nivelId,
			int programaId, int gradoId, int cursoId) {
		val paramMap = new HashMap<String, Object>();
		paramMap.put(PARAM_ID_INSTITUCION, institucionId);
		paramMap.put(PARAM_ID_PERFIL, PERFIL_ESTUDIANTE);
		paramMap.put(PARAM_ID_JORNADA, jornadaId);
		paramMap.put(PARAM_ID_NIVEL_EDUCATIVO, nivelId);
		paramMap.put(PARAM_ID_PROGRAMA, programaId);
		paramMap.put(PARAM_ID_GRADO, gradoId);
		paramMap.put(PARAM_ID_CURSO, cursoId);

		val result = getJdbcTemplate().query(USUARIOS_ESTUDIANTES_BY_CURSO, paramMap, getUsuarioRowMapper());
		return result;
	}

	@Override
	public List<UsuarioPasajeroDto> findAllUsuariosPasajerosByGradoIdAsUsuarioPasajeroDto(int institucionId,
			int jornadaId, int nivelId, int programaId, int gradoId) {
		val paramMap = new HashMap<String, Object>();
		paramMap.put(PARAM_ID_INSTITUCION, institucionId);
		paramMap.put(PARAM_ID_PERFIL, PERFIL_ESTUDIANTE);
		paramMap.put(PARAM_ID_JORNADA, jornadaId);
		paramMap.put(PARAM_ID_NIVEL_EDUCATIVO, nivelId);
		paramMap.put(PARAM_ID_PROGRAMA, programaId);
		paramMap.put(PARAM_ID_GRADO, gradoId);

		val result = getJdbcTemplate().query(USUARIOS_PASAJEROS_BY_GRADO, paramMap, getUsuarioPasajeroRowMapper());
		return result;
	}
}