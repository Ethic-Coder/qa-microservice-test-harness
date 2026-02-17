package com.jose.harness.api;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

  @ExceptionHandler(TaskNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public Map<String, String> handleTaskNotFound() {
    return Map.of("detail", "Task not found");
  }

  @ExceptionHandler(IdempotencyConflictException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public Map<String, String> handleIdempotencyConflict() {
    return Map.of("detail", "Idempotency key conflict");
  }
}
