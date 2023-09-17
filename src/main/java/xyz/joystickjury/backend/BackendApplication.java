package xyz.joystickjury.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) { SpringApplication.run(BackendApplication.class, args); }
	// TODO : AUTH Controller for logins and registrations using userCredentials validated with @Valid & @Pattern, ControllerAdvice, Controller unit tests

}
