package com.tbf.cibercolegios.api.ciber.services.api;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import com.tbf.cibercolegios.api.routes.model.graph.DireccionDto;

public interface CiberService {

	Optional<byte[]> findImagenUsuarioByUsuarioId(int usuarioId);

	// -----------------------------------------------------------------------------------
	// --
	// -----------------------------------------------------------------------------------
	Optional<UsuarioDto> findUsuarioByUsuarioId(int usuarioId);

	List<UsuarioDto> findAllUsuariosByUsuarioIdIn(List<Integer> usuariosId);

	// -----------------------------------------------------------------------------------
	// --
	// -----------------------------------------------------------------------------------
	Optional<UsuarioDto> findUsuarioEstudianteByInstitucionIdAndUsuarioId(int institucionId, int usuarioId);

	Optional<UsuarioDto> findUsuarioEstudianteByInstitucionIdAndIdentificacion(int institucionId,
			int tipoIdentificacion, String numeroIdentificacion);

	// -----------------------------------------------------------------------------------
	// --
	// -----------------------------------------------------------------------------------
	Optional<UsuarioDto> findUsuarioMonitorByInstitucionIdAndUsuarioId(int institucionId, int usuarioId);

	Optional<UsuarioDto> findUsuarioMonitorByInstitucionIdAndIdentificacion(int institucionId, int tipoIdentificacion,
			String numeroIdentificacion);

	// -----------------------------------------------------------------------------------
	// --
	// -----------------------------------------------------------------------------------
	Optional<UsuarioDto> findUsuarioAcudienteByUsuarioId(int usuarioId);

	List<UsuarioDto> findUsuariosAcudientesByInstitucionIdAndUsuarioPasajeroId(int institucionId, int usuarioId);

	// -----------------------------------------------------------------------------------
	// --
	// -----------------------------------------------------------------------------------
	Optional<UsuarioPasajeroDto> findUsuarioPasajeroByInstitucionIdAndUsuarioIdAsUsuarioPasajeroDto(int institucionId,
			int usuarioId);

	List<UsuarioPasajeroDto> findAllUsuariosPasajerosByInstitucionIdAndPasajeroIdInAsUsuarioPasajeroDto(
			int institucionId, List<Integer> pasajerosId);

	// -----------------------------------------------------------------------------------
	// --
	// -----------------------------------------------------------------------------------
	List<DepartamentoDto> findAllDepartamentosByPaisId(int paisId);

	List<DepartamentoDto> findAllDepartamentosByNombres(int paisId, List<String> departamentosNombre);

	Optional<DepartamentoDto> findDepartamentoByDepartamentoId(int paisId, int departamentoId);

	List<CiudadDto> findAllCiudadesByDepartamentoId(int paisId, int departamentoId);

	List<CiudadDto> findAllCiudadesByNombres(int paisId, int departamentoId, List<String> ciudadesNombre);

	List<CiudadDto> findAllCiudadesByIdIn(int paisId, int departamentoId, Collection<Integer> ciudadesId);

	Optional<CiudadDto> findCiudadByCiudadId(int paisId, int departamentoId, int ciudadId);

	Map<DepartamentoDto, List<CiudadDto>> findCiudadesByDirecciones(List<DireccionDto> direcciones);

	// -----------------------------------------------------------------------------------
	// --
	// -----------------------------------------------------------------------------------
	List<TipoDocumentoDto> findAllTiposDocumento();

	Optional<InstitucionDto> findInstitucionById(int institucionId);

	List<NivelEducativoDto> findAllNivelesEducativosByInstitucionId(int institucionId);

	List<ProgramaDto> findAllProgramasByNivelEducativoId(int institucionId, int jornadaId, int nivelId);

	List<GradoDto> findAllGradosByProgramaId(int institucionId, int jornadaId, int nivelId, int programaId);

	List<CursoDto> findAllCursosByGradoId(int institucionId, int jornadaId, int nivelId, int programaId, int gradoId);

	List<UsuarioDto> findAllUsuariosEstudiantesByCurso(int institucionId, int jornadaId, int nivelId, int programaId,
			int gradoId, int grupoId);

	List<UsuarioPasajeroDto> findAllUsuariosPasajerosByGradoIdAsUsuarioPasajeroDto(int institucionId, int jornadaId,
			int nivelId, int programaId, int gradoId);

}
