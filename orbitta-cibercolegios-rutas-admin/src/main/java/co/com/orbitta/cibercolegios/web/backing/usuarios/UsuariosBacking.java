package co.com.orbitta.cibercolegios.web.backing.usuarios;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RestController;

import co.com.orbitta.cibercolegios.service.ExcelWorkSheetReader;
import co.com.orbitta.cibercolegios.web.backing.sesion.UserPreferences;
import lombok.Getter;
import lombok.Setter;

@RestController
@Scope("view")
@Setter
@Getter
public class UsuariosBacking implements Serializable {
	private static final long serialVersionUID = 1L;

	@Autowired
	private UserPreferences preferences;

	@Autowired
	private ExcelWorkSheetReader reader;


}
