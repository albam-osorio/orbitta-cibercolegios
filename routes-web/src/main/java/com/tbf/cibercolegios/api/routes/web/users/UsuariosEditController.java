package com.tbf.cibercolegios.api.routes.web.users;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.SessionScope;

import com.tbf.cibercolegios.api.ciber.model.graph.CiudadDto;
import com.tbf.cibercolegios.api.ciber.model.graph.DepartamentoDto;
import com.tbf.cibercolegios.api.ciber.model.graph.UsuarioDto;
import com.tbf.cibercolegios.api.ciber.model.graph.UsuarioPasajeroDto;
import com.tbf.cibercolegios.api.ciber.services.api.CiberService;
import com.tbf.cibercolegios.api.model.routes.PasajeroDireccion;
import com.tbf.cibercolegios.api.model.routes.enums.CourseType;
import com.tbf.cibercolegios.api.routes.model.graph.DireccionDto;
import com.tbf.cibercolegios.api.routes.model.graph.EstadoDireccionDto;
import com.tbf.cibercolegios.api.routes.model.graph.EstadoPasajeroDto;
import com.tbf.cibercolegios.api.routes.model.graph.PasajeroDireccionDto;
import com.tbf.cibercolegios.api.routes.model.graph.PasajeroDto;
import com.tbf.cibercolegios.api.routes.model.graph.RutaDto;
import com.tbf.cibercolegios.api.routes.models.users.DireccionViewModel;
import com.tbf.cibercolegios.api.routes.models.users.TrayectoViewModel;
import com.tbf.cibercolegios.api.routes.repository.PasajeroDireccionRepository;
import com.tbf.cibercolegios.api.routes.services.api.DireccionService;
import com.tbf.cibercolegios.api.routes.services.api.PasajeroService;
import com.tbf.cibercolegios.api.routes.services.api.RutaService;
import com.tbf.cibercolegios.api.routes.web.utils.UserProfile;
import com.tbf.cibercolegios.core.Command;
import com.tbf.cibercolegios.core.CrudController;

import lombok.Getter;
import lombok.Setter;
import lombok.val;

@RestController
@SessionScope
@Setter
@Getter
public class UsuariosEditController extends CrudController<TrayectoViewModel, String> {

	private static final long serialVersionUID = 1L;

	// -----------------------------------------------------------------------------------
	// --
	// -----------------------------------------------------------------------------------
	@Autowired
	private UserProfile profile;

	@Autowired
	private CiberService ciberService;

	@Autowired
	private RutaService rutaService;

	@Autowired
	private PasajeroService pasajeroService;

	@Autowired
	private DireccionService direccionService;

	// -----------------------------------------------------------------------------------
	// --
	// -----------------------------------------------------------------------------------

	private Integer usuarioId;

	private UsuarioPasajeroDto pasajero;

	private List<UsuarioDto> acudientes;

	private List<PasajeroDireccionDto> pasajerosDirecciones;

	private List<RutaDto> rutas;

	// -----------------------------------------------------------------------------------
	// --
	// -----------------------------------------------------------------------------------
	private List<DireccionViewModel> primary;

	private List<DireccionViewModel> deleted;

	// -----------------------------------------------------------------------------------
	// --
	// -----------------------------------------------------------------------------------
	@Override
	protected void init() {
		super.init();
		this.setAcudientes(new ArrayList<>());
		this.setRutas(new ArrayList<>());
		this.setPrimary(new ArrayList<>());
		this.setDeleted(new ArrayList<>());
	}

	@Override
	protected void clear() {
		super.clear();
		this.getAcudientes().clear();
		this.getRutas().clear();
		this.getPrimary().clear();
		this.getDeleted().clear();
	}

	@Override
	protected boolean populate() {
		val institucionId = getProfile().getInstitucionId();
		val usuarioId = this.getUsuarioId();

		if (institucionId != null) {
			val optional = ciberService
					.findUsuarioPasajeroByInstitucionIdAndUsuarioIdAsUsuarioPasajeroDto(institucionId, usuarioId);

			if (optional.isPresent()) {
				this.setPasajero(optional.get());

				this.getAcudientes().addAll(ciberService
						.findUsuariosAcudientesByInstitucionIdAndUsuarioPasajeroId(institucionId, usuarioId));
				this.getRutas().addAll(rutaService.findAllByInstitucionId(institucionId));

				// La consulta principal depende de la existencia del usuario y el resto de
				// datos aqui consultados
				super.find();
				return true;
			}
		}
		return false;
	}

