package co.com.orbitta.cibercolegios.rutas.service.api;

import java.time.LocalDate;
import java.util.Optional;

import co.com.orbitta.cibercolegios.dto.UbicacionRutaDto;
import co.com.orbitta.core.services.crud.api.CrudService;

public interface UbicacionRutaCrudService extends CrudService<UbicacionRutaDto, Integer> {

	Optional<UbicacionRutaDto> findSiguienteUbicacion(int rutaId, int id);

	Optional<UbicacionRutaDto> findUltimaUbicacionByRutaIdAndFecha(int rutaId, LocalDate fecha);
}
