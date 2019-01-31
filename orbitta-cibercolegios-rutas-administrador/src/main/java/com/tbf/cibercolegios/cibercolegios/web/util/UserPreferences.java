package com.tbf.cibercolegios.cibercolegios.web.util;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import lombok.Getter;
import lombok.Setter;

@SessionScope
@Component
@Setter
@Getter
public class UserPreferences {

	String sesionId;
	
	Integer usuarioId;

	Integer institucionId;

	Integer jornadaId;
	
	Integer paisId;
}