	// -----------------------------------------------------------------------------------
	// --
	// -----------------------------------------------------------------------------------
	@Override
	protected void executeFind() {
		val pasajerosDirecciones = new ArrayList<PasajeroDireccion>();

		if (this.getPasajero().getPasajeroId() != null) {
			pasajerosDirecciones
					.addAll(pasajeroService.findAllPasajeroDireccionByPasajeroId(this.getPasajero().getPasajeroId()));
		}

		// -----------------------------------------------------------------------------------
		// Consultar y consolidar los datos de las entidades referenciadas por las
		// direcciones de los pasajeros
		// -----------------------------------------------------------------------------------
		val direcionesId = pasajerosDirecciones.stream().map(PasajeroDireccion::getDireccionId).distinct()
				.collect(toList());

		val direcciones = direccionService.findAllById(direcionesId);

		val ciudadesId = direcciones.stream().collect(groupingBy(DireccionDto::getPaisId,
				groupingBy(DireccionDto::getDepartamentoId, groupingBy(DireccionDto::getCiudadId))));

		val departamentos = new ArrayList<DepartamentoDto>();
		val ciudades = new ArrayList<CiudadDto>();

		ciudadesId.entrySet().forEach(pais -> {
			pais.getValue().entrySet().forEach(departamento -> {
				val opd = ciberService.findDepartamentoByDepartamentoId(pais.getKey(), departamento.getKey());
				if (opd.isPresent()) {
					departamentos.add(opd.get());

					departamento.getValue().entrySet().forEach(ciudad -> {
						val opc = ciberService.findCiudadByCiudadId(pais.getKey(), departamento.getKey(),
								ciudad.getKey());
						if (opc.isPresent()) {
							ciudades.add(opc.get());
						}
					});
				}
			});
		});

		// -----------------------------------------------------------------------------------
		// Construir los trayectos
		// -----------------------------------------------------------------------------------
		val entries = pasajerosDirecciones.stream().collect(groupingBy(PasajeroDireccion::getCorrelacion)).entrySet();

		val list = entries.stream().map(asTrayecto(direcciones, departamentos, ciudades)).collect(toList());
		this.getModels().clear();
		this.getModels().addAll(list);

		resetPrimaryBuffer();
	}

	private Function<Entry<Integer, List<PasajeroDireccion>>, TrayectoViewModel> asTrayecto(
			List<DireccionDto> direcciones, List<DepartamentoDto> departamentos, List<CiudadDto> ciudades) {
		return entry -> {
			val model = new TrayectoViewModel();
			val pasajeroDireccion = entry.getValue().stream().findFirst().get();

			model.setCorrelacion(pasajeroDireccion.getCorrelacion());
			model.setActivo(pasajeroDireccion.isActivo());
			model.setOriginalActivo(pasajeroDireccion.isActivo());

			val optional = getRutas().stream().filter(a -> a.getId().equals(pasajeroDireccion.getRutaId())).findFirst();
			if (optional.isPresent()) {
				val ruta = optional.get();
				model.setRutaId(ruta.getId());
				model.setRutaDescripcion(ruta.getDescripcion());
				model.setOriginalRutaId(ruta.getId());
			}

			model.setDireccionAm(asPasajeroDireccionViewModel(entry.getValue(), CourseType.SENTIDO_IDA, direcciones,
					departamentos, ciudades));
			model.setDireccionPm(asPasajeroDireccionViewModel(entry.getValue(), CourseType.SENTIDO_RETORNO, direcciones,
					departamentos, ciudades));

			return model;
		};
	}

	private DireccionViewModel asPasajeroDireccionViewModel(List<PasajeroDireccion> pasajerosDirecciones,
			CourseType sentido, final List<DireccionDto> direcciones, final List<DepartamentoDto> departamentos,
			final List<CiudadDto> ciudades) {

		val optional = pasajerosDirecciones.stream().filter(a -> a.getSentido() == sentido.getIntValue()).findFirst();

		if (optional.isPresent()) {
			val direccion = getDireccion(direcciones, optional.get().getDireccionId());
			val departamento = getDepartamento(departamentos, direccion);
			val ciudad = getCiudad(ciudades, direccion);

			val result = new DireccionViewModel();

			result.setId(optional.get().getId());
			result.setSentido(sentido);
			result.setDireccionId(direccion.getId());
			result.setDepartamentoId(departamento.getDepartamentoId());
			result.setDepartamentoNombre(departamento.getNombre());
			result.setCiudadId(ciudad.getCiudadId());
			result.setCiudadNombre(ciudad.getNombre());
			result.setDireccion(direccion.getDireccion());
			result.setDireccionVersion(direccion.getVersion());

			result.setSecuencia(optional.get().getSecuencia());
			result.setGeoCodificada(direccion.getX() != null);
			result.setVersion(optional.get().getVersion());
			result.setModificado(false);

			return result;
		}

		return null;
	}

