package co.com.orbitta.cibercolegios.service.api;

import java.util.List;

import co.com.orbitta.cibercolegios.rutas.dto.EstadoRutaDto;
import co.com.orbitta.cibercolegios.rutas.enums.TipoEstadoRutaEnum;
import co.com.orbitta.core.services.crud.api.CrudService;

public interface EstadoRutaCrudService extends CrudService<EstadoRutaDto, Integer> {

	List<EstadoRutaDto> findAll();
	
	List<EstadoRutaDto> findAllByTipoEvento(TipoEstadoRutaEnum tipoEvento);

	EstadoRutaDto findEstadoInicioPredeterminado();

	EstadoRutaDto findEstadoNormalPredeterminado();

	EstadoRutaDto findEstadoFinPredeterminado();
}
