package co.com.orbitta.cibercolegios.web.views;

import javax.inject.Named;

import lombok.Data;

@Named
@Data
public class HelloWorld {

	private String firstName = "John";
	private String lastName = "Doe";

	public String showGreeting() {
		return "Hello " + firstName + " " + lastName + "!";
	}
}
