package com.jose.harness.api;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import com.jose.harness.it.BaseIT;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IdempotencyConflictIT extends BaseIT {

  @LocalServerPort private int port;

  @BeforeEach
  void setUp() {
    RestAssured.baseURI = "http://localhost";
    RestAssured.port = port;
  }

  @Test
  void sameIdempotencyKeyWithDifferentPayloadReturns409() {
    String key = "same-key";

    given()
        .header("Idempotency-Key", key)
        .contentType("application/json")
        .body("{\"title\":\"Uno\"}")
        .when()
        .post("/tasks")
        .then()
        .statusCode(201);

    given()
        .header("Idempotency-Key", key)
        .contentType("application/json")
        .body("{\"title\":\"Dos\"}")
        .when()
        .post("/tasks")
        .then()
        .statusCode(409)
        .body("detail", equalTo("Idempotency key conflict"));
  }
}
