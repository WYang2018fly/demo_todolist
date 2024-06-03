package com.todo.controller;


import com.todo.dto.JWTResponseDTO;
import com.todo.dto.LoginDTO;
import com.todo.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/oauth")
@AllArgsConstructor
public class AuthController {
    private AuthService authService;

    @PostMapping("/token")
    public JWTResponseDTO authenticate(@RequestBody LoginDTO loginDTO) {
        String token = authService.login(loginDTO);
        JWTResponseDTO jwtResponseDTO = new JWTResponseDTO();
        jwtResponseDTO.setAccessToken(token);
        return jwtResponseDTO;
    }
}
