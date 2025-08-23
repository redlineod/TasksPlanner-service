package com.redline.tasks.domain.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.redline.tasks.domain.entities.TaskPriority;
import com.redline.tasks.domain.entities.TaskStatus;

public record TaskDto(
        UUID id,
        String title,
        String description,
        LocalDateTime dueDate,
        TaskPriority priority,
        TaskStatus status) {

}
