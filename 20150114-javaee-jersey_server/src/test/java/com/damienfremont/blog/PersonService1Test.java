package com.damienfremont.blog;

import static com.jayway.restassured.RestAssured.expect;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.notNullValue;

import javax.servlet.ServletException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Les tests pourront s'executer au choix vers un serveur Tomcat local ou embarque dans le test  (c'est plus rapide et moins repetitif).
 */
public class PersonService1Test {

	/* EMBEDDED SERVER (TOMCAT) */ 
	
	private static EmbeddedServer server;

	@BeforeClass
	public static void startServer() throws ServletException {
		server = new EmbeddedServer(8080, "/20150114-javaee-jersey_server");
		server.start();
	}

	@AfterClass
	public static void stopServer() {
		server.stop();
	}
	
	/* TESTS */ 

	private static final String REST_API = "/20150114-javaee-jersey_server/api";

	@Test
	public void testPersonCreateSuccess() {
		expect()
			.statusCode(200)
			.body(notNullValue())
		.given()
			.contentType("application/json")
			.parameters(
					"firstName", "Padme",
					"lastName", "Amidala",
					"birthDate", "46 BBY")
		.when()
			.post(REST_API + "/persons");
	}

	@Test
	public void testPersonGetSuccess() {
		expect()
			.statusCode(200)
			.body("id", equalTo(1))
			.body("firstName", equalTo("Anakin"))
			.body("lastName", equalTo("Skywalker"))
			.body("birthDate", equalTo("41.9 BBY")) //
		.when()
			.get(REST_API + "/persons/id/1");
	}

	@Test
	public void testPersonGetAllSuccess() {
		expect()
			.statusCode(200)
			.body("id", hasItems(1, 2, 3)) //
		.when()
			.get(REST_API + "/persons");
	}

	@Test
	public void testPersonPostUpdateSuccess() {
		expect()
			.statusCode(204)
		.given()
			.contentType("application/json")
			.parameters(
					"firstName", "Anakin", 
					"lastName", "Skywalker",
					"birthDate", "41.9 BBY")
		.when()
			.post(REST_API + "/persons/id/1");
	}
	
	@Test
	public void testPersonDeleteSuccess() {
		expect()
			.statusCode(204)
		.when()
			.delete(REST_API + "/persons/id/3");
	}
}
