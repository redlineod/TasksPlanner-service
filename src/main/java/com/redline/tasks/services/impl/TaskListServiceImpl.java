package com.redline.tasks.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.redline.tasks.domain.entities.TaskList;
import com.redline.tasks.repositories.TaskListRepository;
import com.redline.tasks.services.TaskListService;

import jakarta.transaction.Transactional;

@Service
public class TaskListServiceImpl implements TaskListService {

    private final TaskListRepository taskListRepository;

    public TaskListServiceImpl(TaskListRepository taskListRepository) {
        this.taskListRepository = taskListRepository;
    }

    @Override
    public List<TaskList> listTaskLists() {
        return taskListRepository.findAll();
    }

    @Override
    public TaskList createTaskList(TaskList taskList) {
        if (taskList.getId() != null) {
            throw new IllegalArgumentException("New TaskList cannot have an ID");
        }

        if (taskList.getTitle() == null || taskList.getTitle().isBlank()) {
            throw new IllegalArgumentException("TaskList title cannot be null or empty");
        }

        LocalDateTime now = LocalDateTime.now();
        return taskListRepository.save(new TaskList(
                null,
                taskList.getTitle(),
                taskList.getDescription(),
                null,
                now,
                now));
    }

    @Override
    public Optional<TaskList> getTaskList(UUID id) {
        return taskListRepository.findById(id);
    }

    @Transactional
    @Override
    public TaskList updateTaskList(UUID id, TaskList taskList) {
        if (taskList.getId() == null) {
            throw new IllegalArgumentException("TaskList ID cannot be null");
        }

        if (!Objects.equals(taskList.getId(), id)) {
            throw new IllegalArgumentException("TaskList ID in the path and body do not match");
        }

        TaskList existingTaskList = taskListRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("TaskList with ID " + id + " does not exist"));

        existingTaskList.setTitle(taskList.getTitle());
        existingTaskList.setDescription(taskList.getDescription());
        existingTaskList.setUpdated(LocalDateTime.now());
        return taskListRepository.save(existingTaskList);
    }

    @Override
    public void deleteTaskList(UUID id) {
        taskListRepository.deleteById(id);
    }

}
