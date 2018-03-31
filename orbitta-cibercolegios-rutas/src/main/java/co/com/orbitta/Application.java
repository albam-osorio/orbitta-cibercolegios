package co.com.orbitta;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import co.com.orbitta.cibercolegios.rutas.service.api.tracking.TrackingService;
import lombok.val;

@SpringBootApplication
public class Application implements CommandLineRunner {

	public static final String SPRING_CONFIG_NAME_APPLICATION = "application";

	public static void main(String[] args) {
		// @formatter:off
		new SpringApplicationBuilder(Application.class)
		.properties("spring.config.name:"+SPRING_CONFIG_NAME_APPLICATION)
		.build()
		.run(args);
		// @formatter:on
	}

	@Autowired
	TrackingService service;

	@Override
	public void run(String... args) throws Exception {
		System.out.println("");
		val result = service.iniciarRecorrido(75, 1, BigDecimal.valueOf(0.0), BigDecimal.valueOf(0.0));
		System.out.println(result);
	}
}
