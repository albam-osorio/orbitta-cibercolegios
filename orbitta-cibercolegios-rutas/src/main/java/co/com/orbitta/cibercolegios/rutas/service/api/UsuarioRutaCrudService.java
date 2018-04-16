package co.com.orbitta.cibercolegios.rutas.service.api;

import java.util.List;

import co.com.orbitta.cibercolegios.dto.UsuarioRutaDto;
import co.com.orbitta.core.services.crud.api.CrudService;

public interface UsuarioRutaCrudService extends CrudService<UsuarioRutaDto, Integer> {

	List<UsuarioRutaDto> findAllByRutaId(int rutaId);
}
