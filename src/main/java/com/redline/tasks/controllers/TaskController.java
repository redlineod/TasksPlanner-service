package com.redline.tasks.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.redline.tasks.domain.dto.TaskDto;
import com.redline.tasks.domain.entities.Task;
import com.redline.tasks.mappers.TaskMapper;
import com.redline.tasks.services.TaskService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping(path = "api/task-lists/{task_list_id}/tasks")
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    public TaskController(TaskService taskService, TaskMapper taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    @GetMapping
    public List<TaskDto> listTask(@PathVariable("task_list_id") UUID taskListId) {
        List<Task> tasks = taskService.listTasks(taskListId);
        return tasks.stream()
                .map(taskMapper::toDto)
                .toList();
    }

    @PostMapping
    public TaskDto createTask(@PathVariable("task_list_id") UUID taskListId, @RequestBody TaskDto taskDto) {
        Task createdTask = taskService.createTask(taskListId, taskMapper.fromDto(taskDto));
        return taskMapper.toDto(createdTask);
    }

    @GetMapping("/{task_id}")
    public Optional<TaskDto> getMethodName(@PathVariable("task_list_id") UUID taskListId,
            @PathVariable("task_id") UUID taskId) {
        return taskService.getTask(taskListId, taskId).map(taskMapper::toDto);
    }

    @PutMapping("/{task_id}")
    public TaskDto updateTask(@PathVariable("task_list_id") UUID taskListId,
            @PathVariable("task_id") UUID taskId,
            @RequestBody TaskDto task) {
        Task updatedTask = taskService.updateTask(taskListId, taskId, taskMapper.fromDto(task));

        return taskMapper.toDto(updatedTask);
    }

    @DeleteMapping("/{task_id}")
    public void deleteTask(@PathVariable("task_list_id") UUID taskListId,
            @PathVariable("task_id") UUID taskId) {
        taskService.deleteTask(taskListId, taskId);
    }
}
