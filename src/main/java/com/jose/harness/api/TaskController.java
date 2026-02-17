package com.jose.harness.api;

import com.jose.harness.domain.Task;
import com.jose.harness.domain.TaskRepository;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskController {

  private final TaskRepository taskRepository;

  public TaskController(TaskRepository taskRepository) {
    this.taskRepository = taskRepository;
  }

  @PostMapping("/tasks")
  @ResponseStatus(HttpStatus.CREATED)
  public TaskResponse create(@Valid @RequestBody CreateTaskRequest request) {
    Task task = new Task(request.title().trim());
    Task saved = taskRepository.save(task);
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

    // We requested a page with size=limit; offsetWithinPage matters only if offset isn't multiple of limit.
    // Apply it by slicing in-memory for the first page segment.
    List<Task> content = result.getContent();
    if (offsetWithinPage > 0) {
      content = content.stream().skip(offsetWithinPage).toList();
    }

    return content.stream().map(t -> new TaskResponse(t.getId(), t.getTitle())).toList();
  }
}
