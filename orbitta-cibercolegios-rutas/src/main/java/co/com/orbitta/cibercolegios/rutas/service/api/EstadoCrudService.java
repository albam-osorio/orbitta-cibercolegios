package co.com.orbitta.cibercolegios.rutas.service.api;

import java.util.List;
import java.util.Optional;

import co.com.orbitta.cibercolegios.dto.EstadoDto;
import co.com.orbitta.cibercolegios.enums.TipoEventoEnum;
import co.com.orbitta.core.services.crud.api.CrudService;

public interface EstadoCrudService extends CrudService<EstadoDto, Integer> {

	List<EstadoDto> findAllByTipoEvento(TipoEventoEnum tipoEvento);

	Optional<EstadoDto> findByDescripcion(String estadoCodigo);
}
