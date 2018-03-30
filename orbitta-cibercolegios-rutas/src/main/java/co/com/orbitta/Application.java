package co.com.orbitta;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class Application {

	public static final String SPRING_CONFIG_NAME_APPLICATION = "application";

	public static void main(String[] args) {
		// @formatter:off
		new SpringApplicationBuilder(Application.class)
		.properties("spring.config.name:"+SPRING_CONFIG_NAME_APPLICATION)
		.build()
		.run(args);
		// @formatter:on
	}
}
