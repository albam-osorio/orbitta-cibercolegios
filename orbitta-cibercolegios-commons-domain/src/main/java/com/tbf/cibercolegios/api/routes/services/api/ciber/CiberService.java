package com.tbf.cibercolegios.api.routes.services.api.ciber;

import java.util.List;
import java.util.Optional;

import com.tbf.cibercolegios.api.routes.model.graph.readonly.CiudadDto;
import com.tbf.cibercolegios.api.routes.model.graph.readonly.DepartamentoDto;
import com.tbf.cibercolegios.api.routes.model.graph.readonly.GradoDto;
import com.tbf.cibercolegios.api.routes.model.graph.readonly.GrupoDto;
import com.tbf.cibercolegios.api.routes.model.graph.readonly.InstitucionDto;
import com.tbf.cibercolegios.api.routes.model.graph.readonly.NivelEducativoDto;
import com.tbf.cibercolegios.api.routes.model.graph.readonly.ProgramaDto;
import com.tbf.cibercolegios.api.routes.model.graph.readonly.TipoDocumentoDto;
import com.tbf.cibercolegios.api.routes.model.graph.readonly.UsuarioDto;
import com.tbf.cibercolegios.api.routes.model.graph.readonly.UsuarioPerfilDto;

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
