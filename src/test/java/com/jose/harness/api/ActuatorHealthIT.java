package com.jose.harness.api;

import static io.restassured.RestAssured.given;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ActuatorHealthIT extends com.jose.harness.it.BaseIT {

  @LocalServerPort private int port;

  @BeforeEach
  void setUp() {
    RestAssured.baseURI = "http://localhost";
    RestAssured.port = port;
  }

  @Test
  void actuatorHealthIsUp() {
    given().when().get("/actuator/health").then().statusCode(200);
  }
}
