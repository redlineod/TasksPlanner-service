package com.redline.tasks.mappers.impl;

import org.springframework.stereotype.Component;

import com.redline.tasks.domain.dto.TaskDto;
import com.redline.tasks.domain.entities.Task;
import com.redline.tasks.mappers.TaskMapper;

@Component
public class TaskMapperImpl implements TaskMapper {

    @Override
    public Task fromDto(TaskDto dto) {
        if (dto == null) {
            return null;
        }

        Task task = new Task(
                dto.id(),
                dto.title(),
                dto.description(),
                dto.dueDate(),
                dto.status(),
                dto.priority(),
                null,
                null,
                null);

        return task;
    }

    @Override
    public TaskDto toDto(Task entity) {
        if (entity == null) {
            return null;
        }

        TaskDto dto = new TaskDto(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getDueDate(),
                entity.getPriority(),
                entity.getStatus());

        return dto;
    }

}
