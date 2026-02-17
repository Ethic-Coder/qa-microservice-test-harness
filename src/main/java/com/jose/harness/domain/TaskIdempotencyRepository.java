package com.jose.harness.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskIdempotencyRepository extends JpaRepository<TaskIdempotency, Long> {
  Optional<TaskIdempotency> findByIdempotencyKey(String idempotencyKey);
}
