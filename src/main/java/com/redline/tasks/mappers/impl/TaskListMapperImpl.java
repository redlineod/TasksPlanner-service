package com.redline.tasks.mappers.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.redline.tasks.domain.dto.TaskListDto;
import com.redline.tasks.domain.entities.Task;
import com.redline.tasks.domain.entities.TaskList;
import com.redline.tasks.domain.entities.TaskStatus;
import com.redline.tasks.mappers.TaskListMapper;
import com.redline.tasks.mappers.TaskMapper;

@Component
public class TaskListMapperImpl implements TaskListMapper {

    private final TaskMapper taskMapper;

    public TaskListMapperImpl(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    @Override
    public TaskList fromDto(TaskListDto dto) {
        return new TaskList(
                dto.id(),
                dto.title(),
                dto.description(),
                Optional.ofNullable(dto.tasks())
                        .map(tasks -> tasks.stream()
                                .map(taskMapper::fromDto)
                                .toList())
                        .orElse(null),
                null,
                null);
    }

    @Override
    public TaskListDto toDto(TaskList entity) {
        return new TaskListDto(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                Optional.ofNullable(entity.getTasks())
                        .map(List::size)
                        .orElse(0),
                calculateProgress(entity.getTasks()),
                Optional.ofNullable(entity.getTasks())
                        .map(tasks -> tasks.stream().map(taskMapper::toDto).toList()).orElse(null));
    }

    private Double calculateProgress(List<Task> tasks) {
        if (tasks == null) {
            return null;
        }

        long closedTasksCount = tasks.stream().filter(task -> task.getStatus() == TaskStatus.CLOSED).count();

        return (double) closedTasksCount / tasks.size();
    }

}