	private DepartamentoDto getDepartamento(List<DepartamentoDto> departamentos, DireccionDto direccion) {
		return departamentos.stream().filter(
				a -> a.getPaisId() == direccion.getPaisId() && a.getDepartamentoId() == direccion.getDepartamentoId())
				.findFirst().get();
	}

	private CiudadDto getCiudad(List<CiudadDto> ciudades, DireccionDto direccion) {
		return ciudades.stream()
				.filter(a -> a.getPaisId() == direccion.getPaisId()
						&& a.getDepartamentoId() == direccion.getDepartamentoId()
						&& a.getCiudadId() == direccion.getCiudadId())
				.findFirst().get();
	}

	private DireccionDto getDireccion(List<DireccionDto> direcciones, int direccionId) {
		return direcciones.stream().filter(a -> a.getId() == direccionId).findFirst().get();
	}

	// -----------------------------------------------------------------------------------
	// --
	// -----------------------------------------------------------------------------------
	public void resetPrimaryBuffer() {
		this.getPrimary().clear();
		this.getPrimary().addAll(
				this.getModels().stream().map(a -> a.getDireccionAm()).filter(a -> a != null).collect(toList()));
		this.getPrimary().addAll(
				this.getModels().stream().map(a -> a.getDireccionPm()).filter(a -> a != null).collect(toList()));
	}

	// -----------------------------------------------------------------------------------
	// -- EVENTOS
	// -----------------------------------------------------------------------------------
	public void onChangeActivo(TrayectoViewModel trayecto) {
		if (trayecto.isActivo()) {
			activateTrayecto(trayecto);
		}
	}

	// -----------------------------------------------------------------------------------
	// -- ENABLE COMMANDS
	// -----------------------------------------------------------------------------------
	public boolean enableAddTrayecto() {
		return true;
	}

	public boolean enableDeleteTrayecto(TrayectoViewModel trayecto) {
		return trayecto.isDeletable();
	}

	public boolean enableActivateTrayecto(TrayectoViewModel trayecto) {
		boolean result = !trayecto.isTrayectoVacio();
		return result;
	}

	public boolean enableAssignRoute(TrayectoViewModel trayecto) {
		boolean result = !trayecto.isTrayectoVacio();
		return result;
	}

	public boolean enableSortRoute(TrayectoViewModel trayecto) {
		return trayecto.isActivo() && trayecto.getRutaId() != null && !enableSave();
	}

	public boolean enableAddDireccion(TrayectoViewModel trayecto, CourseType sentido) {
		boolean result = trayecto.isEditable();
		return result;
	}

	public boolean enableCopyDireccion(TrayectoViewModel trayecto, CourseType sentido) {
		boolean result = trayecto.isEditable();
		if (result) {
			switch (sentido) {
			case SENTIDO_IDA:
				result = trayecto.isTieneDireccionPm();
				break;
			case SENTIDO_RETORNO:
				result = trayecto.isTieneDireccionAm();
				break;
			default:
				break;
			}
		}
		return result;
	}

	public boolean enableEditDireccion(TrayectoViewModel trayecto, DireccionViewModel direccion) {
		return true;
	}

	public boolean enableDeleteDireccion(TrayectoViewModel trayecto, DireccionViewModel direccion) {
		boolean result = trayecto.isEditable();
		return result;
	}

	public boolean enableSave() {
		boolean result = false;

		if (this.getModels().stream().anyMatch(a -> a.isModificado())) {
			result = true;
		} else {
			if (!this.getDeleted().isEmpty()) {
				result = true;
			}
		}

		return result;
	}

	// -----------------------------------------------------------------------------------
	// -- COMANDOS
	// -----------------------------------------------------------------------------------
	public void addTrayecto() {
		val trayecto = new TrayectoViewModel();
		int correlacion = 0;

		val max = getModels().stream().map(TrayectoViewModel::getCorrelacion).max(Integer::compare);
		if (max.isPresent()) {
			correlacion = max.get() + 1;
		}

		trayecto.setCorrelacion(correlacion);
		trayecto.setActivo(false);

		this.getModels().add(trayecto);
		this.resetPrimaryBuffer();
	}

