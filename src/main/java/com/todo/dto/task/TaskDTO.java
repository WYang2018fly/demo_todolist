package com.todo.dto.task;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.todo.dto.CommonDTO;
import com.todo.dto.user.UserDTO;
import lombok.Data;

@Data
@JsonPropertyOrder({"id", "title", "description", "isCompleted", "userId", "createdAt", "updatedAt"})
public class TaskDTO extends CommonDTO {
  private String title;
  private String description;
  private Integer isCompleted;
  private String userId;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private UserDTO users;
}