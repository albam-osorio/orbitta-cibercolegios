package com.tbf.cibercolegios.api.routes.configuration;

import java.util.HashMap;

import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tbf.cibercolegios.api.routes.web.utils.ViewScope;



@Configuration
public class JsfConfiguration {

	@Bean
	public static ViewScope viewScope() {
		return new ViewScope();
	}

	/**
	 * Allows the use of @Scope("view") on Spring @Component, @Service and @Controller
	 * beans
	 */
	@Bean
	public static CustomScopeConfigurer scopeConfigurer() {
		CustomScopeConfigurer configurer = new CustomScopeConfigurer();
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("view", viewScope());
		configurer.setScopes(hashMap);
		return configurer;
	}

}
