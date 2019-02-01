package com.tbf.cibercolegios.api.ciber.services.impl;

import static java.util.stream.Collectors.toList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.tbf.cibercolegios.api.ciber.model.graph.CiudadDto;
import com.tbf.cibercolegios.api.ciber.model.graph.DepartamentoDto;
import com.tbf.cibercolegios.api.ciber.model.graph.GradoDto;
import com.tbf.cibercolegios.api.ciber.model.graph.GrupoDto;
import com.tbf.cibercolegios.api.ciber.model.graph.InstitucionDto;
import com.tbf.cibercolegios.api.ciber.model.graph.NivelEducativoDto;
import com.tbf.cibercolegios.api.ciber.model.graph.ProgramaDto;
import com.tbf.cibercolegios.api.ciber.model.graph.TipoDocumentoDto;
import com.tbf.cibercolegios.api.ciber.model.graph.UsuarioDto;
import com.tbf.cibercolegios.api.ciber.model.graph.UsuarioPerfilDto;
import com.tbf.cibercolegios.api.ciber.services.api.CiberService;

import lombok.val;

@Service
public class CiberServiceImpl implements CiberService {

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	protected NamedParameterJdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	@Override
	public Optional<InstitucionDto> findInstitucionById(int id) {
		val sql = "SELECT ID_INSTITUCION, NOMBRE FROM CIBER.INSTITUCIONES WHERE ID_INSTITUCION = :ID_INSTITUCION";

		val paramMap = new HashMap<String, Object>();
		paramMap.put("ID_INSTITUCION", id);

		val result = getJdbcTemplate().queryForObject(sql, paramMap, (rs, rowNum) -> {
			InstitucionDto r = new InstitucionDto();

			r.setId(rs.getInt("ID_INSTITUCION"));
			r.setNombre(rs.getString("NOMBRE"));

			return r;
		});
		return Optional.of(result);
	}

	@Override
	public Optional<UsuarioDto> findUsuarioById(int id) {
		val sql = "SELECT ID_USUARIO, LOGIN, ID_TIPOID, NUMERO_ID, NOMBRE, APELLIDO FROM CIBER.USUARIOS WHERE ID_USUARIO = :ID";

		val paramMap = new HashMap<String, Object>();
		paramMap.put("ID", id);

		val result = getUsuario(sql, paramMap);
		return result;
	}

	@Override
	public Optional<UsuarioDto> findUsuarioByIdentificacion(int tipoIdentificacion, String numeroIdentificacion) {
		val sql = "SELECT ID_USUARIO, LOGIN, ID_TIPOID, NUMERO_ID, NOMBRE, APELLIDO FROM CIBER.USUARIOS WHERE ID_TIPOID = :ID_TIPOID AND NUMERO_ID = :NUMERO_ID";

		val paramMap = new HashMap<String, Object>();
		paramMap.put("ID_TIPOID", tipoIdentificacion);
		paramMap.put("NUMERO_ID", numeroIdentificacion);

		val result = getUsuario(sql, paramMap);
		return result;
	}

	@Override
	public Optional<UsuarioDto> findMonitorByInstitucionAndIdentificacion(int institucionId, int tipoIdentificacion,
			String numeroIdentificacion) {
		// @formatter:off
		val sql = "" + 
				"SELECT \r\n" + 
				"  a.ID_USUARIO, a.LOGIN, a.ID_TIPOID, a.NUMERO_ID, a.NOMBRE, a.APELLIDO \r\n" + 
				"FROM CIBER.USUARIOS a \r\n" + 
				"INNER JOIN CIBER.PERFIL b ON \r\n" + 
				"    b.ID_USUARIO = a.ID_USUARIO \r\n" + 
				"AND b.ID_PERFIL = 6 \r\n" + 
				"AND b.ACTIVO = 1 \r\n" + 
				"WHERE 1 = 1 \r\n" + 
				"AND a.BORRADO = 'N' \r\n" + 
				"AND a.ID_TIPOID = :ID_TIPOID \r\n" +
				"AND a.NUMERO_ID = :NUMERO_ID \r\n" +
				"AND b.ID_INSTITUCION = :ID_INSTITUCION";
		// @formatter:on

		val paramMap = new HashMap<String, Object>();
		paramMap.put("ID_TIPOID", tipoIdentificacion);
		paramMap.put("NUMERO_ID", numeroIdentificacion);
		paramMap.put("ID_INSTITUCION", institucionId);

		val result = getUsuario(sql, paramMap);
		return result;
	}

