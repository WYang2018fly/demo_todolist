package com.todo.service;

import com.todo.dto.LoginDTO;

public interface AuthService {
    String login(LoginDTO loginDTO);
    Boolean checkUserRole(String username, String role);
}