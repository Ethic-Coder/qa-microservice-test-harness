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
class ListTasksPaginationIT extends BaseIT {

  @LocalServerPort private int port;

  @BeforeEach
  void setUp() {
    RestAssured.baseURI = "http://localhost";
    RestAssured.port = port;
  }

  @Test
  void listRespectsLimitAndOffset() {
    for (int i = 1; i <= 5; i++) {
      given()
          .contentType("application/json")
          .body("{\"title\":\"T" + i + "\"}")
          .post("/tasks")
          .then()
          .statusCode(201);
    }

    given().when().get("/tasks?limit=2&offset=0").then().statusCode(200).body("$", hasSize(2));

    given().when().get("/tasks?limit=2&offset=2").then().statusCode(200).body("$", hasSize(2));

    given()
        .when()
        .get("/tasks?limit=2&offset=4")
        .then()
        .statusCode(200)
        .body("$", hasSize(1))
        .body("[0].title", equalTo("T5"));
  }
}
