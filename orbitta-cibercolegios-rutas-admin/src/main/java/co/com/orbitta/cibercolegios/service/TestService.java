package co.com.orbitta.cibercolegios.service;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * Simple service to be used both by the JSF Controller and the Spring MVC
 * Service to demonstrate that they are working together in the same context.
 * 
 * @author Caleb
 */
@Service
@Scope("session")
public class TestService {
	private int counter = 0;

	@PostConstruct
	public void init() {
		System.out.println("TestService: Upps i did it again");
	}
	
	public String getMessage() {
		return "This is my message " + counter++;
	}
}
