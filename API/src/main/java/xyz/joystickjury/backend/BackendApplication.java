package xyz.joystickjury.backend;

import org.apache.log4j.BasicConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		BasicConfigurator.configure(); // Required by our logger
		SpringApplication.run(BackendApplication.class, args);
	}
	// TODO : Controller unit tests w MockMVC, Spring security JWT filter, using an ORM like Hibernate as an alternative to DAOs, dynamic entity filtering using Turkraft, pageable for collections + sorting, use a proper DTO mapper, Redis Caching, Swagger documentation

}
