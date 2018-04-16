package co.com.orbitta.cibercolegios.rutas.service.api;

import java.util.List;
import java.util.Optional;

import co.com.orbitta.cibercolegios.dto.EstadoRutaDto;
import co.com.orbitta.cibercolegios.enums.TipoEstadoRutaEnum;
import co.com.orbitta.core.services.crud.api.CrudService;

public interface EstadoRutaCrudService extends CrudService<EstadoRutaDto, Integer> {

	List<EstadoRutaDto> findAllByTipoEvento(TipoEstadoRutaEnum tipoEvento);

	Optional<EstadoRutaDto> findByDescripcion(String estadoCodigo);
}
