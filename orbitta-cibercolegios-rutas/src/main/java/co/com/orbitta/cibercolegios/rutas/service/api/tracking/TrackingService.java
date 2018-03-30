package co.com.orbitta.cibercolegios.rutas.service.api.tracking;

import java.math.BigDecimal;

public interface TrackingService {

	void iniciarRecorrido(int monitorId, int sentido, BigDecimal cx, BigDecimal cy);

	void registrarPosicion();

	void registrarEventoEnRecorrido();

	void registrarRecogidaDePasajero();

	void finalizarRecorrido();

	void getInformacionRuta();

	void getEstadoRuta();
}
