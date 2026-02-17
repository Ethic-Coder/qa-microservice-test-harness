package com.jose.harness.it;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * Base class for integration tests that need a real PostgreSQL instance.
 *
 * <p>Testcontainers will start PostgreSQL automatically and Spring will connect to it using these
 * dynamic properties.
 */
@Testcontainers
public abstract class BaseIT {

  static final PostgreSQLContainer<?> POSTGRES =
      new PostgreSQLContainer<>("docker.io/library/postgres:16")
          .withDatabaseName("harness")
          .withUsername("harness")
          .withPassword("harness");

  static {
    POSTGRES.start();
  }

  @DynamicPropertySource
  static void registerDatasourceProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
    registry.add("spring.datasource.username", POSTGRES::getUsername);
    registry.add("spring.datasource.password", POSTGRES::getPassword);

    // Ensure Flyway runs against the container DB
    registry.add("spring.flyway.enabled", () -> "true");
  }
}
