package com.redline.tasks.mappers;

import com.redline.tasks.domain.dto.TaskListDto;
import com.redline.tasks.domain.entities.TaskList;

public interface TaskListMapper {

    TaskList fromDto(TaskListDto dto);

    TaskListDto toDto(TaskList entity);
}