	@Override
	public boolean isEstudianteBelongToInstitucion(int usuarioId, int institucionId) {
		// @formatter:off
		val sql = "" +
				"SELECT \r\n" + 
				"    COUNT(1)\r\n" + 
				"FROM  CIBER.ESTUDIANTES a\r\n" + 
				"INNER JOIN CIBER.PERFIL b ON\r\n" + 
				"    b.ID_INSTITUCION = a.ID_INSTITUCION \r\n" + 
				"AND b.ID_JORNADA     = a.ID_JORNADA\r\n" + 
				"AND b.ID_USUARIO     = a.ID_USUARIO\r\n" + 
				"AND b.ID_PERFIL      = 2\r\n" + 
				"AND b.ACTIVO         = 1\r\n" +
				"WHERE 1 = 1 \r\n" + 
				"AND a.ID_INSTITUCION = :ID_INSTITUCION \r\n" + 
				"AND a.ID_USUARIO = :ID_USUARIO \r\n";
		// @formatter:on

		val paramMap = new HashMap<String, Object>();
		paramMap.put("ID_INSTITUCION", institucionId);
		paramMap.put("ID_USUARIO", usuarioId);

		boolean result = false;
		int count = jdbcTemplate.queryForObject(sql, paramMap, Integer.class);
		if (count > 0) {
			result = true;
		}
		return result;
	}

