package com.redline.tasks.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.redline.tasks.domain.entities.Task;
import com.redline.tasks.domain.entities.TaskList;
import com.redline.tasks.domain.entities.TaskPriority;
import com.redline.tasks.domain.entities.TaskStatus;
import com.redline.tasks.repositories.TaskListRepository;
import com.redline.tasks.repositories.TaskRepository;
import com.redline.tasks.services.TaskService;

import jakarta.transaction.Transactional;

@Service
public class TaskServiceImpl implements TaskService {

    TaskRepository taskRepository;
    TaskListRepository taskListRepository;

    public TaskServiceImpl(TaskRepository taskRepository, TaskListRepository taskListRepository) {
        this.taskRepository = taskRepository;
        this.taskListRepository = taskListRepository;
    }

    @Override
    public List<Task> listTasks(UUID taskListId) {
        return taskRepository.findByTaskListId(taskListId);
    }

    @Override
    public Task createTask(UUID taskListId, Task task) {
        if (task.getId() != null) {
            throw new IllegalArgumentException("New TaskList cannot have an ID");
        }

        if (task.getTitle() == null || task.getTitle().isBlank()) {
            throw new IllegalArgumentException("TaskList title cannot be null or empty");
        }

        TaskPriority priority = Optional.ofNullable(task.getPriority()).orElse(TaskPriority.MEDIUM);
        TaskStatus status = TaskStatus.OPEN;

        TaskList taskList = taskListRepository.findById(taskListId)
                .orElseThrow(() -> new IllegalArgumentException("TaskList with ID " + taskListId + " does not exist"));

        LocalDateTime now = LocalDateTime.now();
        return taskRepository.save(new Task(
                null,
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                status,
                priority,
                taskList,
                now,
                now));
    }

    @Override
    public Optional<Task> getTask(UUID taskListId, UUID taskId) {
        return taskRepository.findByTaskListIdAndId(taskListId, taskId);
    }

    @Transactional
    @Override
    public Task updateTask(UUID taskListId, UUID taskId, Task task) {
        if (!Objects.equals(taskId, task.getId())) {
            throw new IllegalArgumentException("Task ID in the path and body do not match");
        }
        Task existedTask = taskRepository.findByTaskListIdAndId(taskListId, taskId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Task with ID " + taskId + " does not exist in TaskList " + taskListId));

        TaskList taskList = taskListRepository.findById(taskListId)
                .orElseThrow(() -> new IllegalArgumentException("TaskList with ID " + taskListId + " does not exist"));

        LocalDateTime now = LocalDateTime.now();
        return taskRepository.save((new Task(
                taskId,
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                task.getStatus(),
                task.getPriority(),
                taskList,
                existedTask.getCreated(),
                now)));
    }

    @Transactional
    @Override
    public void deleteTask(UUID taskListId, UUID taskId) {
        taskRepository.deleteByTaskListIdAndId(taskListId, taskId);
    }

}
