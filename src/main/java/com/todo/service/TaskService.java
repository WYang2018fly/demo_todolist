package com.todo.service;

import com.todo.dto.task.TaskCreateDTO;
import com.todo.dto.task.TaskDTO;
import com.todo.dto.task.TaskUpdateDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

public interface TaskService {
  TaskDTO createTask(TaskCreateDTO taskCreateDTO, String userId);

  Page<TaskDTO> getAllTasks(Pageable pageable);

  Page<TaskDTO> getAllTasksByUserId(String userId, Pageable pageable);

  TaskDTO updateTaskByTaskId(TaskUpdateDTO taskUpdateDTO);

  TaskDTO findTaskByTaskAndUserId(String taskId, String userId);

  TaskDTO findTaskByTaskId(String taskId);

  void deleteTaskByTaskId(String taskId);
}