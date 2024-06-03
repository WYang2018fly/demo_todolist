package com.todo.dto.task;

import lombok.Data;

@Data
public class TaskUpdateDTO {
  private String taskId;
  private String title;
  private String description;
  private Integer isCompleted;
}