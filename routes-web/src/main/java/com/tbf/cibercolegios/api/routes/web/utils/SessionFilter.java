package com.tbf.cibercolegios.api.routes.web.utils;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.tbf.cibercolegios.api.routes.web.WebSettings;

import lombok.extern.slf4j.Slf4j;

@Component
@Order(1)
@Slf4j
public class SessionFilter implements Filter {

	@Autowired
	private UserProfile profile;

	@Value(WebSettings.PARAM_SESION_URL_REDIRECCCION_SESION_FINALIZADA)
	private String url;

	@Value(WebSettings.PARAM_SESION_TIEMPO_MINIMO_RENOVACION_SESION)
	private Integer timeout;
	
	@Override
	public void init(final FilterConfig filterConfig) throws ServletException {
		log.info("Initializing filter :{}", this);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		boolean success = true;

		boolean testUrl = true;
		if (req.getRequestURI().contains("javax.faces.resource")) {
			testUrl = false;
		}

		if (testUrl) {
			success = profile.renew(timeout);
			//TODO
			success = true;
		}

		if (success) {
			chain.doFilter(request, response);
		} else {
			res.sendRedirect(url);
		}
	}

	@Override
	public void destroy() {
		log.warn("Destructing filter :{}", this);
	}
}
