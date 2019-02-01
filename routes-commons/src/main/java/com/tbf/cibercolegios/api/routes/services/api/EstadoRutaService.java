package com.tbf.cibercolegios.api.routes.services.api;

import java.util.List;

import com.tbf.cibercolegios.api.core.services.api.CrudService;
import com.tbf.cibercolegios.api.model.routes.enums.RouteTypeStatus;
import com.tbf.cibercolegios.api.routes.model.graph.EstadoRutaDto;

public interface EstadoRutaService extends CrudService<EstadoRutaDto, Integer> {

	List<EstadoRutaDto> findAll();

	List<EstadoRutaDto> findAllByTipoEvento(RouteTypeStatus tipoEvento);

	EstadoRutaDto findEstadoInicioPredeterminado();

	EstadoRutaDto findEstadoRecorridoPredeterminado();

	EstadoRutaDto findEstadoFinPredeterminado();
}
