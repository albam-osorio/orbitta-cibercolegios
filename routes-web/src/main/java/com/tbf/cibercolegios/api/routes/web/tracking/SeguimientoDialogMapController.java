package com.tbf.cibercolegios.api.routes.web.tracking;

import java.util.stream.Collectors;

import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.primefaces.model.map.Polyline;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RestController;

import com.tbf.cibercolegios.api.model.routes.enums.CourseType;
import com.tbf.cibercolegios.api.routes.model.graph.tracking.MonitorDatosRutaDto;
import com.tbf.cibercolegios.core.DialogController;

import lombok.Getter;
import lombok.Setter;
import lombok.val;

@RestController
@Scope("view")
@Setter
@Getter
public class SeguimientoDialogMapController extends DialogController<MonitorDatosRutaDto, Integer> {

	private static final long serialVersionUID = 1L;

	private MapModel mapModel = new DefaultMapModel();

	@Override
	protected void init() {

	}

	@Override
	protected boolean populate() {
		mapModel = new DefaultMapModel();
		val posicion = new LatLng(getModel().getY().doubleValue(), getModel().getX().doubleValue());
		mapModel.addOverlay(new Marker(posicion, "Ruta " + getModel().getCodigo()));

		if (getModel().getUltimoSentido() == CourseType.SENTIDO_IDA.getIntValue()) {
			Polyline polyline = new Polyline();

			polyline.setStrokeWeight(10);
			polyline.setStrokeColor("#FF9900");
			polyline.setStrokeOpacity(0.7);

			polyline.getPaths().add(posicion);
			
			val pasajeros = getModel().getPasajeros().stream().filter(a -> !a.isFinalizado())
					.sorted((a, b) -> Integer.compare(a.getSecuencia(), b.getSecuencia())).collect(Collectors.toList());
			for (val pasajero : pasajeros) {
				if (pasajero.getX() != null && pasajero.getY() != null) {
					val c = new LatLng(pasajero.getY().doubleValue(), pasajero.getX().doubleValue());

					mapModel.addOverlay(new Marker(c, pasajero.getSecuencia() + "-" + pasajero.getDireccion(), "",
							"http://maps.google.com/mapfiles/ms/micons/blue-dot.png"));

					polyline.getPaths().add(c);
				}
			}

			if (getModel().getInstitucionX() != null && getModel().getInstitucionY() != null) {
				val sede = new LatLng(getModel().getInstitucionY().doubleValue(),
						getModel().getInstitucionX().doubleValue());
				mapModel.addOverlay(new Marker(sede, getModel().getInstitucionNombre()));

				polyline.getPaths().add(sede);
			}

			mapModel.addOverlay(polyline);
		}

		if (getModel().getUltimoSentido() == CourseType.SENTIDO_RETORNO.getIntValue()) {
			Polyline polyline = new Polyline();

			polyline.setStrokeWeight(10);
			polyline.setStrokeColor("#FF9900");
			polyline.setStrokeOpacity(0.7);

			polyline.getPaths().add(posicion);
			
			val pasajeros = getModel().getPasajeros().stream().filter(a -> !a.isFinalizado())
					.sorted((a, b) -> Integer.compare(a.getSecuencia(), b.getSecuencia())).collect(Collectors.toList());
			for (val pasajero : pasajeros) {
				if (pasajero.getX() != null && pasajero.getY() != null) {
					val c = new LatLng(pasajero.getY().doubleValue(), pasajero.getX().doubleValue());

					mapModel.addOverlay(new Marker(c, pasajero.getSecuencia() + "-" + pasajero.getDireccion(), "",
							"http://maps.google.com/mapfiles/ms/micons/blue-dot.png"));

					polyline.getPaths().add(c);
				}
			}

			mapModel.addOverlay(polyline);
		}
		return true;
	}
}
