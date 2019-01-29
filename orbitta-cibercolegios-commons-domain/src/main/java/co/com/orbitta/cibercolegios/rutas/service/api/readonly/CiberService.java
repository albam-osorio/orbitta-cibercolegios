package co.com.orbitta.cibercolegios.rutas.service.api.readonly;

import java.util.List;
import java.util.Optional;

import co.com.orbitta.cibercolegios.rutas.dto.readonly.CiudadDto;
import co.com.orbitta.cibercolegios.rutas.dto.readonly.DepartamentoDto;
import co.com.orbitta.cibercolegios.rutas.dto.readonly.GradoDto;
import co.com.orbitta.cibercolegios.rutas.dto.readonly.GrupoDto;
import co.com.orbitta.cibercolegios.rutas.dto.readonly.InstitucionDto;
import co.com.orbitta.cibercolegios.rutas.dto.readonly.NivelEducativoDto;
import co.com.orbitta.cibercolegios.rutas.dto.readonly.ProgramaDto;
import co.com.orbitta.cibercolegios.rutas.dto.readonly.TipoDocumentoDto;
import co.com.orbitta.cibercolegios.rutas.dto.readonly.UsuarioDto;
import co.com.orbitta.cibercolegios.rutas.dto.readonly.UsuarioPerfilDto;

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
