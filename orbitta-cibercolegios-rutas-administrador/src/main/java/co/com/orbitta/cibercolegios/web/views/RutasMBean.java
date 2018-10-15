package co.com.orbitta.cibercolegios.web.views;

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.faces.view.ViewScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.com.orbitta.cibercolegios.ciber.dto.InstitucionDto;
import co.com.orbitta.cibercolegios.rutas.dto.RutaDto;
import co.com.orbitta.cibercolegios.service.api.InstitucionQueryService;
import co.com.orbitta.cibercolegios.service.api.RutaCrudService;
import co.com.orbitta.cibercolegios.service.api.UsuarioQueryService;
import co.com.orbitta.cibercolegios.web.model.RutaViewModel;
import lombok.Getter;
import lombok.Setter;
import lombok.val;

@Setter
@Getter
@Component
@ViewScoped
public class RutasMBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private RutaDto model = new RutaDto();;

	private RutaDto selected = new RutaDto();

	private boolean edit;

	@Autowired
	private RutaCrudService rutasService;

	@Autowired
	private InstitucionQueryService institucionesService;

	@Autowired
	private UsuarioQueryService usuariosService;

	public List<RutaViewModel> getModels() {
		val institucion = institucionesService.findOneById(getInstituciionId());
		val models = rutasService.findAllByInstitucionId(institucion.getId());

		val result = models.stream().map(asViewModel(institucion)).collect(Collectors.toList());
		return result;
	}

	private Function<? super RutaDto, RutaViewModel> asViewModel(InstitucionDto institucion) {
		return model -> {
			val monitor = usuariosService.findOneById(model.getMonitorId());

			val result = new RutaViewModel();

			result.setRutaId(model.getId());

			result.setCodigo(model.getCodigo());
			result.setDescripcion(model.getDescripcion());
			result.setMarca(model.getMarca());
			result.setPlaca(model.getPlaca());
			result.setMovil(model.getMovil());

			result.setInstitucionId(model.getInstitucionId());
			result.setInstitucionNombre(institucion.getNombre());

			result.setMonitorId(model.getMonitorId());
			result.setMonitorNombres(monitor.getNombre());
			result.setMonitorApellidos(monitor.getApellido());

			result.setConductorNombres(model.getConductorNombres());

			result.setCapacidadMaxima(model.getCapacidadMaxima());
			result.setUsuariosInscritos(0);

			return result;
		};
	}

	private int getInstituciionId() {
		return 1;
	}

	public String create() {
		String result = "home.xhmtl";

		return result;
	}

	public String update(RutaDto item) {
		String result = "home.xhmtl";

		return result;
	}

	public String delete(RutaDto item) {
		String result = "home.xhmtl";

		return result;
	}

}
