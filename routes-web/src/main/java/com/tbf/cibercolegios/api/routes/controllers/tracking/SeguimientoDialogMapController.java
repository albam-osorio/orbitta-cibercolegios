package com.tbf.cibercolegios.api.routes.controllers.tracking;

import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.primefaces.model.map.Polyline;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RestController;

import com.tbf.cibercolegios.api.routes.model.graph.RutaDto;
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
	protected void populate() {
		mapModel = new DefaultMapModel();
		val posicion = new LatLng(getModel().getY().doubleValue(), getModel().getX().doubleValue());
		mapModel.addOverlay(new Marker(posicion, "Ruta "+getModel().getCodigo()));

		if (getModel().getInstitucionY() != null && getModel().getUltimoSentido() == RutaDto.SENTIDO_IDA) {
			val sede = new LatLng(getModel().getInstitucionY().doubleValue(), getModel().getInstitucionX().doubleValue());
			mapModel.addOverlay(new Marker(sede, getModel().getInstitucionNombre()));
			
			Polyline polyline = new Polyline();
			polyline.getPaths().add(posicion);
			polyline.getPaths().add(sede);

			mapModel.addOverlay(polyline);
		}
	}
}
