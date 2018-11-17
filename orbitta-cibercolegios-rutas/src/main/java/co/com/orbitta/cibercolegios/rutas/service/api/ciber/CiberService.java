package co.com.orbitta.cibercolegios.rutas.service.api.ciber;

import java.util.Optional;

import co.com.orbitta.cibercolegios.rutas.dto.readonly.CiudadDto;
import co.com.orbitta.cibercolegios.rutas.dto.readonly.InstitucionDto;
import co.com.orbitta.cibercolegios.rutas.dto.readonly.UsuarioDto;

public interface CiberService {

	Optional<InstitucionDto> findInstitucionById(int id);

	Optional<UsuarioDto> findUsuarioById(int id);

	Optional<CiudadDto> findCiudadByPk(int paisId, int departamentoId, int ciudadId);
}
