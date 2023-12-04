package xyz.joystickjury.backend;

import org.apache.log4j.BasicConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		BasicConfigurator.configure();
		SpringApplication.run(BackendApplication.class, args);
	}
	// TODO : Controller unit tests

}
