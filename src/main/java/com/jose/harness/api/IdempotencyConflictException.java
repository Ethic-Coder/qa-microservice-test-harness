package com.jose.harness.api;

public class IdempotencyConflictException extends RuntimeException {
  public IdempotencyConflictException() {
    super("Idempotency key conflict");
  }
}
