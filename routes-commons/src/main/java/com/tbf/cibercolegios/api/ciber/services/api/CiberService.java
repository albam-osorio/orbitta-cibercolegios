package com.tbf.cibercolegios.api.ciber.services.api;

import java.util.List;
import java.util.Optional;

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

public interface CiberService {

	Optional<InstitucionDto> findInstitucionById(int id);

	Optional<UsuarioDto> findUsuarioById(int id);

	Optional<UsuarioDto> findUsuarioByIdentificacion(int tipoIdentificacion, String numeroIdentificacion);

	Optional<UsuarioDto> findMonitorByInstitucionAndIdentificacion(int institucionId, int tipoIdentificacion,
			String numeroIdentificacion);

	boolean isEstudianteBelongToInstitucion(int usuarioId, int institucionId);

	List<DepartamentoDto> findAllDepartamentosByPais(int paisId);

	List<CiudadDto> findAllCiudadesByDepartamento(int paisId, int departamentoId);

	Optional<CiudadDto> findCiudadByPk(int paisId, int departamentoId, int ciudadId);

	List<CiudadDto> findAllCiudadesByNombres(int paisId, String departamentoNOmbre, String ciudadNombre);

	List<TipoDocumentoDto> findAllTiposDocumento();

	List<NivelEducativoDto> findAllNivelesEducativosByInstitucion(int institucionId);

	List<ProgramaDto> findAllProgramasByNivelEducativo(int institucionId, int jornadaId, int nivelId);

	List<GradoDto> findAllGradosByPrograma(int institucionId, int jornadaId, int nivelId, int programaId);

	List<GrupoDto> findAllGruposByGrado(int institucionId, int jornadaId, int nivelId, int programaId, int gradoId);

	List<UsuarioDto> findAllPajaserosSinRutaByGrado(int institucionId, int jornadaId, int nivelId, int programaId,
			int gradoId);

	List<UsuarioDto> findAllEstudiantesByGrupo(int institucionId, int jornadaId, int nivelId, int programaId,
			int gradoId, int grupoId);

	Optional<UsuarioPerfilDto> findPerfilByUsuario(int usuarioId);

	List<UsuarioDto> findAcudientesByUsuario(int usuarioId);
	
	List<Integer> findUsuariosIdDeAcudientesByUsuarioId(int usuarioId);

}
