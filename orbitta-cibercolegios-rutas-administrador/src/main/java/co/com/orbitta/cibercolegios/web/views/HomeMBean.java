package co.com.orbitta.cibercolegios.web.views;

import java.io.Serializable;

import javax.faces.view.ViewScoped;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Component
@ViewScoped
public class HomeMBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final int USUARIOS = 3;

	private static final int SEGUIMIENTO = 2;

	private static final int ADMINISTRACION = 1;

	private Integer opcion;

	public String submit() {
		String result = "home.xhmtl";
		if (this.getOpcion() != null) {
			switch (this.getOpcion()) {
			case ADMINISTRACION:
				result = "rutas.xhmtl";
				break;
			case SEGUIMIENTO:
				result = "seguimiento.xhmtl";
				break;
			case USUARIOS:
				result = "usuarios.xhmtl";
				break;
			default:
				break;
			}
		}
		return result;
	}
}
