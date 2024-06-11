package com.todo.expand;

import com.todo.common.AbstractExpander;
import com.todo.common.Transformer;
import com.todo.dto.task.TaskDTO;
import com.todo.transformer.TaskTransformerUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import java.util.Map;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) // 创建原型作用域的Bean，每次获取这个Bean时会创建一个新实例
public class TaskExpander extends AbstractExpander<TaskDTO> {
  @Autowired
  private TaskTransformerUsers taskTransformerUsers;

  @Override
  public void initTransformers(Map<String, Transformer<TaskDTO>> transformers) {
    transformers.put("users", taskTransformerUsers);
  }
}