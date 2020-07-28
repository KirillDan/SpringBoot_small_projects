package ru.sbrf.sb;

import org.keycloak.admin.client.Keycloak;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EmployeeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeServiceApplication.class, args);
	}
	
	@Bean
	public Keycloak keycloak() {
		return Keycloak.getInstance(
			    "http://localhost:8080/auth",
			    "master",
			    "admin",
			    "admin",
			    "admin-cli",
			    "117b80c5-10b0-453b-bafc-d84e5b1d5b5b");
	}

}
