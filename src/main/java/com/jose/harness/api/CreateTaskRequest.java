package com.jose.harness.api;

import jakarta.validation.constraints.NotBlank;

public record CreateTaskRequest(@NotBlank String title) {}
