package co.com.orbitta.cibercolegios.ciber.client.components;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import co.com.orbitta.cibercolegios.ciber.constants.CiberRestConstants;
import co.com.orbitta.core.web.client.configuration.RestProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Component
@ConfigurationProperties(prefix = CiberRestConstants.configurationPropertiesPrefix)
@Getter
@Setter
@ToString
@Validated
public class CiberRestProperties implements RestProperties {

	private String basePath;
}

