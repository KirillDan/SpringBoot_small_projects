package com.VertxStorage.database;

import org.springframework.stereotype.Component;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.ResultSet;
import io.vertx.ext.sql.SQLClient;
import io.vertx.ext.sql.SQLConnection;
import io.vertx.ext.sql.UpdateResult;

@Component
public class DatabaseVerticle extends AbstractVerticle {

	private static final Logger log = LoggerFactory.getLogger(DatabaseVerticle.class);

	public static final String CONFIG_QUEUE = "storage.queue";
	private static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS user";
	private static final String SQL_CREATE_USER_TABLE = "create table if not exists user (Id IDENTITY NOT NULL PRIMARY KEY, NAME varchar(100) NOT NULL, ADDRESS varchar(100) NOT NULL);";
	private static final String SQL_GET_USER = "select * from user where id = ?";
	private static final String SQL_CREATE_USER = "insert into user (NAME, ADDRESS) values (?, ?);";

	private SQLClient client;

	public void start(Promise<Void> promise) {
		JsonObject config = new JsonObject().put("url", "jdbc:h2:mem:testdb").put("driver_class", "org.h2.Driver")
				.put("user", "sa").put("max_pool_size", 10);

		client = JDBCClient.createShared(vertx, config);
		client.getConnection(ar -> {
			if (ar.failed()) {
				promise.fail(ar.cause());
			} else {
				SQLConnection connection = ar.result();
				connection.execute(SQL_DROP_TABLE, handler -> {
					if (handler.failed()) {
						promise.fail(handler.cause());
					} else {
						connection.execute(SQL_CREATE_USER_TABLE, handler2 -> {
							connection.close();
							if (handler2.failed()) {
								promise.fail(handler.cause());
							} else {
								vertx.eventBus().consumer("storage.queue", this::onMessage);
								promise.complete();
							}
						});
					}
				});
			}
		});
	}

	public void onMessage(Message<JsonObject> message) {
		if (!message.headers().contains("action")) {
			message.fail(400, message.toString());
			log.error("Запрос не содержит заголовок action");
			return;
		}
		String action = message.headers().get("action");
		switch (action) {
		case "name":
			fetchName(message);
			break;
		case "address":
			fetchAddress(message);
			break;
		case "user":
			fetchUser(message);
			break;
		case "create":
			fetchCreate(message);
			break;
		default:
			message.fail(400, "Bad action: " + action);
		}
	}

	private void fetchName(Message<JsonObject> message) {
		JsonArray params = new JsonArray().add(message.body().getString("id"));
		client.queryWithParams(SQL_GET_USER, params, get -> {
			if (get.succeeded()) {
				ResultSet resultSet = get.result();
				JsonObject responseJson = new JsonObject();
				if (resultSet.getNumRows() == 0) {
					responseJson.put("id", "");
					responseJson.put("name", "");
				} else {
					JsonArray row = resultSet.getResults().get(0);
					responseJson.put("id", row.getLong(0));
					responseJson.put("name", row.getString(1));
				}
				message.reply(responseJson);
			} else {
				message.fail(400, "Не удалось получить пользователя");
			}
		});
	}

	private void fetchAddress(Message<JsonObject> message) {
		JsonArray params = new JsonArray().add(message.body().getString("id"));
		client.queryWithParams(SQL_GET_USER, params, get -> {
			if (get.succeeded()) {
				ResultSet resultSet = get.result();
				JsonObject responseJson = new JsonObject();
				if (resultSet.getNumRows() == 0) {
					responseJson.put("id", "");
					responseJson.put("address", "");
				} else {
					JsonArray row = resultSet.getResults().get(0);
					responseJson.put("id", row.getLong(0));
					responseJson.put("address", row.getString(2));
				}
				message.reply(responseJson);
			} else {
				message.fail(400, "Не удалось получить пользователя");
			}
		});
	}

	private void fetchUser(Message<JsonObject> message) {
		JsonArray params = new JsonArray().add(message.body().getString("id"));
		client.queryWithParams(SQL_GET_USER, params, get -> {
			if (get.succeeded()) {
				ResultSet resultSet = get.result();
				JsonObject responseJson = new JsonObject();
				if (resultSet.getNumRows() == 0) {
					System.err.println("Пользователь не найден");
					responseJson.put("id", "");
					responseJson.put("name", "");
					responseJson.put("address", "");
				} else {
					JsonArray row = resultSet.getResults().get(0);
					responseJson.put("id", row.getLong(0));
					responseJson.put("name", row.getString(1));
					responseJson.put("address", row.getString(2));
				}
				message.reply(responseJson);
			} else {
				message.fail(400, "Не удалось получить пользователя");
			}
		});
	}

	private void fetchCreate(Message<JsonObject> message) {
		JsonObject request = message.body();
		JsonArray params = new JsonArray().add(message.body().getString("name"))
				.add(message.body().getString("address"));
		client.getConnection(connection -> {
			if (connection.failed()) {
				message.fail(400, connection.cause().toString());
			} else {
				connection.result().updateWithParams(SQL_CREATE_USER, params, res -> {
					if (res.succeeded()) {
						UpdateResult updateResult = res.result();
						message.reply(new JsonObject().put("id", updateResult.getKeys().getLong(0)));
					} else {
						message.fail(400, "Не удалось создать пользователя");
					}
				});
			}
		});	
	}

}
