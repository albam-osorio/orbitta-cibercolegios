package com.tbf.cibercolegios.api.routes.controllers.tracking;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RestController;

import com.tbf.cibercolegios.api.routes.controllers.util.UserPreferences;
import com.tbf.cibercolegios.api.routes.model.graph.tracking.MonitorDatosRutaDto;
import com.tbf.cibercolegios.api.routes.services.api.RutaService;
import com.tbf.cibercolegios.core.CrudController;

import lombok.Getter;
import lombok.Setter;
import lombok.val;

@RestController
@Scope("view")
@Setter
@Getter
public class SeguimientoCrudController extends CrudController<MonitorDatosRutaDto, String> {

	private static final long serialVersionUID = 1L;

	@Autowired
	private UserPreferences preferences;

	@Autowired
	private RutaService rutasService;

	@Override
	protected void init() {
		super.init();
		this.reset();
	}

	@Override
	protected void populate() {
		find();
	}

	@Override
	protected void executeFind() {
		getModels().addAll(findAllRutas());
	}

	private List<MonitorDatosRutaDto> findAllRutas() {
		val result = new ArrayList<MonitorDatosRutaDto>();
		val institucionId = getInstitucionId();
		if (institucionId != null) {
			result.addAll(rutasService.findAllByInstitucionIdAsMonitorDatosRuta(institucionId).stream()
					.sorted((a, b) -> a.getCodigo().compareTo(b.getCodigo())).collect(Collectors.toList()));
		}
		return result;
	}

	private Integer getInstitucionId() {
		val result = preferences.getInstitucionId();
		return result;
	}
}
