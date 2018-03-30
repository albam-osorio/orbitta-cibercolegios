package co.com.orbitta.cibercolegios.rutas.client.components;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import co.com.orbitta.cibercolegios.constants.RestConstants;
import co.com.orbitta.core.web.client.configuration.RestProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Component
@ConfigurationProperties(prefix = RestConstants.configurationPropertiesPrefix)
@Getter
@Setter
@ToString
@Validated
public class RutasRestProperties implements RestProperties {

	private String hostname;
	
	private String basePath;
}

