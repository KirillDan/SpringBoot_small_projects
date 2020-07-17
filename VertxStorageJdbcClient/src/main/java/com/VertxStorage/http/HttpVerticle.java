package com.VertxStorage.http;

import org.springframework.stereotype.Component;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

@Component
public class HttpVerticle extends AbstractVerticle {

	private static final Logger log = LoggerFactory.getLogger(HttpVerticle.class);

	public void start(Promise<Void> promise) {
		HttpServer server = vertx.createHttpServer();

		Router router = Router.router(vertx);
		router.get("/api/users/:id/name").handler(this::nameHandler);
		router.get("/api/users/:id/address").handler(this::addressHandler);
		router.get("/api/users/:id").handler(this::userHandler);
		router.post("/api/users").handler(this::createHandler);

		int portNumber = 8081;
		server.requestHandler(router).listen(portNumber, lh -> {
			if (lh.succeeded()) {
				log.info("Сервер запустился на порту" + portNumber);
				promise.complete();
			} else {
				promise.fail(lh.cause());
			}
		});
	}

	private void nameHandler(RoutingContext context) {
		String id = context.request().getParam("id");
		JsonObject request = new JsonObject().put("id", id);

		DeliveryOptions options = new DeliveryOptions().addHeader("action", "name");
		vertx.eventBus().request("storage.queue", request, options, rh -> {
			if (rh.succeeded()) {
				JsonObject body = (JsonObject) rh.result().body();
				HttpServerResponse response = context.response();
				response.putHeader("content-type", "text/plain");
				response.end(body.toString());
			} else {
				context.fail(rh.cause());
			}
		});
	}

	private void addressHandler(RoutingContext context) {
		String id = context.request().getParam("id");
		JsonObject request = new JsonObject().put("id", id);

		DeliveryOptions options = new DeliveryOptions().addHeader("action", "address");
		vertx.eventBus().request("storage.queue", request, options, rh -> {
			if (rh.succeeded()) {
				JsonObject body = (JsonObject) rh.result().body();
				HttpServerResponse response = context.response();
				response.putHeader("content-type", "text/plain");
				response.end(body.toString());
			} else {
				context.fail(rh.cause());
			}
		});
	}

	private void userHandler(RoutingContext context) {
		String id = context.request().getParam("id");
		JsonObject request = new JsonObject().put("id", id);

		DeliveryOptions options = new DeliveryOptions().addHeader("action", "user");
		vertx.eventBus().request("storage.queue", request, options, rh -> {
			if (rh.succeeded()) {
				JsonObject body = (JsonObject) rh.result().body();
				HttpServerResponse response = context.response();
				response.putHeader("content-type", "text/plain");
				response.end(body.toString());
			} else {
				context.fail(rh.cause());
			}
		});
	}

	private void createHandler(RoutingContext context) {
		context.request().bodyHandler(bodyHandler -> {
			DeliveryOptions options = new DeliveryOptions().addHeader("action", "create");
			vertx.eventBus().request("storage.queue", bodyHandler.toJsonObject(), options, rh -> {
				if (rh.succeeded()) {
					JsonObject body = (JsonObject) rh.result().body();					
					JsonObject req = new JsonObject().put("id", String.valueOf(body.getLong("id")));
					DeliveryOptions options2 = new DeliveryOptions().addHeader("action", "user");
					vertx.eventBus().request("storage.queue", req, options2, rh2 -> {
						if (rh2.succeeded()) {
							JsonObject body2 = (JsonObject) rh2.result().body();
							HttpServerResponse response = context.response();
							response.putHeader("content-type", "text/plain");
							response.end(body2.toString());
						} else {
							context.fail(rh2.cause());
						}
					});
				} else {
					context.fail(rh.cause());
				}
			});
		});
	}

}
