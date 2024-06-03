package com.todo.service.impl;

import com.todo.constant.TaskExceptionConstant;
import com.todo.dto.task.TaskCreateDTO;
import com.todo.dto.task.TaskDTO;
import com.todo.dto.task.TaskUpdateDTO;
import com.todo.entity.Task;
import com.todo.exception.BizException;
import com.todo.repository.TaskRepository;
import com.todo.service.TaskService;
import com.todo.util.UUIDUtil;
import com.todo.util.mapper.TaskMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class TaskServiceImpl implements TaskService {
  private final TaskRepository taskRepository;

  @Autowired
  public TaskServiceImpl(TaskRepository taskRepository) {
    this.taskRepository = taskRepository;
  }

  @Override
  public TaskDTO createTask(TaskCreateDTO taskCreateDTO, String userId) {
    Task task = new Task();
    BeanUtils.copyProperties(taskCreateDTO, task);
    task.setId(UUIDUtil.generateUUID());
    task.setIsCompleted(0);
    task.setUserId(userId);
    task.setCreatedAt(System.currentTimeMillis());
    task.setUpdatedAt(System.currentTimeMillis());
    log.info(String.valueOf(task));
    Task taskEntity = taskRepository.save(task);
    return TaskMapper.INSTANCE.taskEntityToTaskDTO(taskEntity);
  }

  @Override
  public Page<TaskDTO> getAllTasks(Pageable pageable) {
    Page<Task> tasks = taskRepository.findAll(pageable);
    return tasks.map(TaskMapper.INSTANCE::taskEntityToTaskDTO);
  }

  @Override
  public Page<TaskDTO> getAllTasksByUserId(String userId, Pageable pageable) {
    Specification<Task>  taskQuerySpecification = (root, query, criteriaBuilder) -> criteriaBuilder.and(criteriaBuilder.equal(root.get("userId"), userId));
    Page<Task> tasks = taskRepository.findAll(taskQuerySpecification, pageable);
    return tasks.map(TaskMapper.INSTANCE::taskEntityToTaskDTO);
  }

  @Override
  public TaskDTO updateTaskByTaskId(TaskUpdateDTO taskUpdateDTO) {
    String taskId = taskUpdateDTO.getTaskId();
    Task task = taskRepository.findTaskById(taskId).orElseThrow(() -> new BizException(TaskExceptionConstant.TASK_NOT_EXISTED));
    BeanUtils.copyProperties(taskUpdateDTO, task);
    task.setUpdatedAt(System.currentTimeMillis());
    task = taskRepository.save(task);
    return TaskMapper.INSTANCE.taskEntityToTaskDTO(task);
  }

  @Override
  public TaskDTO findTaskByTaskAndUserId(String taskId, String userId) {
    Specification<Task> taskQuerySpecification = (root, query, criteriaBuilder) -> criteriaBuilder.and(criteriaBuilder.equal(root.get("userId"), userId), criteriaBuilder.equal(root.get("id"), taskId));
    Task task = taskRepository.findOne(taskQuerySpecification).orElseThrow(() -> new BizException(TaskExceptionConstant.TASK_NOT_EXISTED));
    return TaskMapper.INSTANCE.taskEntityToTaskDTO(task);
  }

  @Override
  public TaskDTO findTaskByTaskId(String taskId) {
    Task taskEntity = taskRepository.findById(taskId).orElseThrow(() -> new BizException(TaskExceptionConstant.TASK_NOT_EXISTED));
    return TaskMapper.INSTANCE.taskEntityToTaskDTO(taskEntity);
  }

  @Override
  public void deleteTaskByTaskId(String taskId) {
    taskRepository.deleteById(taskId);
  }
}
