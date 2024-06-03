package com.todo.dto.task;

import lombok.Data;

@Data
public class TaskCreateDTO {
  private String title;
  private String description;
  private Integer isCompleted;
}