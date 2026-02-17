package com.jose.harness.api;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;

import com.jose.harness.it.BaseIT;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CreateTaskIT extends BaseIT {

  @LocalServerPort private int port;

  @BeforeEach
  void setUp() {
    RestAssured.baseURI = "http://localhost";
    RestAssured.port = port;
  }

  @Test
  void createTaskReturns201AndBody() {
    given()
        .contentType("application/json")
        .body("{\"title\":\"Comprar pan\"}")
        .when()
        .post("/tasks")
        .then()
        .statusCode(201)
        .body("id", notNullValue())
        .body("title", equalTo("Comprar pan"));
  }
}
