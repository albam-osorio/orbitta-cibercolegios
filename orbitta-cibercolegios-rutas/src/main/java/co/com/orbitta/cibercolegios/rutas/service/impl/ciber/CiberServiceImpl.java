package co.com.orbitta.cibercolegios.rutas.service.impl.ciber;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.rutas.dto.readonly.CiudadDto;
import co.com.orbitta.cibercolegios.rutas.dto.readonly.InstitucionDto;
import co.com.orbitta.cibercolegios.rutas.dto.readonly.UsuarioDto;
import co.com.orbitta.cibercolegios.rutas.service.api.ciber.CiberService;
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
		val sql = "SELECT ID_INSTITUCION, NOMBRE FROM CIBER.INSTITUCIONES WHERE ID_INSTITUCION = :ID";

		val paramMap = new HashMap<String, Object>();
		paramMap.put("ID", id);

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

		val result = getJdbcTemplate().queryForObject(sql, paramMap, (rs, rowNum) -> {
			UsuarioDto r = new UsuarioDto();

			r.setId(rs.getInt("ID_USUARIO"));
			r.setLogin(rs.getString("LOGIN"));
			r.setTipoId(rs.getInt("ID_TIPOID"));
			r.setNumeroId(rs.getString("NUMERO_ID"));
			r.setNombre(rs.getString("NOMBRE"));
			r.setApellido(rs.getString("APELLIDO"));

			return r;
		});
		return Optional.of(result);
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
}