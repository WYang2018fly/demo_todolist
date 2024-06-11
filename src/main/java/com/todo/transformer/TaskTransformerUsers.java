package com.todo.transformer;

import com.todo.common.Transformer;
import com.todo.dto.task.TaskDTO;
import com.todo.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TaskTransformerUsers implements Transformer<TaskDTO> {
  @Autowired
  private UserServiceImpl userService;

  @Override
  public void transform(TaskDTO taskDTO) {
    taskDTO.setUsers(userService.findUserById(taskDTO.getUserId()));
  }
}
