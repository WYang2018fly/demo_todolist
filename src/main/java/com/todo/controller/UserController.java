package com.todo.controller;

import com.todo.dto.task.TaskCreateDTO;
import com.todo.dto.task.TaskDTO;
import com.todo.dto.task.TaskUpdateDTO;
import com.todo.expand.TaskExpander;
import com.todo.service.TaskService;
import com.todo.service.UserService;
import com.todo.util.LoggerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {
    private final TaskService taskService;

    @Autowired
    private TaskExpander taskExpanderProvider;

    @Autowired
    public UserController(UserService userService, TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/{userId}/tasks")
    public TaskDTO createTask(
        @PathVariable("userId") String userId,
        @RequestBody TaskCreateDTO taskCreateDTO
    ) {
        Long beginTime = System.currentTimeMillis();
        LoggerUtil.printStartCallMethodInfo("createTask", userId, taskCreateDTO);

        TaskDTO taskDTO = taskService.createTask(taskCreateDTO, userId);

        Long endTime = System.currentTimeMillis();
        LoggerUtil.printMethodExecuteDuration("createTask", beginTime, endTime);

        return taskDTO;
    }

    @GetMapping("/{userId}/tasks")
    public Page<TaskDTO> getTasks(
        @PathVariable("userId") String userId,
        Pageable pageable
    ) {
        Long beginTime = System.currentTimeMillis();
        LoggerUtil.printStartCallMethodInfo("getTasks", pageable);

        Page<TaskDTO> taskDTOPage = taskService.getAllTasksByUserId(userId, pageable);
        taskExpanderProvider.setPopulatedFieldsList("users").expandFields(taskDTOPage);

        Long endTime = System.currentTimeMillis();
        LoggerUtil.printMethodExecuteDuration("getTasks", beginTime, endTime);

        return taskDTOPage;
    }

    @PutMapping("/{userId}/tasks")
    public TaskDTO updateTask(
        @PathVariable("userId") String userId,
        @RequestBody TaskUpdateDTO taskUpdateDTO
    ) {
        Long beginTime = System.currentTimeMillis();
        LoggerUtil.printStartCallMethodInfo("updateTask", taskUpdateDTO);

        TaskDTO updatedTaskDTO = taskService.updateTaskByTaskId(taskUpdateDTO);

        Long endTime = System.currentTimeMillis();
        LoggerUtil.printMethodExecuteDuration("updateTask", beginTime, endTime);

        return updatedTaskDTO;
    }

    @GetMapping("/{userId}/tasks/{taskId}")
    public TaskDTO getTask(
        @PathVariable("userId") String userId,
        @PathVariable("taskId") String taskId
    ) {
        Long beginTime = System.currentTimeMillis();
        LoggerUtil.printStartCallMethodInfo("getTask", userId, taskId);

        TaskDTO taskDTO = taskService.findTaskByTaskAndUserId(taskId, userId);
        taskExpanderProvider.setPopulatedFieldsList("users").expandFields(taskDTO);

        Long endTime = System.currentTimeMillis();
        LoggerUtil.printMethodExecuteDuration("getTask", beginTime, endTime);

        return taskDTO;
    }

    @DeleteMapping("/{userId}/tasks/{taskId}")
    public void deleteTask(
        @PathVariable("userId") String userId,
        @PathVariable("taskId") String taskId
    ) {
        Long beginTime = System.currentTimeMillis();
        LoggerUtil.printStartCallMethodInfo("deleteTask", userId, taskId);

        taskService.deleteTaskByTaskId(taskId);

        Long endTime = System.currentTimeMillis();
        LoggerUtil.printMethodExecuteDuration("deleteTask", beginTime, endTime);
    }
}