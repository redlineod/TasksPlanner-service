package com.redline.tasks.mappers;

import com.redline.tasks.domain.dto.TaskDto;
import com.redline.tasks.domain.entities.Task;

public interface TaskMapper {

    Task fromDto(TaskDto dto);

    TaskDto toDto(Task entity);
}
