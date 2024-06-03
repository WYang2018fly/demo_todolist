package com.todo.dto;

import lombok.Data;

@Data
public class ErrorResponseDTO {
    private String reason;
    private String message;

    public ErrorResponseDTO(String reason, String errorMessage) {
        this.reason = reason;
        this.message = errorMessage;
    }
}
