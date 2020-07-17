package com.VertxStorage;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

import com.VertxStorage.database.DatabaseVerticle;
import com.VertxStorage.http.HttpVerticle;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;

@Configuration
@SpringBootApplication
public class VertxStorageJdbcClientApplication {

	@Autowired
	private HttpVerticle httpVerticle;
	
	@Autowired
	private DatabaseVerticle databaseVerticle;
	
	public static void main(String[] args) {
		SpringApplication.run(VertxStorageJdbcClientApplication.class, args);
	}

	@PostConstruct
	public void deployVerticle() {
		final Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(databaseVerticle, new DeploymentOptions().setInstances(1));
		vertx.deployVerticle(httpVerticle, new DeploymentOptions().setInstances(1));
	}
}