	public void copyDireccion(TrayectoViewModel trayecto, CourseType sentido) {
		DireccionViewModel source = null;
		DireccionViewModel target = new DireccionViewModel();

		switch (sentido) {
		case SENTIDO_IDA:
			source = trayecto.getDireccionPm();
			trayecto.setDireccionAm(target);

			break;
		case SENTIDO_RETORNO:
			source = trayecto.getDireccionAm();
			trayecto.setDireccionPm(target);

			break;
		default:
			break;
		}

		if (source != null) {
			target.setSentido(sentido);
			target.setDepartamentoId(source.getDepartamentoId());
			target.setDepartamentoNombre(source.getDepartamentoNombre());
			target.setCiudadId(source.getCiudadId());
			target.setCiudadNombre(source.getCiudadNombre());
			target.setDireccion(source.getDireccion());
			target.setSecuencia(null);
			target.setGeoCodificada(false);
			target.setModificado(true);

			resetPrimaryBuffer();
		}
	}

	public void deleteTrayecto(TrayectoViewModel trayecto) {
		this.deleteDireccionAm(trayecto);
		this.deleteDireccionPm(trayecto);

		this.getModels().remove(trayecto);
		resetPrimaryBuffer();
	}

	public void deleteDireccion(TrayectoViewModel trayecto, DireccionViewModel direccion) {
		if (trayecto.contiene(direccion)) {
			if (trayecto.getDireccionAm() == direccion) {
				this.deleteDireccionAm(trayecto);
			}

			if (trayecto.getDireccionPm() == direccion) {
				this.deleteDireccionPm(trayecto);
			}

			if (trayecto.isTrayectoVacio()) {
				this.getModels().remove(trayecto);
			}

			resetPrimaryBuffer();
		}
	}

	protected void deleteDireccionAm(TrayectoViewModel trayecto) {
		if (trayecto.isTieneDireccionAm()) {
			if (trayecto.getDireccionAm().getDireccionId() != null) {
				this.getDeleted().add(trayecto.getDireccionAm());
			}
			trayecto.setDireccionAm(null);
		}
	}

	protected void deleteDireccionPm(TrayectoViewModel trayecto) {
		if (trayecto.isTieneDireccionPm()) {
			if (trayecto.getDireccionPm().getDireccionId() != null) {
				this.getDeleted().add(trayecto.getDireccionPm());
			}
			trayecto.setDireccionPm(null);
		}
	}

	private void activateTrayecto(TrayectoViewModel trayecto) {
		val otros = getModels().stream().filter(a -> a != trayecto && a.isActivo()).collect(toList());
		if (trayecto.isTieneDireccionAm()) {
			// Se esta activando AM -> todos los trayectos que tengan AM deben ser
			// inactivados
			val list = otros.stream().filter(a -> a.getDireccionAm() != null).collect(toList());
			for (val t : list) {
				t.setActivo(false);
			}
		}
		if (trayecto.isTieneDireccionPm()) {
			// Se esta activando PM -> todos los trayectos que tengan PM deben ser
			// inactivados
			val list = otros.stream().filter(a -> a.getDireccionPm() != null).collect(toList());
			for (val t : list) {
				t.setActivo(false);
			}
		}
	}

	public void sortRoute(TrayectoViewModel item) {
		// TODO
	}

	public void save() {
		submit(new SaveCommand());
	}

	@Autowired
	private PasajeroDireccionRepository pasajeroDireccionRepository;

	private final class SaveCommand extends Command<String> {

