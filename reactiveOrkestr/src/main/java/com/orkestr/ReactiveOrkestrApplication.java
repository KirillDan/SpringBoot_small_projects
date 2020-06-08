package com.orkestr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class ReactiveOrkestrApplication {

	@Bean
	WebClient webClient() {
		return WebClient.create();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(ReactiveOrkestrApplication.class, args);
	}

}
