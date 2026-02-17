package com.jose.harness.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "task_idempotency")
public class TaskIdempotency {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "idempotency_key", nullable = false, unique = true)
  private String idempotencyKey;

  @Column(name = "request_hash", nullable = false)
  private String requestHash;

  @Column(name = "task_id", nullable = false)
  private Long taskId;

  protected TaskIdempotency() {
    // for JPA
  }

  public TaskIdempotency(String idempotencyKey, String requestHash, Long taskId) {
    this.idempotencyKey = idempotencyKey;
    this.requestHash = requestHash;
    this.taskId = taskId;
  }

  public String getIdempotencyKey() {
    return idempotencyKey;
  }

  public String getRequestHash() {
    return requestHash;
  }

  public Long getTaskId() {
    return taskId;
  }
}
