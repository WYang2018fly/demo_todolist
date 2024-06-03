package com.todo.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.todo.dto.CommonDTO;
import lombok.Data;

@Data
@JsonPropertyOrder({"id", "username", "password", "role", "createdAt", "updatedAt"})
public class UserDTO extends CommonDTO {
    // 用户名
    private String username;
    // 密码
    @JsonIgnore
    private String password;
    // 角色
    private String role;
}