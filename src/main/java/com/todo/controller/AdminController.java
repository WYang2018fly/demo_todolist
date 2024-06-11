package com.todo.controller;

import com.todo.constant.AuthExceptionConstant;
import com.todo.constant.UserExceptionConstant;
import com.todo.dto.JWTResponseDTO;
import com.todo.dto.LoginDTO;
import com.todo.dto.task.TaskDTO;
import com.todo.dto.task.TaskUpdateDTO;
import com.todo.dto.user.UserCreateDTO;
import com.todo.dto.user.UserDTO;
import com.todo.enu.UserRoleEnu;
import com.todo.exception.AuthException;
import com.todo.exception.BizException;
import com.todo.expand.TaskExpander;
import com.todo.service.AuthService;
import com.todo.service.TaskService;
import com.todo.service.UserService;
import com.todo.util.LoggerUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

@RestController
@RequestMapping("/api/admin")
@AllArgsConstructor
public class AdminController {
  private AuthService authService;
  private UserService userService;
  private TaskService taskService;

  @Autowired
  private TaskExpander taskExpanderProvider;

  @PostMapping("/token")
  public JWTResponseDTO authenticate(@RequestBody LoginDTO loginDTO) {
    Boolean isAdmin = authService.checkUserRole(loginDTO.getUsername(), UserRoleEnu.ADMIN_USER_ROLE);
    if(!isAdmin) throw new AuthException(AuthExceptionConstant.JWT_NO_PERMISSION);

    String token = authService.login(loginDTO);
    JWTResponseDTO jwtResponseDTO = new JWTResponseDTO();
    jwtResponseDTO.setAccessToken(token);
    return jwtResponseDTO;
  }

  @PostMapping("/users")
  // @PreAuthorize("hasRole('admin')")
  public UserDTO crateUser(@RequestBody UserCreateDTO userCreateDTO) {
    Long beginTime = System.currentTimeMillis();
    LoggerUtil.printStartCallMethodInfo("createUser", userCreateDTO);

    // 校验用户名是否存在
    boolean usernameIsExisted = userService.checkUsernameIsExisted(userCreateDTO.getUsername());
    if(usernameIsExisted) throw new BizException(UserExceptionConstant.USER_USERNAME_EXISTED);

    UserDTO userDTO = userService.createUser(userCreateDTO);

    Long endTime = System.currentTimeMillis();
    LoggerUtil.printMethodExecuteDuration("createUser", beginTime, endTime);

    return userDTO;
  }

  @GetMapping("/users")
  // @PreAuthorize("hasRole('ROLE_admin')")
  public Page<UserDTO> getUsers(Pageable pageable) {
    Long beginTime = System.currentTimeMillis();
    LoggerUtil.printStartCallMethodInfo("getUsers", pageable);

    Page<UserDTO> userDTOPage = userService.getAllUsersByPage(pageable);

    Long endTime = System.currentTimeMillis();
    LoggerUtil.printMethodExecuteDuration("getUsers", beginTime, endTime);

    return userDTOPage;
  }

  @GetMapping("/tasks")
  public Page<TaskDTO> getTasks(Pageable pageable) {
    Long beginTime = System.currentTimeMillis();
    LoggerUtil.printStartCallMethodInfo("getTasks", pageable);

    Page<TaskDTO> taskDTOPage = taskService.getAllTasks(pageable);
    taskExpanderProvider.setPopulatedFieldsList("users").expandFields(taskDTOPage);

    Long endTime = System.currentTimeMillis();
    LoggerUtil.printMethodExecuteDuration("getTasks", beginTime, endTime);

    return taskDTOPage;
  }

  @PutMapping("/tasks")
  public TaskDTO updateTask(@RequestBody TaskUpdateDTO taskUpdateDTO) {
    Long beginTime = System.currentTimeMillis();
    LoggerUtil.printStartCallMethodInfo("updateTask", taskUpdateDTO);

    TaskDTO updatedTaskDTO = taskService.updateTaskByTaskId(taskUpdateDTO);

    Long endTime = System.currentTimeMillis();
    LoggerUtil.printMethodExecuteDuration("updateTask", beginTime, endTime);

    return updatedTaskDTO;
  }

  @GetMapping("/tasks/{taskId}")
  public TaskDTO getTask(@PathVariable String taskId) {
    Long beginTime = System.currentTimeMillis();
    LoggerUtil.printStartCallMethodInfo("getTask", taskId);

    TaskDTO taskDTO = taskService.findTaskByTaskId(taskId);
    taskExpanderProvider.setPopulatedFieldsList("users").expandFields(taskDTO);

    Long endTime = System.currentTimeMillis();
    LoggerUtil.printMethodExecuteDuration("getTask", beginTime, endTime);

    return taskDTO;
  }

  @DeleteMapping("/tasks/{taskId}")
  public void deleteTask(@PathVariable String taskId) {
    Long beginTime = System.currentTimeMillis();
    LoggerUtil.printStartCallMethodInfo("deleteTask", taskId);

    taskService.deleteTaskByTaskId(taskId);

    Long endTime = System.currentTimeMillis();
    LoggerUtil.printMethodExecuteDuration("deleteTask", beginTime, endTime);
  }
}

