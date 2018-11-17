package co.com.orbitta.cibercolegios.rutas.configuration;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import co.com.orbitta.core.web.client.configuration.RestTemplateConfiguration;


@Configuration
public class RestConfiguration extends RestTemplateConfiguration {

	@Override
	protected CloseableHttpClient getHttpClient() {
		return HttpClientBuilder.create().disableCookieManagement().build();
	}

	@Override
	public HttpComponentsClientHttpRequestFactory clientHttpRequestFactory() {
		return super.clientHttpRequestFactory();
	}
}
