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
class IdempotencySameKeyIT extends BaseIT {

  @LocalServerPort private int port;

  @BeforeEach
  void setUp() {
    RestAssured.baseURI = "http://localhost";
    RestAssured.port = port;
  }

  @Test
  void sameIdempotencyKeyAndPayloadReturnsSameResult() {
    String key = "abc-123";

    long firstId =
        given()
            .header("Idempotency-Key", key)
            .contentType("application/json")
            .body("{\"title\":\"Tarea idem\"}")
            .when()
            .post("/tasks")
            .then()
            .statusCode(201)
            .extract()
            .jsonPath()
            .getLong("id");

    long secondId =
        given()
            .header("Idempotency-Key", key)
            .contentType("application/json")
            .body("{\"title\":\"Tarea idem\"}")
            .when()
            .post("/tasks")
            .then()
            .statusCode(201)
            .body("id", equalTo((int) firstId))
            .body("title", equalTo("Tarea idem"))
            .extract()
            .jsonPath()
            .getLong("id");

    // Ensure IDs are equal
    org.junit.jupiter.api.Assertions.assertEquals(firstId, secondId);
  }
}
