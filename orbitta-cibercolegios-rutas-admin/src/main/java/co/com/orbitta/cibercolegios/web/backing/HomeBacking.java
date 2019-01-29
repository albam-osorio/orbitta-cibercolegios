package co.com.orbitta.cibercolegios.web.backing;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.annotation.RequestScope;

import co.com.orbitta.cibercolegios.web.backing.rutas.RutasConstantes;
import co.com.orbitta.cibercolegios.web.backing.sesion.UserPreferences;
import co.com.orbitta.cibercolegios.web.backing.usuarios.UsuariosConstantes;
import lombok.Getter;
import lombok.Setter;
import lombok.val;

@Component
@RequestScope
@Setter
@Getter
public class HomeBacking implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String PARAM_USUARIO = "#{request.getParameter('usuario')}";

	private static final String PARAM_INSTITUCION = "#{request.getParameter('institucion')}";

	private static final String PARAM_PAIS = "#{request.getParameter('pais')}";

	private static final int USUARIOS = 1;

	private static final int ADMINISTRACION = 2;

	private static final int SEGUIMIENTO = 3;

	@Value(PARAM_USUARIO)
	private Integer usuarioId;

	@Value(PARAM_INSTITUCION)
	private Integer institucionId;

	@Value(PARAM_PAIS)
	private Integer paisId;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private UserPreferences preferences;

	private Integer opcion;

	@PostConstruct
	public void init() {
		if (preferences.getUsuarioId() == null) {
			preferences.setUsuarioId(usuarioId);
			preferences.setInstitucionId(institucionId);
			preferences.setPaisId(paisId);
		}
	}

	public String submit() {
		String result = null;
		if (this.getOpcion() != null) {
			switch (this.getOpcion()) {
			case ADMINISTRACION:
				result = RutasConstantes.URL_LIST;
				break;
			case USUARIOS:
				result = UsuariosConstantes.URL_LIST;
				break;
			case SEGUIMIENTO:
				//result = "seguimiento?faces-redirect=true";
				break;
			default:
				break;
			}
		}

		if (result == null) {
			result = getCurrentUrl();
		}
		return result;
	}

	private String getCurrentUrl() {
		String requestURI = request.getRequestURI();
		String queryString = request.getQueryString();

		val result = StringUtils.isEmpty(queryString) ? requestURI : requestURI + "?" + queryString;
		return result;
	}
}