		@Override
		protected void test(List<String> errors) {
			val trayectosVacios = getModels().stream().filter(a -> a.isTrayectoVacio()).count();
			if (trayectosVacios > 0) {
				val sb = new StringBuilder();
				sb.append("Ha creado ");
				sb.append(trayectosVacios);
				sb.append(" ");
				sb.append("trayecto");
				if (trayectosVacios > 1) {
					sb.append("s");
				}
				sb.append(", pero no se le");
				if (trayectosVacios > 1) {
					sb.append("s");
				}
				sb.append(" ha asociado al menos una dirección.");
				errors.add(sb.toString());
			}

			val trayectos = getModels().stream().filter(a -> a.isModificado() && a.isActivo() && a.getRutaId() != null)
					.collect(toList());
			if (!trayectos.isEmpty()) {
				val rutasConCapacidad = rutaService.findAllRutasConCapacidadByInstitucionId(profile.getInstitucionId(),
						getPasajero().getPasajeroId());

				for (val trayecto : trayectos) {
					val rutaId = trayecto.getRutaId();
					if (rutaId != null) {
						val optional = rutasConCapacidad.stream().filter(a -> a.getId().equals(rutaId)).findFirst();
						if (optional.isPresent()) {
							val ruta = optional.get();
							if (trayecto.isTieneDireccionAm() && ruta.getDisponibilidadAm() <= 0) {
								val format = "En la ruta %s, no hay cupo para el trayecto %s";
								errors.add(String.format(format, ruta.getDescripcion(),
										CourseType.SENTIDO_IDA.getDescripcion()));
							}
							if (trayecto.isTieneDireccionPm() && ruta.getDisponibilidadPm() <= 0) {
								val format = "En la ruta %s, no hay cupo para el trayecto %s";
								errors.add(String.format(format, ruta.getDescripcion(),
										CourseType.SENTIDO_RETORNO.getDescripcion()));
							}
						}
					}
				}
			}
		}

		@Transactional(readOnly = false)
		@Override
		protected void execute() {
			// crear pasajero
			if (getPasajero().getPasajeroId() == null) {
				val model = new PasajeroDto();
				model.setEstadoId(EstadoDireccionDto.ESTADO_NO_GEOREFERENCIADA);
				model.setInstitucionId(profile.getInstitucionId());
				model.setUsuarioId(getUsuarioId());
				model.setEstadoId(EstadoPasajeroDto.ESTADO_INACTIVO);

				val id = getPasajeroService().create(model).getId();
				getPasajero().setPasajeroId(id);
			}

			delete();
			// crear direcciones
			getPrimary().stream().filter(a -> a.isModificado()).forEach(a -> {
				if (a.getDireccionId() == null) {
					val model = new DireccionDto();
					model.setEstadoId(EstadoDireccionDto.ESTADO_NO_GEOREFERENCIADA);
					model.setInstitucionId(profile.getInstitucionId());
					model.setPaisId(profile.getPaisId());

					model.setDepartamentoId(a.getDepartamentoId());
					model.setCiudadId(a.getCiudadId());
					model.setDireccion(a.getDireccion());

					val id = getDireccionService().create(model).getId();
					a.setDireccionId(id);
				} else {
					val model = getDireccionService().findById(a.getDireccionId()).get();
					model.setDepartamentoId(a.getDepartamentoId());
					model.setCiudadId(a.getCiudadId());
					model.setDireccion(a.getDireccion());
					if (!a.isGeoCodificada()) {
						model.setX(null);
						model.setY(null);
					}

					getDireccionService().update(model);
				}
			});

			getModels().stream().filter(TrayectoViewModel::isModificado).forEach(trayecto -> {
				if (trayecto.isTieneDireccionAm()) {
					save(trayecto, trayecto.getDireccionAm());
				}
				if (trayecto.isTieneDireccionPm()) {
					save(trayecto, trayecto.getDireccionPm());
				}
			});

			reset();
		}

		private void save(TrayectoViewModel trayecto, DireccionViewModel model) {
			if (trayecto.isActivoModificado() || trayecto.isRutaModificado() || model.isModificado()) {
				if (model.getId() == null) {
					val entity = new PasajeroDireccion();

					entity.setPasajeroId(getPasajero().getPasajeroId());
					entity.setCorrelacion(trayecto.getCorrelacion());
					entity.setSentido(model.getSentido().getIntValue());
					entity.setDireccionId(model.getDireccionId());
					entity.setActivo(trayecto.isActivo());
					entity.setRutaId(trayecto.getRutaId());
					entity.setSecuencia(model.getSecuencia());

					getPasajeroDireccionRepository().save(entity);
				} else {
					val optional = getPasajeroDireccionRepository().findById(model.getId());

					if (optional.isPresent()) {
						val entity = optional.get();

						entity.setActivo(trayecto.isActivo());
						entity.setRutaId(trayecto.getRutaId());
						entity.setSecuencia(model.getSecuencia());

						getPasajeroDireccionRepository().save(entity);
					}
				}
			}
		}

		private void delete() {
			for (val deleted : getDeleted()) {
				pasajeroDireccionRepository.deleteById(deleted.getId());
				getDireccionService().delete(deleted.getDireccionId(), deleted.getDireccionVersion());
			}
		}
	}
}
