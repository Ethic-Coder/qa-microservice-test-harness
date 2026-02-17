package com.jose.harness.api;

import com.jose.harness.domain.Task;
import com.jose.harness.domain.TaskIdempotency;
import com.jose.harness.domain.TaskIdempotencyRepository;
import com.jose.harness.domain.TaskRepository;
import jakarta.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HexFormat;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskController {

  private final TaskRepository taskRepository;
  private final TaskIdempotencyRepository idempotencyRepository;

  public TaskController(TaskRepository taskRepository, TaskIdempotencyRepository idempotencyRepository) {
    this.taskRepository = taskRepository;
    this.idempotencyRepository = idempotencyRepository;
  }

  @PostMapping("/tasks")
  @ResponseStatus(HttpStatus.CREATED)
  public TaskResponse create(
      @Valid @RequestBody CreateTaskRequest request,
      @RequestHeader(value = "Idempotency-Key", required = false) String idempotencyKey) {

    String normalizedTitle = request.title().trim();

    if (idempotencyKey != null && !idempotencyKey.isBlank()) {
      String key = idempotencyKey.trim();
      String requestHash = sha256(normalizedTitle);

      var existing = idempotencyRepository.findByIdempotencyKey(key);
      if (existing.isPresent()) {
        TaskIdempotency record = existing.get();
        if (!record.getRequestHash().equals(requestHash)) {
          throw new IdempotencyConflictException();
        }
        Task task = taskRepository.findById(record.getTaskId()).orElseThrow(() -> new TaskNotFoundException(record.getTaskId()));
        return new TaskResponse(task.getId(), task.getTitle());
      }

      Task saved = taskRepository.save(new Task(normalizedTitle));
      idempotencyRepository.save(new TaskIdempotency(key, requestHash, saved.getId()));
      return new TaskResponse(saved.getId(), saved.getTitle());
    }

    Task saved = taskRepository.save(new Task(normalizedTitle));
    return new TaskResponse(saved.getId(), saved.getTitle());
  }

  @GetMapping("/tasks/{id}")
  public TaskResponse getById(@PathVariable Long id) {
    Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
    return new TaskResponse(task.getId(), task.getTitle());
  }

  @GetMapping("/tasks")
  public List<TaskResponse> list(
      @RequestParam(defaultValue = "20") int limit,
      @RequestParam(defaultValue = "0") int offset,
      @RequestParam(required = false) String search) {

    if (limit < 1 || limit > 100) {
      throw new IllegalArgumentException("limit out of range");
    }
    if (offset < 0) {
      throw new IllegalArgumentException("offset out of range");
    }

    int page = offset / limit;
    int offsetWithinPage = offset % limit;

    Pageable pageable = PageRequest.of(page, limit);

    Page<Task> result;
    if (search != null && !search.isBlank()) {
      result = taskRepository.findByTitleContainingIgnoreCase(search.trim(), pageable);
    } else {
      result = taskRepository.findAll(pageable);
    }

    List<Task> content = result.getContent();
    if (offsetWithinPage > 0) {
      content = content.stream().skip(offsetWithinPage).toList();
    }

    return content.stream().map(t -> new TaskResponse(t.getId(), t.getTitle())).toList();
  }

  private static String sha256(String input) {
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
      return HexFormat.of().formatHex(hash);
    } catch (Exception e) {
      throw new IllegalStateException("Cannot hash request", e);
    }
  }
}
