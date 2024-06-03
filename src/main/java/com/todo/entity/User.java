package com.todo.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "users")
public class User extends CommonEntity implements Serializable {
   // 用户名
   private String username;
   // 密码
   private String password;
   // 角色
   private String role;
}