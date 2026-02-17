package com.jose.harness.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
  Page<Task> findByTitleContainingIgnoreCase(String title, Pageable pageable);
}