	private Optional<UsuarioDto> getUsuario(final String sql, final Map<String, Object> paramMap) {
		try {
			val result = getJdbcTemplate().queryForObject(sql, paramMap, UsuarioRowMapper());

			return Optional.of(result);
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	private RowMapper<UsuarioDto> UsuarioRowMapper() {
		return (rs, rowNum) -> {
			UsuarioDto r = new UsuarioDto();

			r.setId(rs.getInt("ID_USUARIO"));
			r.setLogin(rs.getString("LOGIN"));
			r.setTipoId(rs.getInt("ID_TIPOID"));
			r.setNumeroId(rs.getString("NUMERO_ID"));
			r.setNombre(rs.getString("NOMBRE"));
			r.setApellido(rs.getString("APELLIDO"));

			return r;
		};
	}

	@Override
	public List<DepartamentoDto> findAllDepartamentosByPais(int paisId) {
		val sql = "SELECT ID_PAISES, ID_ESTADO, DESCRIPCION FROM CIBER.ESTADOS WHERE ID_PAISES = :ID_PAIS ORDER BY DESCRIPCION";

		val paramMap = new HashMap<String, Object>();
		paramMap.put("ID_PAIS", paisId);

		val result = getJdbcTemplate().query(sql, paramMap, (rs, rowNum) -> {
			DepartamentoDto r = new DepartamentoDto();

			r.setPaisId(rs.getInt("ID_PAISES"));
			r.setDepartamentoId(rs.getInt("ID_ESTADO"));
			r.setNombre(rs.getString("DESCRIPCION"));

			return r;
		});

		return result;
	}

	@Override
	public List<CiudadDto> findAllCiudadesByDepartamento(int paisId, int departamentoId) {
		val sql = "SELECT ID_PAISES, ID_ESTADO, ID_CIUDAD, DESCRIPCION FROM CIBER.CIUDADES WHERE ID_PAISES = :ID_PAIS AND ID_ESTADO = :ID_ESTADO ORDER BY DESCRIPCION";

		val paramMap = new HashMap<String, Object>();
		paramMap.put("ID_PAIS", paisId);
		paramMap.put("ID_ESTADO", departamentoId);

		val result = getJdbcTemplate().query(sql, paramMap, (rs, rowNum) -> {
			CiudadDto r = new CiudadDto();

			r.setPaisId(rs.getInt("ID_PAISES"));
			r.setDepartamentoId(rs.getInt("ID_ESTADO"));
			r.setCiudadId(rs.getInt("ID_CIUDAD"));
			r.setNombre(rs.getString("DESCRIPCION"));

			return r;
		});

		return result;
	}

	@Override
	public Optional<CiudadDto> findCiudadByPk(int paisId, int departamentoId, int ciudadId) {
		val sql = "SELECT ID_PAISES,ID_ESTADO,ID_CIUDAD,DESCRIPCION FROM CIBER.CIUDADES WHERE ID_PAISES = :ID_PAIS AND ID_ESTADO = :ID_ESTADO AND ID_CIUDAD = :ID_CIUDAD";

		val paramMap = new HashMap<String, Object>();
		paramMap.put("ID_PAIS", paisId);
		paramMap.put("ID_ESTADO", departamentoId);
		paramMap.put("ID_CIUDAD", ciudadId);

		val result = getJdbcTemplate().queryForObject(sql, paramMap, (rs, rowNum) -> {
			CiudadDto r = new CiudadDto();

			r.setPaisId(rs.getInt("ID_PAISES"));
			r.setDepartamentoId(rs.getInt("ID_ESTADO"));
			r.setCiudadId(rs.getInt("ID_CIUDAD"));
			r.setNombre(rs.getString("DESCRIPCION"));

			return r;
		});
		return Optional.of(result);
	}

	@Override
	public List<CiudadDto> findAllCiudadesByNombres(int paisId, String departamentoNombre, String ciudadNombre) {
		val sql = "SELECT a.ID_PAISES,a.ID_ESTADO,a.ID_CIUDAD,a.DESCRIPCION FROM CIBER.CIUDADES a INNER JOIN CIBER.ESTADOS b ON b.ID_PAISES = a.ID_PAISES AND b.ID_ESTADO = a.ID_ESTADO WHERE a.ID_PAISES = :ID_PAIS AND UPPER(b.DESCRIPCION) = UPPER(:ESTADO_DESCRIPCION) AND UPPER(a.DESCRIPCION) = UPPER(:CIUDAD_DESCRIPCION)";

		val paramMap = new HashMap<String, Object>();
		paramMap.put("ID_PAIS", paisId);
		paramMap.put("ESTADO_DESCRIPCION", departamentoNombre);
		paramMap.put("CIUDAD_DESCRIPCION", ciudadNombre);

		val result = getJdbcTemplate().query(sql, paramMap, (rs, rowNum) -> {
			CiudadDto r = new CiudadDto();

			r.setPaisId(rs.getInt("ID_PAISES"));
			r.setDepartamentoId(rs.getInt("ID_ESTADO"));
			r.setCiudadId(rs.getInt("ID_CIUDAD"));
			r.setNombre(rs.getString("DESCRIPCION"));

			return r;
		});
		return result;

	}

	@Override
	public List<TipoDocumentoDto> findAllTiposDocumento() {
		val sql = "SELECT ID_TIPOID,ABREVIATURA,DESCRIPCION FROM CIBER.TIPO_ID";

		val paramMap = new HashMap<String, Object>();

		val result = getJdbcTemplate().query(sql, paramMap, (rs, rowNum) -> {
			TipoDocumentoDto r = new TipoDocumentoDto();

			r.setId(rs.getInt("ID_TIPOID"));
			r.setAbreviatura(rs.getString("ABREVIATURA"));
			r.setDescripcion(rs.getString("DESCRIPCION"));

			return r;
		});

		return result;
	}

	@Override
	public List<NivelEducativoDto> findAllNivelesEducativosByInstitucion(int institucionId) {
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
		paramMap.put("ID_INSTITUCION", institucionId);

		val result = getJdbcTemplate().query(sql, paramMap, (rs, rowNum) -> {
			NivelEducativoDto r = new NivelEducativoDto();

			r.setJornadaId(rs.getInt("ID_JORNADA"));
			r.setId(rs.getInt("ID_NEDUCATIVO"));
			r.setDescripcion(rs.getString("DESCRIPCION"));

			return r;
		});

		return result;
	}

	@Override
	public List<ProgramaDto> findAllProgramasByNivelEducativo(int institucionId, int jornadaId, int nivelId) {
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
		paramMap.put("ID_INSTITUCION", institucionId);
		paramMap.put("ID_JORNADA", jornadaId);
		paramMap.put("ID_NEDUCATIVO", nivelId);

		val result = getJdbcTemplate().query(sql, paramMap, (rs, rowNum) -> {
			ProgramaDto r = new ProgramaDto();

			r.setId(rs.getInt("ID_PROGRAMA"));
			r.setDescripcion(rs.getString("DESCRIPCION"));

			return r;
		});

		return result;
	}

	@Override
	public List<GradoDto> findAllGradosByPrograma(int institucionId, int jornadaId, int nivelId, int programaId) {
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
		paramMap.put("ID_INSTITUCION", institucionId);
		paramMap.put("ID_JORNADA", jornadaId);
		paramMap.put("ID_NEDUCATIVO", nivelId);
		paramMap.put("ID_PROGRAMA", programaId);

		val result = getJdbcTemplate().query(sql, paramMap, (rs, rowNum) -> {
			GradoDto r = new GradoDto();

			r.setId(rs.getInt("ID_GRADO"));
			r.setDescripcion(rs.getString("DESCRIPCION"));

			return r;
		});

		return result;
	}

	@Override
	public List<GrupoDto> findAllGruposByGrado(int institucionId, int jornadaId, int nivelId, int programaId,
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
		paramMap.put("ID_INSTITUCION", institucionId);
		paramMap.put("ID_JORNADA", jornadaId);
		paramMap.put("ID_NEDUCATIVO", nivelId);
		paramMap.put("ID_PROGRAMA", programaId);
		paramMap.put("ID_GRADO", gradoId);

		val result = getJdbcTemplate().query(sql, paramMap, (rs, rowNum) -> {
			GrupoDto r = new GrupoDto();

			r.setId(rs.getInt("ID_GRUPOGRAD"));
			r.setDescripcion(rs.getString("DESCRIPCION"));

			return r;
		});

		return result;
	}

	@Override
	public List<UsuarioDto> findAllPajaserosSinRutaByGrado(int institucionId, int jornadaId, int nivelId,
			int programaId, int gradoId) {
		// @formatter:off
		val sql = "" +
				"SELECT \r\n" + 
				"    c.ID_USUARIO, c.LOGIN, c.ID_TIPOID, c.NUMERO_ID, c.NOMBRE, c.APELLIDO, a.ID_NEDUCATIVO, a.ID_PROGRAMA, a.ID_GRADO_ACT\r\n" + 
				"FROM  CIBER.ESTUDIANTES a\r\n" + 
				"INNER JOIN CIBER.PERFIL b ON\r\n" + 
				"    b.ID_INSTITUCION = a.ID_INSTITUCION \r\n" + 
				"AND b.ID_JORNADA     = a.ID_JORNADA\r\n" + 
				"AND b.ID_USUARIO     = a.ID_USUARIO\r\n" + 
				"AND b.ID_PERFIL      = 2\r\n" + 
				"AND b.ACTIVO         = 1\r\n" + 
				"INNER JOIN CIBER.USUARIOS c ON\r\n" + 
				"    c.ID_USUARIO      = a.ID_USUARIO\r\n" + 
				"AND c.BORRADO         = 'N'\r\n" + 
				"INNER JOIN PASAJEROS d ON\r\n" + 
				"    d.ID_USUARIO      = a.ID_USUARIO\r\n" + 
				"AND d.ID_RUTA         IS NULL\r\n" + 
				"WHERE 1 = 1\r\n" + 
				"AND a.ID_INSTITUCION 	= :ID_INSTITUCION \r\n" + 
				"AND a.ID_JORNADA 		= :ID_JORNADA \r\n" + 
				"AND a.ID_NEDUCATIVO 	= :ID_NEDUCATIVO \r\n" + 
				"AND a.ID_PROGRAMA 		= :ID_PROGRAMA \r\n" + 
				"AND a.ID_GRADO_ACT    	= :ID_GRADO \r\n" + 
				"ORDER BY \r\n" + 
				"    c.APELLIDO, c.NOMBRE		\r\n" + 
				"";		
		// @formatter:on

		val paramMap = new HashMap<String, Object>();
		paramMap.put("ID_INSTITUCION", institucionId);
		paramMap.put("ID_JORNADA", jornadaId);
		paramMap.put("ID_NEDUCATIVO", nivelId);
		paramMap.put("ID_PROGRAMA", programaId);
		paramMap.put("ID_GRADO", gradoId);

		val result = getJdbcTemplate().query(sql, paramMap, UsuarioRowMapper());
		return result;
	}

	@Override
	public List<UsuarioDto> findAllEstudiantesByGrupo(int institucionId, int jornadaId, int nivelId, int programaId,
			int gradoId, int grupoId) {
		// @formatter:off
		val sql = "" +
				"SELECT \r\n" + 
				"    c.ID_USUARIO, c.LOGIN, c.ID_TIPOID, c.NUMERO_ID, c.NOMBRE, c.APELLIDO, a.ID_NEDUCATIVO, a.ID_PROGRAMA, a.ID_GRADO\r\n" + 
				"FROM  CIBER.USUARIOS_X_GRUPO a\r\n" + 
				"INNER JOIN CIBER.PERFIL b ON\r\n" + 
				"    b.ID_INSTITUCION = a.ID_INSTITUCION \r\n" + 
				"AND b.ID_JORNADA     = a.ID_JORNADA\r\n" + 
				"AND b.ID_USUARIO     = a.ID_USUARIO\r\n" + 
				"AND b.ID_PERFIL      = 2\r\n" + 
				"AND b.ACTIVO         = 1\r\n" + 
				"INNER JOIN CIBER.USUARIOS c ON\r\n" + 
				"    c.ID_USUARIO      = a.ID_USUARIO\r\n" + 
				"AND c.BORRADO         = 'N'\r\n" + 
				"WHERE 1 = 1\r\n" + 
				"AND a.ID_INSTITUCION 	= :ID_INSTITUCION \r\n" + 
				"AND a.ID_JORNADA 		= :ID_JORNADA \r\n" + 
				"AND a.ID_NEDUCATIVO 	= :ID_NEDUCATIVO \r\n" + 
				"AND a.ID_PROGRAMA 		= :ID_PROGRAMA \r\n" + 
				"AND a.ID_GRADO    		= :ID_GRADO \r\n" +
				"AND a.ID_GRUPOGRAD    	= :ID_GRUPO \r\n" +
				"ORDER BY \r\n" + 
				"    c.APELLIDO, c.NOMBRE		\r\n" + 
				"";		
		// @formatter:on

		val paramMap = new HashMap<String, Object>();
		paramMap.put("ID_INSTITUCION", institucionId);
		paramMap.put("ID_JORNADA", jornadaId);
		paramMap.put("ID_NEDUCATIVO", nivelId);
		paramMap.put("ID_PROGRAMA", programaId);
		paramMap.put("ID_GRADO", gradoId);
		paramMap.put("ID_GRUPO", grupoId);

		val result = getJdbcTemplate().query(sql, paramMap, UsuarioRowMapper());
		return result;
	}

	@Override
	public Optional<UsuarioPerfilDto> findPerfilByUsuario(int usuarioId) {
		val sql = "SELECT ID_USUARIO, b.DESCRIPCION AS TIPO_DOCUMENTO, a.FOTO.SOURCE.LOCALDATA AS FOTO, TEL_CASA, DIR_CASA, EMAIL FROM CIBER.USUARIOS a INNER JOIN CIBER.TIPO_ID b ON b.ID_TIPOID = a.ID_TIPOID  WHERE a.ID_USUARIO = :ID_USUARIO";

		val paramMap = new HashMap<String, Object>();
		paramMap.put("ID_USUARIO", usuarioId);

		val result = getJdbcTemplate().queryForObject(sql, paramMap, (rs, rowNum) -> {
			UsuarioPerfilDto r = new UsuarioPerfilDto();

			byte foto[] = null;
			foto = rs.getBytes("FOTO");
			foto = null;
			
			String email = rs.getString("EMAIL");
			if (email == null) {
				email = "";
			}

			r.setFoto(foto);
			r.setId(rs.getInt("ID_USUARIO"));
			r.setTipoDocumento(rs.getString("TIPO_DOCUMENTO"));
			r.setTelefonoDomicilio(rs.getString("TEL_CASA"));
			r.setDireccionDomicilio(rs.getString("DIR_CASA"));
			r.setEmail(email);

			return r;
		});
		return Optional.of(result);
	}

	@Override
	public List<UsuarioDto> findAcudientesByUsuario(int usuarioId) {
		// @formatter:off
		val sql = "SELECT DISTINCT \r\n"
				+ "		ID_USUARIO, LOGIN, ID_TIPOID, NUMERO_ID, NOMBRE, APELLIDO \r\n"
				+ "FROM CIBER.RELACION_X_USUARIO a \r\n"
				+ "INNER JOIN CIBER.USUARIOS b ON \r\n"
				+ "		b.ID_USUARIO = a.ID_USUARIO_ACU \r\n"
				+ "WHERE \r\n"
				+ "		a.ID_USUARIO_EST = :ID_USUARIO";
		// @formatter:on

		val paramMap = new HashMap<String, Object>();
		paramMap.put("ID_USUARIO", usuarioId);

		val result = getJdbcTemplate().query(sql, paramMap, UsuarioRowMapper());
		return result;
	}

	@Override
	public List<Integer> findUsuariosIdDeAcudientesByUsuarioId(int usuarioId) {
		val list = findAcudientesByUsuario(usuarioId);
		val result = list.stream().map(a -> a.getId()).collect(toList());
		return result;
	}
}