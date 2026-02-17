package com.jose.harness.api;

public class TaskNotFoundException extends RuntimeException {
  public TaskNotFoundException(Long id) {
    super("Task not found: " + id);
  }
}
