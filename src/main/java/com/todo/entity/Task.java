package com.todo.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "tasks")
public class Task extends CommonEntity implements Serializable {
  // 标题
  private String title;
  // 描述
  private String description;
  // 是否完成
  private Integer isCompleted;
  // 所属用户
  private String userId;
}