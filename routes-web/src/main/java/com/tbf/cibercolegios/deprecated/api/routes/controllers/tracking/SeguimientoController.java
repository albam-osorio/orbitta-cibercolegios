package com.tbf.cibercolegios.deprecated.api.routes.controllers.tracking;

import static java.util.stream.Collectors.toList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RestController;

import com.tbf.cibercolegios.api.ciber.services.api.CiberService;
import com.tbf.cibercolegios.api.routes.controllers.util.UserPreferences;
import com.tbf.cibercolegios.api.routes.model.graph.tracking.MonitorDatosRutaDto;
import com.tbf.cibercolegios.api.routes.services.api.RutaService;

import lombok.Getter;
import lombok.Setter;
import lombok.val;

public class SeguimientoController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private UserPreferences preferences;

	@Autowired
	private CiberService ciberService;

	@Autowired
	private RutaService rutasService;

	private List<MonitorDatosRutaDto> items;

	private MonitorDatosRutaDto selected;

	private boolean detail = false;

	private boolean map = false;

	private Integer getInstitucionId() {
		val result = preferences.getInstitucionId();
		return result;
	}

	@PostConstruct
	public void init() {
		reset();
	}

	private void reset() {
		resetActions();

		this.items = getData();
	}

	private void resetActions() {
		//this.setDetail(false);
		//this.setMap(false);
	}

	private List<MonitorDatosRutaDto> getData() {
		val institucionId = getInstitucionId();
		if (institucionId != null) {
			val list = rutasService.findAllByInstitucionIdAsMonitorDatosRuta(institucionId);
			val result = list.stream().sorted((a, b) -> a.getCodigo().compareTo(b.getCodigo())).collect(toList());

			return result;
		} else {
			return new ArrayList<MonitorDatosRutaDto>();
		}
	}

	// ---------------------------------------------------------------------------------
	public void detailListener(MonitorDatosRutaDto item) {
		resetActions();

		this.selected = item;

		//this.setDetail(true);
		//this.setMap(true);
	}

	public void mapListener(ActionEvent event) {
		resetActions();

		boolean success = true;

		if (success) {
			//this.setDetail(false);
			//this.setMap(true);
		}
	}
}