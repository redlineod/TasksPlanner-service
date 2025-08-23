# Task Management API

A Spring Boot RESTful API for managing tasks and task lists.  

## Overview
This application is a backend API for managing tasks and task lists.  
It allows users to:
- Create, read, update, and delete task lists  
- Add tasks to specific lists  
- Set task priorities (e.g., low, medium, high) and statuses (e.g., open, completed)  
- Assign due dates and descriptions to tasks  
- Retrieve all tasks in a list or a specific task by ID  
- Update task details and status  
- Delete tasks from a list  

The app uses **PostgreSQL** for data storage, supports **validation** and **error handling**, and is ready for deployment with **Docker**.  
It’s suitable for personal productivity, team task management, or as a backend for a to-do application.  

## Features
- CRUD operations for tasks and task lists  
- Task priorities and statuses  
- PostgreSQL database integration  
- Exception handling and validation  
- Docker support for easy deployment  

## Tech Stack
- Java  
- Spring Boot  
- PostgreSQL  
- Docker  

## Available REST API Endpoints

### Task Lists
- `GET /api/tasklists` — Retrieve all task lists  
- `POST /api/tasklists` — Create a new task list  
- `GET /api/tasklists/{taskListId}` — Get details of a specific task list  
- `PUT /api/tasklists/{taskListId}` — Update a task list  
- `DELETE /api/tasklists/{taskListId}` — Delete a task list  

### Tasks
- `GET /api/tasklists/{taskListId}/tasks` — Retrieve all tasks in a specific task list  
- `POST /api/tasklists/{taskListId}/tasks` — Create a new task in a specific task list  
- `GET /api/tasklists/{taskListId}/tasks/{taskId}` — Get details of a specific task  
- `PUT /api/tasklists/{taskListId}/tasks/{taskId}` — Update a specific task  
- `DELETE /api/tasklists/{taskListId}/tasks/{taskId}` — Delete a specific task

## Data Model

### TaskList
| Field       | Type           | Description                        |
|-------------|---------------|------------------------------------|
| `id`        | UUID           | Unique identifier                  |
| `title`     | String         | Task list title                    |
| `description` | String       | Description (optional)             |
| `created`   | LocalDateTime  | Date of creation                   |
| `updated`   | LocalDateTime  | Date of last update                |

### Task
| Field       | Type           | Description                        |
|-------------|---------------|------------------------------------|
| `id`        | UUID           | Unique identifier                  |
| `title`     | String         | Task title                         |
| `description` | String       | Description (optional)             |
| `dueDate`   | LocalDateTime  | Due date (optional)                |
| `status`    | Enum (OPEN, COMPLETED, ...) | Task status           |
| `priority`  | Enum (LOW, MEDIUM, HIGH)    | Task priority        |
| `taskList`  | TaskList       | Reference to the related task list |
| `created`   | LocalDateTime  | Date of creation                   |
| `updated`   | LocalDateTime  | Date of last update                |
