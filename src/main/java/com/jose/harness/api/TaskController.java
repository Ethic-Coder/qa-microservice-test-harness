package com.jose.harness.api;

import com.jose.harness.domain.Task;
import com.jose.harness.domain.TaskRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
}
