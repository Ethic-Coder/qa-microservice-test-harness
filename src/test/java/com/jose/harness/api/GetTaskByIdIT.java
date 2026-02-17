package com.jose.harness.api;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import com.jose.harness.it.BaseIT;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GetTaskByIdIT extends BaseIT {

  @LocalServerPort private int port;

  @BeforeEach
  void setUp() {
    RestAssured.baseURI = "http://localhost";
    RestAssured.port = port;
  }

  @Test
  void getByIdReturns200WhenExists() {
    Long id =
        given()
            .contentType("application/json")
            .body("{\"title\":\"Tarea A\"}")
            .when()
            .post("/tasks")
            .then()
            .statusCode(201)
            .body("id", notNullValue())
            .extract()
            .jsonPath()
            .getLong("id");

    given()
        .when()
        .get("/tasks/" + id)
        .then()
        .statusCode(200)
        .body("id", equalTo(id.intValue()))
        .body("title", equalTo("Tarea A"));
  }

  @Test
  void getByIdReturns404WithExactMessage() {
    given()
        .when()
        .get("/tasks/99999999")
        .then()
        .statusCode(404)
        .body("detail", equalTo("Task not found"));
  }
}
