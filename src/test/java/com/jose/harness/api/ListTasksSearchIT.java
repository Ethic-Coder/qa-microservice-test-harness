package com.jose.harness.api;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import com.jose.harness.it.BaseIT;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ListTasksSearchIT extends BaseIT {

  @LocalServerPort private int port;

  @BeforeEach
  void setUp() {
    RestAssured.baseURI = "http://localhost";
    RestAssured.port = port;
  }

  @Test
  void searchIsCaseInsensitiveAndFiltersByTitle() {
    given()
        .contentType("application/json")
        .body("{\"title\":\"Comprar Pan\"}")
        .post("/tasks")
        .then()
        .statusCode(201);
    given()
        .contentType("application/json")
        .body("{\"title\":\"Lavar coche\"}")
        .post("/tasks")
        .then()
        .statusCode(201);

    given()
        .when()
        .get("/tasks?search=pan")
        .then()
        .statusCode(200)
        .body("$", hasSize(1))
        .body("[0].title", equalTo("Comprar Pan"));

    given()
        .when()
        .get("/tasks?search=PAN")
        .then()
        .statusCode(200)
        .body("$", hasSize(1))
        .body("[0].title", equalTo("Comprar Pan"));
  }
}
