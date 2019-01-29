package co.com.orbitta.cibercolegios.web.backing.sesion;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import lombok.Getter;
import lombok.Setter;

@SessionScope
@Component
@Setter
@Getter
public class UserPreferences {

	Integer usuarioId;

	Integer institucionId;

	Integer paisId;
}
